package co.infinum.socketman.mvp.views;

import android.support.annotation.NonNull;

/**
 * Created by Željko Plesac on 03/04/16.
 */
public interface BaseView {

    void showLoadingDialog();

    void hideLoadingDialog();

    void showError(@NonNull String message);
}
