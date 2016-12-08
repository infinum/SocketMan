package co.infinum.socketman.dagger.modules;

import co.infinum.socketman.interfaces.WebSocketsService;
import co.infinum.socketman.mvp.presenters.SocketPresenter;
import co.infinum.socketman.mvp.presenters.impl.SocketPresenterImpl;
import co.infinum.socketman.mvp.views.SocketView;
import co.infinum.socketman.socket.AndroidAsyncWebSocketService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 05/04/16.
 */
@Module
public class AndroidAsyncModule {

    private SocketView view;

    public AndroidAsyncModule(SocketView view) {
        this.view = view;
    }

    @Provides
    public SocketView provideView() {
        return view;
    }

    @Provides
    public SocketPresenter providePresenter(SocketPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    public WebSocketsService provideWebSocketImplementation() {
        return new AndroidAsyncWebSocketService();
    }
}
