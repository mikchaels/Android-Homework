package by.soloviev.mybrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BrowserActivity extends Activity implements View.OnClickListener {

    public static final String INTENT_ACTION = "android.intent.action.VIEW";
    public static final String URL = "LatestURL";
    private WebView mWebView;
    private EditText mUrlBar;
    private Button mLoadButton;
    private Button mReloadButton;
    private Button mBackButton;
    private Button mForwardButton;
    private Button mHistoryButton;

    /*
    * обработчик нажатий командных кнопок
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.load_button):
                String url = mUrlBar.getText().toString();
                if ((TextUtils.isEmpty(url)) || (url.equals("http://"))) {
                    url = getString(R.string.defAdress);
                }
                mWebView.loadUrl(url);
                break;
            case (R.id.reload_button):
                mWebView.reload();
                break;
            case (R.id.back_button):
                mWebView.goBack();
                break;
            case (R.id.forward_button):
                mWebView.goForward();
                break;
            case (R.id.history_button):
                Intent intent = new Intent(BrowserActivity.this, HistoryListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /*
    * обработчик нажатия кнопи назад девайса
    *
    * */
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        mUrlBar = (EditText) findViewById(R.id.url_bar);
        mWebView = (WebView) findViewById(R.id.web_view);
        mLoadButton = (Button) findViewById(R.id.load_button);
        mReloadButton = (Button) findViewById(R.id.reload_button);
        mBackButton = (Button) findViewById(R.id.back_button);
        mForwardButton = (Button) findViewById(R.id.forward_button);
        mHistoryButton = (Button) findViewById(R.id.history_button);
        mForwardButton.setEnabled(false);
        mBackButton.setEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
            /*
            * без переопределения этого метода,
            * тоже работает.Загадка, ждущая своего ответа...
            * */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                mUrlBar.setText(url);
                HistoryRepository.getInstance().writeHistory(new History(url));
                if (mWebView.canGoBack()) {
                    mBackButton.setEnabled(true);
                } else {
                    mBackButton.setEnabled(false);
                }
                if (mWebView.canGoForward()) {
                    mForwardButton.setEnabled(true);
                } else {
                    mForwardButton.setEnabled(false);
                }
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mLoadButton.setOnClickListener(this);
        mReloadButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mForwardButton.setOnClickListener(this);
        mHistoryButton.setOnClickListener(this);
        Intent intent = getIntent();

        if (INTENT_ACTION.equals(intent.getAction())) {
            String uri = intent.getData().toString();
            if (!TextUtils.isEmpty(uri)) {
                mWebView.loadUrl(uri);
            }
        } else {

            if (!(getSharedPreferences(URL, Context.MODE_APPEND).getString(URL, null) == null)) {
                mWebView.loadUrl(getPreferences(Context.MODE_PRIVATE).getString(URL, null));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            String url = mWebView.getUrl();
            SharedPreferences lastUrl = getSharedPreferences(URL, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = lastUrl.edit();
            editor.putString(URL, url);
            editor.apply();
           // Toast.makeText(this,"Destroy",Toast.LENGTH_LONG).show();
        }

    }
}





