package co.infinum.socketman.activities.sockets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.infinum.socketman.R;
import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.activities.BaseActivity;
import co.infinum.socketman.dagger.modules.NVModule;
import co.infinum.socketman.mvp.presenters.NVPresenter;
import co.infinum.socketman.mvp.views.NVView;
import co.infinum.socketman.utils.DialogUtils;
import co.infinum.socketman.views.InMessageView;
import co.infinum.socketman.views.OutMessageView;

public class NVActivity extends BaseActivity implements NVView {

    @Bind(R.id.et_text)
    EditText etText;

    @Bind(R.id.content_layout)
    LinearLayout contentLayout;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.sv_log)
    ScrollView logScrollView;

    @Bind(R.id.tv_log)
    TextView tvLog;

    @Inject
    NVPresenter presenter;

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, NVActivity.class);
        return intent;
    }

    @Override
    protected void initDependencies() {
        SocketManApp.getInstance().getAppComponent().plus(new NVModule(this)).inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);

        presenter.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeToSocket();
    }

    @Override
    public void initUI() {
        logScrollView.setVisibility(View.VISIBLE);
        tvLog.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                presenter.onSettingsClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribeFromSocket();
    }

    @Override
    public void appendSocketLog(String log) {
        tvLog.append(log);
    }

    @Override
    public void cleanSocketLog() {
        tvLog.setText("");
    }

    @Override
    public void showInfoMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addUserMessage(@NonNull String message, @NonNull String info) {
        InMessageView inMessageView = new InMessageView(this);
        inMessageView.setText(message, info);
        contentLayout.addView(inMessageView);

        scrollToBottom();
    }

    @Override
    public void addSocketMessage(@NonNull String message, @NonNull String info) {
        OutMessageView outMessageView = new OutMessageView(this);
        outMessageView.setText(message, info);
        contentLayout.addView(outMessageView);

        scrollToBottom();
    }

    private void scrollToBottom() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @OnClick(R.id.button_send)
    void onSendButtonClicked() {
        presenter.onSendClicked(etText.getText().toString());
    }

    @Override
    public void showEndpointDialog(String endpoint) {
        DialogUtils.showEndpointDialog(this, endpoint, new DialogUtils.EndpointDialogListener() {
            @Override
            public void onEndpointChanged(String newEndpoint) {
                presenter.onEndpointChanged(newEndpoint);
            }
        });
    }
}
