package co.infinum.socketman.mvp.presenters;

public interface NVPresenter extends SocketPresenter {

    void onSettingsClicked();

    void onEndpointChanged(String newEndpoint);
}
