package co.infinum.socketman.dagger.components;

import co.infinum.socketman.activities.MainActivity;
import co.infinum.socketman.dagger.modules.MainModule;
import dagger.Subcomponent;

/**
 * Created by Željko Plesac on 05/04/16.
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);
}
