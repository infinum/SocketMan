package co.infinum.socketman.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.infinum.socketman.R;

/**
 * Created by Željko Plesac on 05/04/16.
 */
public class OutMessageView extends LinearLayout {

    @Bind(R.id.tv_text)
    TextView tvText;

    @Bind(R.id.tv_info)
    TextView tvInfo;

    public OutMessageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public OutMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public OutMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_message_out, this);
        ButterKnife.bind(this, this);
    }

    public void setText(String text, String info) {
        tvText.setText(text);
        tvInfo.setText(info);
    }
}
