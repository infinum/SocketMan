package co.infinum.socketman.socket;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import co.infinum.socketman.BuildConfig;
import co.infinum.socketman.interfaces.WebSocketsService;

/**
 * Created by Željko Plesac on 03/04/16.
 */
public class AndroidAsyncWebSocketService implements WebSocketsService, WebSocket.StringCallback, CompletedCallback {

    private WebSocket webSocket;

    /**
     * Callback used to transmit information about web socket connection.
     */
    private ConnectedCallback connectedCallback;

    private MessagesCallback messagesCallback;


    @Override
    public void connect(final ConnectedCallback connectedCallback) {
        if (isConnected()) {
            connectedCallback.onAlreadyConnected();
            return;
        }

        AsyncHttpClient.getDefaultInstance().websocket(BuildConfig.SOCKET_ENDPOINT, BuildConfig.SOCKET_PROTOCOL,
                new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(final Exception ex, WebSocket socket) {
                        if (ex != null) {
                            callbackExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    connectedCallback.onWebSocketException(ex, false);

                                }
                            });

                        } else {
                            webSocket = socket;
                            webSocket.setStringCallback(AndroidAsyncWebSocketService.this);
                            webSocket.setClosedCallback(AndroidAsyncWebSocketService.this);

                            callbackExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (connectedCallback != null) {
                                        connectedCallback.onConnected();
                                    }
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void disconnect() {
        if (webSocket != null) {
            webSocket.close();
            webSocket = null;
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
    public void onCompleted(Exception ex) {
        if (ex != null && connectedCallback != null) {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    connectedCallback.onDisconnected();
                    webSocket = null;
                }
            });
        }
    }

    @Override
    public void onStringAvailable(final String s) {
        if (messagesCallback != null) {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    messagesCallback.onMessageReceived(s);
                }
            });
        }
    }

    @Override
    public void setMessagesListener(MessagesCallback messagesCallback) {
        this.messagesCallback = messagesCallback;
    }

    @Override
    public void sendMessage(@NonNull String message) {
        if (webSocket != null && webSocket.isOpen()) {
            webSocket.send(message);
        } else if (messagesCallback != null) {
            messagesCallback.onSocketClosed();
        }
    }

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
