package co.infinum.socketman.mvp.presenters.impl;

import android.text.TextUtils;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import co.infinum.socketman.R;
import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.interfaces.WebSocketsService;
import co.infinum.socketman.mvp.presenters.NVPresenter;
import co.infinum.socketman.mvp.presenters.PresenterTemplate;
import co.infinum.socketman.mvp.views.NVView;
import co.infinum.socketman.utils.PrefsUtils;
import co.infinum.socketman.utils.StringUtils;

public class NVPresenterImpl extends PresenterTemplate<NVView, Void> implements NVPresenter,
        WebSocketsService.ConnectedCallback, WebSocketsService.MessagesCallback {

    private static final String DEFAULT_ENDPOINT = "wss://socket2.bigbetworld.com:8001/LiveData";

    private WebSocketsService webSocketsService;

    private String endpoint;

    @Inject
    public NVPresenterImpl(NVView view, WebSocketsService webSocketsService) {
        super(view, null);
        this.webSocketsService = webSocketsService;

        endpoint = PrefsUtils.getStringFromPreferences(SocketManApp.getInstance(), PrefsUtils.ENDPOINT_KEY, DEFAULT_ENDPOINT);
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
        webSocketsService.connect(endpoint, this);
    }

    @Override
    public void cancel() {
        unsubscribeFromSocket();
    }

    @Override
    public void onSettingsClicked() {
        view.showEndpointDialog(endpoint);
    }

    @Override
    public void onEndpointChanged(String newEndpoint) {
        this.endpoint = newEndpoint;
        webSocketsService.disconnect();

        view.cleanSocketLog();
        webSocketsService.connect(endpoint, this);
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

    @Override
    public void showLog(String log) {
        view.appendSocketLog(log + "\n\n");
    }

    // endregion
}
