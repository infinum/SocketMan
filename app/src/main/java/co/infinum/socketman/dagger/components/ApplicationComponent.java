package co.infinum.socketman.dagger.components;

import javax.inject.Singleton;

import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.dagger.modules.AndroidAsyncModule;
import co.infinum.socketman.dagger.modules.AutobahnModule;
import co.infinum.socketman.dagger.modules.MainModule;
import dagger.Component;

/**
 * Created by Željko Plesac on 03/04/16.
 */
@Singleton
@Component
public interface ApplicationComponent {

    /**
     * Injects application dependencies.
     *
     * @param socketManApp instance of application
     */
    void inject(SocketManApp socketManApp);

    /**
     * AndroidAsync activity component.
     *
     * @param androidAsyncModule AndroidAsync module
     * @return AndroidAsync activity component for injecting View, Presenter and Interactor
     */
    AndroidAsyncComponent plus(AndroidAsyncModule androidAsyncModule);

    /**
     * Main activity component.
     *
     * @param mainModule AndroidAsync module
     * @return Main activity component for injecting View, Presenter and Interactor
     */
    MainComponent plus(MainModule mainModule);

    /**
     * AutobahnModule activity component.
     *
     * @param autobahnModule AutobahnModule module
     * @return AutobahnModule activity component for injecting View, Presenter and Interactor
     */
    AutobahnComponent plus(AutobahnModule autobahnModule);
}
