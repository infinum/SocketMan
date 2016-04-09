package co.infinum.socketman.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import co.infinum.socketman.R;
import co.infinum.socketman.mvp.views.BaseView;

/**
 * Created by Željko Plesac on 03/04/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    @Override
    public void showLoadingDialog() {
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage(getString(R.string.loading));
            loadingDialog.setCanceledOnTouchOutside(false);

            if (!isFinishing()) {
                loadingDialog.show();
            }
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (!isFinishing() && loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showError(@NonNull String message) {
        if (!isFinishing()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    protected void setActionBarTitle(String title) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(android.R.color.transparent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void initDependencies();
}
