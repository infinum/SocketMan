package co.infinum.socketman.mvp.presenters;

/**
 * Created by Željko Plesac on 05/04/16.
 */
public interface MainPresenter extends BasePresenter {

    void onSendClicked(String message);

    void subscribeToSocket();

    void unsubscribeFromSocket();
}
