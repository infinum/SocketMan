package co.infinum.socketman.dagger.components;

import co.infinum.socketman.activities.sockets.NVActivity;
import co.infinum.socketman.dagger.modules.NVModule;
import dagger.Subcomponent;

@Subcomponent(modules = NVModule.class)
public interface NVComponent {

    void inject(NVActivity activity);
}
