package by.soloviev.linkviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user_2 on 13.02.2015.
 */
public class ActivityLink extends Activity implements View.OnClickListener {
    Uri uri;
    public static final String SCHEME_HTTP = "http://";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_activity);
        Button button_go = (Button) findViewById(R.id.button_go);
        editText = (EditText) findViewById(R.id.url);

        button_go.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String url = editText.getText().toString();
        if (TextUtils.isEmpty(url)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("gecnjdfnj")
                    .setTitle("text")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH, Uri.parse(SCHEME_HTTP));
                            startActivity(intent.createChooser(intent, getString(R.string.select)));
                        }
                    })
                    .setNegativeButton("NO", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {

            if (!url.startsWith(SCHEME_HTTP)) {
                url = SCHEME_HTTP + url;
            }
            uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent.createChooser(intent, getString(R.string.select)));
        }
    }
}
