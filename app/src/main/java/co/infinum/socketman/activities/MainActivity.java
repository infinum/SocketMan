package co.infinum.socketman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import co.infinum.socketman.R;
import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.activities.sockets.AndroidAsyncActivity;
import co.infinum.socketman.activities.sockets.AutobahnActivity;
import co.infinum.socketman.dagger.modules.MainModule;
import co.infinum.socketman.mvp.presenters.MainPresenter;
import co.infinum.socketman.mvp.views.MainView;

/**
 * Created by Željko Plesac on 09/04/16.
 */
public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter presenter;

    @Override
    protected void initDependencies() {
        SocketManApp.getInstance().getAppComponent().plus(new MainModule(this)).inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initUI() {
        // Nothing to do here
    }

    @OnClick(R.id.button_android_async)
    protected void onAndroidAsyncButtonClicked() {
        presenter.onAndroidAsyncButtonClicked();
    }

    @OnClick(R.id.button_autobahn)
    protected void onAutobahnButtonClicked() {
        presenter.onAutobahnButtonClicked();
    }

    @Override
    public void navigateToAndroidAsync() {
        Intent intent = AndroidAsyncActivity.buildIntent(this);
        startActivity(intent);
    }

    @Override
    public void navigateToAutobahn() {
        Intent intent = AutobahnActivity.buildIntent(this);
        startActivity(intent);
    }
}
