package main.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.OnClick;
import main.R;
import main.ReloApp;
import main.util.Constant;
import main.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by quynguyen on 3/22/17.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.group_title_login)
    LinearLayout lnGroupTitleLogin;

    @BindView(R.id.title_toolbar_login)
    TextView mToolbarTilte;

    /*@BindView(R.id.text_view_left_toolbar)
    TextView mLeftToolbar;

    @BindView(R.id.text_view_right_toolbar)
    TextView mRightToolbar;*/

    @BindView(R.id.edit_login_username)
    EditText editUsername;

    @BindView(R.id.edit_login_password)
    EditText editPassword;

    @BindView(R.id.txt_show_error)
    TextView txtShowError;

    @BindView(R.id.bt_login)
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        lnGroupTitleLogin.setVisibility(View.VISIBLE);
        mToolbar.setBackgroundResource(R.color.colorMineShaft);
        mToolbarTilte.setVisibility(View.VISIBLE);
        mToolbarTilte.setText(getString(R.string.txt_title_login));
        // mToolbarTilte.setTextColor(R.color.white);
    }

    @OnClick(R.id.bt_login)
    public void clickLogin(View view){
        boolean isNetworkAvailable = Utils.isNetworkAvailable(this);
        if(isNetworkAvailable) {
            // Get Data From UI
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            try {
                if ((username != null && !username.isEmpty()) && (password != null && !password.isEmpty())) {
                    if(username.equals(Constant.ACC_LOGIN_DEMO_USERNAME) && password.equals(Constant.ACC_LOGIN_DEMO_PASSWORD)) {
                        Intent mainActivity = new Intent(this, MainActivity.class);
                        startActivity(mainActivity);
                    }else{
                        txtShowError.setText(getResources().getString(R.string.error_login_wrong_id_password));
                        txtShowError.setVisibility(View.VISIBLE);
                    }
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle(getResources().getString(R.string.popup_title_login));
                    alertDialog.setMessage(getResources().getString(R.string.error_blank_id_password));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getResources().getString(R.string.popup_title_login));
            alertDialog.setMessage(getResources().getString(R.string.error_connect_network));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @OnClick(R.id.link_webview_forget_id)
    public void clickForget(){
        trackingAnalytics(false,Constant.GA_LOGIN_SCREEN,Constant.GA_LOGIN_SCREEN_ACTION,
                Constant.GA_LOGIN_SCREEN_FORGET_LABEL, Constant.GA_LOGIN_SCREEN_CAN_NOT_LOGIN_VALUE);
        // Go to webview
        goNextWebview(Constant.KEY_URL_FORGET_LOGIN, Constant.WEBVIEW_URL_FORGET_LOGIN, 1);
    }

    @OnClick(R.id.link_webview_not_login)
    public void clickLinkNotLogin(){
        trackingAnalytics(false,Constant.GA_LOGIN_SCREEN, Constant.GA_LOGIN_SCREEN_ACTION,
                Constant.GA_LOGIN_SCREEN_CAN_NOT_LOGIN_LABEL, Constant.GA_LOGIN_SCREEN_CAN_NOT_LOGIN_VALUE);
        // Go to webview
        goNextWebview(Constant.KEY_URL_FORGET_LOGIN, Constant.WEBVIEW_URL_CAN_NOT_LOGIN, 2);
    }

    /**
     *
     * Show webview with current url
     * @param key a key of url
     * @param url a url address
     * @param keyCheckWebview change title webview
     * <p>Note:
     *       <b>1 - Forget ID/Password</b>
     *       <b>2 - You can not login</b>
     * </p>
     */
    //TODO make redirection after splash screen gone
    private void goNextWebview(String key, String url, int keyCheckWebview) {
        Bundle bundle = new Bundle();
        bundle.putString(key,url);
        bundle.putInt(Constant.KEY_CHECK_WEBVIEW, keyCheckWebview);
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /**
     * Tracking Google Analytic Login Screen
     * @param isOnlyScreen
     * @param category
     * @param action
     * @param label
     * @param value
     */
    public void trackingAnalytics(Boolean isOnlyScreen, String category, String action, String label, long value){
        ReloApp application = (ReloApp) getApplication();
        if(isOnlyScreen){
            application.trackingAnalyticByScreen(Constant.GA_LOGIN_SCREEN);
        }else {
            application.trackingWithAnalyticGoogleServices(category, action, label, value);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}