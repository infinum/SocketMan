package co.infinum.socketman.dagger.modules;

import co.infinum.socketman.interfaces.WebSocketsService;
import co.infinum.socketman.mvp.presenters.NVPresenter;
import co.infinum.socketman.mvp.presenters.impl.NVPresenterImpl;
import co.infinum.socketman.mvp.views.NVView;
import co.infinum.socketman.socket.NVWebSocketService;
import dagger.Module;
import dagger.Provides;

@Module
public class NVModule {

    private NVView view;

    public NVModule(NVView view) {
        this.view = view;
    }

    @Provides
    public NVView provideView() {
        return view;
    }

    @Provides
    public NVPresenter providePresenter(NVPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    public WebSocketsService provideWebSocketImplementation() {
        return new NVWebSocketService();
    }
}
