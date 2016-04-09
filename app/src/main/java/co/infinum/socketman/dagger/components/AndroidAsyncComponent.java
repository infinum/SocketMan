package co.infinum.socketman.dagger.components;

import co.infinum.socketman.activities.sockets.AndroidAsyncActivity;
import co.infinum.socketman.dagger.modules.AndroidAsyncModule;
import dagger.Subcomponent;

/**
 * Created by Željko Plesac on 05/04/16.
 */
@Subcomponent(modules = AndroidAsyncModule.class)
public interface AndroidAsyncComponent {

    void inject(AndroidAsyncActivity activity);
}
