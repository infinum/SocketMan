package co.infinum.socketman.dagger.modules;

import javax.inject.Singleton;

import co.infinum.socketman.interfaces.WebSocketsService;
import co.infinum.socketman.socket.AndroidAsyncWebSocketService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 03/04/16.
 */
@Module
public class WebSocketModule {

    @Provides
    @Singleton
    public WebSocketsService provideWebSocketService() {
        return new AndroidAsyncWebSocketService();
    }
}
