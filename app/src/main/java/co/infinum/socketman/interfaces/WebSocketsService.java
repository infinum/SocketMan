package co.infinum.socketman.interfaces;

import android.support.annotation.NonNull;

/**
 * Created by Željko Plesac on 03/04/16.
 */
public interface WebSocketsService {

    /**
     * Connect web sockets callback.
     */
    interface ConnectedCallback {

        /**
         * Called when received an acknowledgement message from WebSocket host.
         * After this method is called, WebSocket is ready to be registered for updates.
         */
        void onConnected();

        void onDisconnected();

        void onAlreadyConnected();

        void onWebSocketException(Exception ex, boolean isSocketStillConnected);
    }

    interface MessagesCallback {

        void onSocketClosed();

        void onMessageReceived(String message);
    }

    /**
     * Connects to the  WebSocket.
     *
     * @param connectedCallback callback that will notify that web socket is connected
     */
    void connect(ConnectedCallback connectedCallback);

    void setMessagesListener(MessagesCallback messagesCallback);

    /**
     * Disconnects from the  WebSocket.
     */
    void disconnect();

    void resetConnectionCallback();

    boolean isConnected();

    void sendMessage(@NonNull String message);
}
