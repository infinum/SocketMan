package co.infinum.socketman.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import co.infinum.socketman.R;

public class DialogUtils {

    private DialogUtils() {

    }

    public interface EndpointDialogListener {

        void onEndpointChanged(String newEndpoint);
    }

    public static void showEndpointDialog(Activity activity, String endpoint, @NonNull final EndpointDialogListener listener) {
        final EditText input = new EditText(activity);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);

        if (!TextUtils.isEmpty(endpoint)) {
            input.setText(endpoint);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.socket_endpoint));
        alert.setView(input);

        alert.setPositiveButton(activity.getString(R.string.update), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                listener.onEndpointChanged(input.getText().toString());
            }
        });

        alert.setNegativeButton(activity.getString(R.string.dismiss), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            alert.show();
        }
    }
}
