package co.infinum.socketman.dagger.components;

import co.infinum.socketman.activities.sockets.AutobahnActivity;
import co.infinum.socketman.dagger.modules.AutobahnModule;
import dagger.Subcomponent;

/**
 * Created by Željko Plesac on 05/04/16.
 */
@Subcomponent(modules = AutobahnModule.class)
public interface AutobahnComponent {

    void inject(AutobahnActivity activity);
}
