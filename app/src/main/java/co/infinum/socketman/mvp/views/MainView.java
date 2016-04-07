package co.infinum.socketman.mvp.views;

import android.support.annotation.NonNull;

/**
 * Created by Željko Plesac on 05/04/16.
 */
public interface MainView extends BaseView {

    void initUI();

    void showInfoMessage(@NonNull String message);

    void addSocketMessage(@NonNull String message, @NonNull String info);

    void addUserMessage(@NonNull String message, @NonNull String info);
}
