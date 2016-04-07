package co.infinum.socketman.dagger.modules;

import co.infinum.socketman.mvp.presenters.MainPresenter;
import co.infinum.socketman.mvp.presenters.impl.MainPresenterImpl;
import co.infinum.socketman.mvp.views.MainView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 05/04/16.
 */
@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainView provideView() {
        return view;
    }

    @Provides
    public MainPresenter providePresenter(MainPresenterImpl presenter) {
        return presenter;
    }
}
