package co.infinum.socketman.mvp.presenters.impl;

import android.text.TextUtils;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import co.infinum.socketman.R;
import co.infinum.socketman.interfaces.WebSocketsService;
import co.infinum.socketman.mvp.presenters.PresenterTemplate;
import co.infinum.socketman.mvp.presenters.SocketPresenter;
import co.infinum.socketman.mvp.views.SocketView;
import co.infinum.socketman.utils.StringUtils;

/**
 * Created by Željko Plesac on 05/04/16.
 */
public class SocketPresenterImpl extends PresenterTemplate<SocketView, Void> implements SocketPresenter,
        WebSocketsService.ConnectedCallback, WebSocketsService.MessagesCallback {

    private WebSocketsService webSocketsService;

    @Inject
    public SocketPresenterImpl(SocketView view, WebSocketsService webSocketsService) {
        super(view, null);
        this.webSocketsService = webSocketsService;
    }

    @Override
    public void init() {
        view.initUI();
    }

    @Override
    public void onSendClicked(String message) {
        if (TextUtils.isEmpty(message)) {
            view.showError(StringUtils.getString(R.string.error_empty_message));
            return;
        }

        if (webSocketsService.isConnected()) {
            view.addUserMessage(message, DateFormat.getDateTimeInstance().format(new Date()));
            webSocketsService.sendMessage(message);
        } else {
            onDisconnected();
        }
    }

    @Override
    public void unsubscribeFromSocket() {
        webSocketsService.disconnect();
        webSocketsService.resetConnectionCallback();
    }

    @Override
    public void subscribeToSocket() {
        webSocketsService.connect(this);
    }

    @Override
    public void cancel() {
        unsubscribeFromSocket();
    }

    // region Socket messages callbacks

    @Override
    public void onMessageReceived(String message) {
        if (!TextUtils.isEmpty(message)) {
            view.addSocketMessage(message, DateFormat.getDateTimeInstance().format(new Date()));
        }
    }

    @Override
    public void onSocketClosed() {
        onDisconnected();
    }

    // end region

    // region Socket connectivity callbacks

    @Override
    public void onConnected() {
        view.showInfoMessage(StringUtils.getString(R.string.connected_to_web_socket));
        webSocketsService.setMessagesListener(this);
    }

    @Override
    public void onDisconnected() {
        view.showInfoMessage(StringUtils.getString(R.string.disconnected_from_web_socket));
    }

    @Override
    public void onAlreadyConnected() {
        view.showInfoMessage(StringUtils.getString(R.string.already_connected_to_web_socket));
    }

    @Override
    public void onWebSocketException(Exception ex, boolean isSocketStillConnected) {
        view.showError(ex.getMessage());
    }

    // endregion
}
