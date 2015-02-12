package by.soloviev.mybrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BrowserActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;
    private EditText mUrlBar;
    private Button mLoadButton;
    private Button mReloadButton;
    private Button mBackButton;
    private Button mForwardButton;
    private Button mHistoryButton;
    List<History> mHistory = new ArrayList<History>();
    // boolean goForward = false;

    public List<History> getHistory() {
        return mHistory;
    }

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
                Intent intent = new Intent(this, HistoryListActivity.class);
// थौधौ TODO пока что не знаю каким методом отправить данные
              //  intent.putExtra("History",mHistory);
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
            @Override
            /*
            *
            * */
            public void onPageFinished(WebView view, String url) {

                mUrlBar.setText(url);
                mHistory.add(new History(url));
                if (!mWebView.canGoBack()) {
                    mBackButton.setEnabled(false);
                } else {
                    mBackButton.setEnabled(true);
                }
                if (!mWebView.canGoForward()) {
                    mForwardButton.setEnabled(false);
                } else {
                    mForwardButton.setEnabled(true);
                }
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mLoadButton.setOnClickListener(this);
        mReloadButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mForwardButton.setOnClickListener(this);
        mHistoryButton.setOnClickListener(this);
    }

}





