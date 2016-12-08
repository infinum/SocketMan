package co.infinum.socketman.activities.sockets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import co.infinum.socketman.R;
import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.dagger.modules.AndroidAsyncModule;

/**
 * Created by Željko Plesac on 09/04/16.
 */
public class AndroidAsyncActivity extends SocketActivity {

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, AndroidAsyncActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getString(R.string.android_async));
    }

    @Override
    protected void initDependencies() {
        SocketManApp.getInstance().getAppComponent().plus(new AndroidAsyncModule(this)).inject(this);
    }
}
