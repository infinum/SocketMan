package co.infinum.socketman.socket;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;

import co.infinum.socketman.BuildConfig;
import co.infinum.socketman.interfaces.WebSocketsService;

public class NVWebSocketService implements WebSocketsService {

    private static final int TIMEOUT = 30000;

    private WebSocket webSocket;

    /**
     * Callback used to transmit information about web socket connection.
     */
    private ConnectedCallback connectedCallback;

    private MessagesCallback messagesCallback;

    @Override
    public void connect(String endpoint, ConnectedCallback connectedCallback) {
        this.connectedCallback = connectedCallback;

        if (isConnected()) {
            connectedCallback.onAlreadyConnected();
        } else {
            // Create a WebSocketFactory instance.
            try {
                connectedCallback.showLog("Connecting to web socket, endpoint is " + endpoint);

                SSLContext context = NaiveSSLClient.getInstance("TLS");
                new WebSocketFactory()
                        .setSSLContext(context)
                        .setConnectionTimeout(TIMEOUT)
                        .createSocket(BuildConfig.SOCKET_ENDPOINT)
                        .addListener(webSocketAdapter)
                        .addProtocol("XSocketsNET")
                        .connectAsynchronously();
            } catch (IOException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                connectedCallback.onWebSocketException(ex, false);
            }
        }
    }

    @Override
    public void setMessagesListener(MessagesCallback messagesCallback) {
        this.messagesCallback = messagesCallback;
    }

    @Override
    public void disconnect() {
        if (webSocket != null) {
            webSocket.disconnect();
        }
    }

    @Override
    public void resetConnectionCallback() {
        connectedCallback = null;
    }

    @Override
    public boolean isConnected() {
        if (webSocket != null) {
            return webSocket.isOpen();
        } else {
            return false;
        }
    }

    @Override
    public void sendMessage(@NonNull String message) {
        if (webSocket != null && webSocket.isOpen()) {
            connectedCallback.showLog("Sending message to web socket: " + message);
            webSocket.sendText(message);
        } else if (messagesCallback != null) {
            messagesCallback.onSocketClosed();
        }
    }

    private WebSocketAdapter webSocketAdapter = new WebSocketAdapter() {

        @Override
        public void onTextMessage(WebSocket websocket, final String text) throws Exception {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    connectedCallback.showLog("Web socket message received: " + text);
                    messagesCallback.onMessageReceived(text);
                }
            });
        }

        @Override
        public void onConnected(WebSocket socket, Map<String, List<String>> headers) throws Exception {
            if (webSocket != null) {
                if (webSocket.isOpen()) {
                    return;
                } else {
                    webSocket.disconnect();
                }
            }

            webSocket = socket;

            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if (connectedCallback != null) {
                        connectedCallback.showLog("Connected to web socket");
                        connectedCallback.onConnected();
                    }
                }
            });
        }

        @Override
        public void onConnectError(WebSocket websocket, final WebSocketException exception) throws Exception {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    connectedCallback.showLog("An error occurred while connecting to web socket: " + exception.getMessage());
                    connectedCallback.onWebSocketException(exception, false);
                }
            });
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                boolean closedByServer) throws Exception {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    connectedCallback.showLog("Disconnected from web socket");
                    connectedCallback.onDisconnected();
                }
            });
        }
    };

    /**
     * Callback executor,  which will post the runnable on main thread.
     */
    private Executor callbackExecutor = new Executor() {

        Handler mainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainHandler.post(command);
        }
    };
}
