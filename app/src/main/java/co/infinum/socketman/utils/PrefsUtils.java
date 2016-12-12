package co.infinum.socketman.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import co.infinum.socketman.SocketManApp;

public class PrefsUtils {

    private PrefsUtils() {
    }

    //region Preference keys

    /**
     * Preferences key for socket endpoint.
     */
    public static final String ENDPOINT_KEY = "endpoint";

    //endregion

    /**
     * This method saves String object in shared preferences.
     *
     * @param ctx   context
     * @param value String object to save
     * @param key   key of the object in shared preferences
     */
    static void saveStringToPreferences(Context ctx, String value, String key) {
        getSharedPrefs(ctx).edit().putString(key, value).apply();
    }

    /**
     * .
     * This method saves int object in shared preferences.
     *
     * @param ctx   context
     * @param value int object to save
     * @param key   key of the object in shared preferences
     */
    static void saveIntToPreferences(Context ctx, int value, String key) {
        getSharedPrefs(ctx).edit().putInt(key, value).apply();
    }

    /**
     * This method saves boolean object in shared preferences.
     *
     * @param ctx   context
     * @param value boolean object to save
     * @param key   key of the object in shared preferences
     */
    static void saveBooleanToPreferences(Context ctx, boolean value, String key) {
        getSharedPrefs(ctx).edit().putBoolean(key, value).apply();
    }

    /**
     * This method saves float in shared preferences.
     *
     * @param ctx   context
     * @param value float to save
     * @param key   key of the object in shared preferences
     */
    public static void saveFloatToPreferences(Context ctx, float value, String key) {
        getSharedPrefs(ctx).edit().putFloat(key, value).apply();
    }

    /**
     * This method saves long in shared preferences.
     *
     * @param ctx   context
     * @param value long object to save
     * @param key   key of the object in shared preferences
     */
    public static void saveLongToPreferences(Context ctx, long value, String key) {
        getSharedPrefs(ctx).edit().putLong(key, value).apply();
    }

    /**
     * This method returns boolean object from preferences.
     *
     * @param ctx          Context
     * @param key          object key
     * @param defaultValue default value
     */
    public static boolean getBooleanFromPreferences(Context ctx, String key, boolean defaultValue) {
        return getSharedPrefs(ctx).getBoolean(key, defaultValue);
    }

    /**
     * This method returns float object from preferences.
     *
     * @param ctx          Context
     * @param key          object key
     * @param defaultValue default value
     */
    public static float getFloatFromPreferences(Context ctx, String key, float defaultValue) {
        return getSharedPrefs(ctx).getFloat(key, defaultValue);
    }

    /**
     * This method returns String object from preferences.
     *
     * @param ctx          Context
     * @param key          object key
     * @param defaultValue default value
     */
    public static String getStringFromPreferences(Context ctx, String key, String defaultValue) {
        return getSharedPrefs(ctx).getString(key, defaultValue);
    }

    /**
     * This method returns int object from preferences.
     *
     * @param ctx          Context
     * @param key          object key
     * @param defaultValue default value
     */
    public static int getIntFromPreferences(Context ctx, String key, int defaultValue) {
        return getSharedPrefs(ctx).getInt(key, defaultValue);
    }

    public static long getLongFromPreferences(Context ctx, String key, long defaultValue) {
        return getSharedPrefs(ctx).getLong(key, defaultValue);
    }

    /**
     * This method returns SharedPreferences object.
     */
    public static SharedPreferences getSharedPrefs(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void deleteAllSharedPreferences() {
        getSharedPrefs(SocketManApp.getInstance()).edit().clear().apply();
    }
}
