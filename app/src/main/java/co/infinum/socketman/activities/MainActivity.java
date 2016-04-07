package co.infinum.socketman.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.infinum.socketman.R;
import co.infinum.socketman.SocketManApp;
import co.infinum.socketman.dagger.modules.MainModule;
import co.infinum.socketman.mvp.presenters.MainPresenter;
import co.infinum.socketman.mvp.views.MainView;
import co.infinum.socketman.views.InMessageView;
import co.infinum.socketman.views.OutMessageView;

/**
 * Created by Željko Plesac on 03/04/16.
 */
public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.et_text)
    EditText etText;

    @Bind(R.id.content_layout)
    LinearLayout contentLayout;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Inject
    MainPresenter presenter;

    @Override
    protected void initDependencies() {
        SocketManApp.getInstance().getAppComponent().plus(new MainModule(this)).inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeToSocket();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribeFromSocket();
    }

    @Override
    public void initUI() {

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
}
