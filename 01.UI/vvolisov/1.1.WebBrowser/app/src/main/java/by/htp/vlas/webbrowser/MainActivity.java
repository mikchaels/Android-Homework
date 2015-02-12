package by.htp.vlas.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by _guest on 07.02.2015.
 */
public class MainActivity extends Activity {

    @InjectView(R.id.btn_back)
    Button mBtnBack;

    @InjectView(R.id.btn_forward)
    Button mBtnForward;

//    @InjectView(R.id.btn_go)
//    Button mBtnGo;

    @InjectView(R.id.address)
    EditText mAddress;

    @InjectView(R.id.page_data)
    WebView mPage;


    private final String URL_PREFIX_DEF = "http://";
    private final String TEXT_ENCODING_NAME_DEF = "utf-8";
    private final String SAVE_INSTANCE_ADDRESS_KEY = "address";
    private final String SAVE_INSTANCE_NAVIGATE_BTNS_STATE_KEY = "navigateButtonsState";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mBtnBack.setEnabled(false);
        mBtnForward.setEnabled(false);

        webViewInit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPage.saveState(outState);
        outState.putString(SAVE_INSTANCE_ADDRESS_KEY, mAddress.getText().toString());
        outState.putBooleanArray(SAVE_INSTANCE_NAVIGATE_BTNS_STATE_KEY, new boolean[]{mBtnBack.isEnabled(), mBtnForward.isEnabled()});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPage.restoreState(savedInstanceState);
        mAddress.setText(savedInstanceState.getString(SAVE_INSTANCE_ADDRESS_KEY));
        boolean[] navigateBtnsState = savedInstanceState.getBooleanArray(SAVE_INSTANCE_NAVIGATE_BTNS_STATE_KEY);
        mBtnBack.setEnabled(navigateBtnsState[0]);
        mBtnForward.setEnabled(navigateBtnsState[1]);
    }

    @OnClick(R.id.btn_go)
    void btnGoAction() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mAddress.getWindowToken(), 0);

        loadUrl();

        //TODO change button Go to GoRefresh - and when address  not changed manually, make this button "refresh"
    }

    @OnClick(R.id.btn_back)
    void goBack() {
        mPage.goBack();
    }

    @OnClick(R.id.btn_forward)
    void goForward() {
        mPage.goForward();
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch(keycode) {
            case KeyEvent.KEYCODE_BACK:{
                if(mPage.canGoBack()){
                    goBack();
                    return true;
                } else {
                    return super.onKeyDown(keycode, e);
                }
            }
            case KeyEvent.KEYCODE_MENU:{

                return true;
            }
            default: {
                return super.onKeyDown(keycode, e);
            }
        }

    }

    //############################## private methods ####################################

    private void loadUrl(){
        String urlString = mAddress.getText().toString();
        if (TextUtils.isEmpty(urlString)) return;

        if (!URLUtil.isValidUrl(urlString)) {
            urlString = URL_PREFIX_DEF + urlString;
            mAddress.setText(urlString);
        }
        mPage.loadUrl(urlString);
    }

    private void webViewInit() {
        WebSettings webViewSettings = mPage.getSettings();
        webViewSettings.setDefaultTextEncodingName(TEXT_ENCODING_NAME_DEF);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setBuiltInZoomControls(true);
        webViewSettings.setDisplayZoomControls(false);
        webViewSettings.setUseWideViewPort(true);
        mPage.setInitialScale(1);
        mPage.setWebViewClient(new MyWebViewClient());
    }

    //################################ private class ############################################

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mAddress.setText(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mPage.canGoBack()) {
                mBtnBack.setEnabled(true);
            } else {
                mBtnBack.setEnabled(false);
            }

            if (mPage.canGoForward()) {
                mBtnForward.setEnabled(true);
            } else {
                mBtnForward.setEnabled(false);
            }
        }
    }

}
