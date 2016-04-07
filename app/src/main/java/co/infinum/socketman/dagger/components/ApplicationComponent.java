package co.infinum.socketman.dagger.components;

import javax.inject.Singleton;

import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.dagger.modules.MainModule;
import co.infinum.socketman.dagger.modules.WebSocketModule;
import dagger.Component;

/**
 * Created by Željko Plesac on 03/04/16.
 */
@Singleton
@Component(modules = WebSocketModule.class)
public interface ApplicationComponent {

    /**
     * Injects application dependencies.
     *
     * @param socketManApp instance of application
     */
    void inject(SocketManApp socketManApp);

    /**
     * Main activity component.
     *
     * @param mainModule Main module
     * @return Main activity component for injecting View, Presenter and Interactor
     */
    MainComponent plus(MainModule mainModule);

}
