package co.infinum.socketman.mvp.presenters.impl;

import javax.inject.Inject;

import co.infinum.socketman.mvp.presenters.MainPresenter;
import co.infinum.socketman.mvp.presenters.PresenterTemplate;
import co.infinum.socketman.mvp.views.MainView;

/**
 * Created by Željko Plesac on 09/04/16.
 */
public class MainPresenterImpl extends PresenterTemplate<MainView, Void> implements MainPresenter {

    @Inject
    public MainPresenterImpl(MainView view) {
        super(view, null);
    }

    @Override
    public void init() {
        view.initUI();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void onAndroidAsyncButtonClicked() {
        view.navigateToAndroidAsync();
    }

    @Override
    public void onAutobahnButtonClicked() {
        view.navigateToAutobahn();
    }
}
