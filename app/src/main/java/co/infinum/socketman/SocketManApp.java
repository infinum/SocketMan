package co.infinum.socketman;

import android.app.Application;

import co.infinum.socketman.dagger.components.ApplicationComponent;
import co.infinum.socketman.dagger.components.DaggerApplicationComponent;
import timber.log.Timber;

/**
 * Created by Željko Plesac on 03/04/16.
 */
public class SocketManApp extends Application {

    private static SocketManApp instance;

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        appComponent = DaggerApplicationComponent.builder().build();
        appComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

    protected void setInstance(SocketManApp instance) {
        SocketManApp.instance = instance;
    }

    public static SocketManApp getInstance() {
        return instance;
    }

}


