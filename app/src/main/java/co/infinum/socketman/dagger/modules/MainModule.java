package co.infinum.socketman.dagger.modules;

import co.infinum.socketman.mvp.presenters.MainPresenter;
import co.infinum.socketman.mvp.presenters.impl.MainPresenterImpl;
import co.infinum.socketman.mvp.views.MainView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 09/04/16.
 */
@Module
public class MainModule {

    private MainView mainView;

    public MainModule(MainView mainView) {
        this.mainView = mainView;
    }

    @Provides
    public MainView provideView() {
        return mainView;
    }

    @Provides
    public MainPresenter providePresenter(MainPresenterImpl presenter) {
        return presenter;
    }
}
