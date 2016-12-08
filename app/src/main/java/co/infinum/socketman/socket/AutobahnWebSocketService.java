package co.infinum.socketman.socket;

import android.support.annotation.NonNull;

import co.infinum.socketman.BuildConfig;
import co.infinum.socketman.exceptions.SocketProtocolException;
import co.infinum.socketman.interfaces.WebSocketsService;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by Željko Plesac on 09/04/16.
 */
public class AutobahnWebSocketService implements WebSocketsService {

    private final WebSocketConnection mConnection = new WebSocketConnection();

    /**
     * Callback used to transmit information about web socket connection.
     */
    private ConnectedCallback connectedCallback;

    private MessagesCallback messagesCallback;


    @Override
    public void connect(ConnectedCallback callback) {
        if (BuildConfig.SOCKET_PROTOCOL.equals("wss")) {
            connectedCallback.onWebSocketException(new SocketProtocolException("Autobahn doesn't have support for WSS"), false);
            return;
        }

        if (isConnected()) {
            connectedCallback.onAlreadyConnected();
            return;
        }

        try {
            this.connectedCallback = callback;
            String uri = BuildConfig.SOCKET_ENDPOINT;
            mConnection.connect(uri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    if (connectedCallback != null) {
                        connectedCallback.onConnected();
                    }
                }

                @Override
                public void onTextMessage(String payload) {
                    if (messagesCallback != null) {
                        messagesCallback.onMessageReceived(payload);
                    }
                }

                @Override
                public void onClose(int code, String reason) {
                    if (connectedCallback != null) {
                        connectedCallback.onDisconnected();
                    }
                }
            });
        } catch (WebSocketException e) {
            connectedCallback.onWebSocketException(e, false);
        }
    }

    @Override
    public void setMessagesListener(MessagesCallback messagesCallback) {
        this.messagesCallback = messagesCallback;
    }

    @Override
    public void disconnect() {
        mConnection.disconnect();
    }

    @Override
    public void resetConnectionCallback() {
        connectedCallback = null;
    }

    @Override
    public boolean isConnected() {
        return mConnection.isConnected();
    }

    @Override
    public void sendMessage(@NonNull String message) {
        if (isConnected()) {
            mConnection.sendTextMessage(message);
        } else if (messagesCallback != null) {
            messagesCallback.onSocketClosed();
        }
    }
}
