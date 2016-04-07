package co.infinum.socketman.utils;

import android.support.annotation.StringRes;

import co.infinum.socketman.SocketManApp;

/**
 * Created by Željko Plesac on 05/04/16.
 */
public class StringUtils {

    private StringUtils() {
    }

    public static String getString(@StringRes int stringResource) {
        return SocketManApp.getInstance().getString(stringResource);
    }
}
