package by.soloviev.mplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MPlayerActivity extends Activity {


    public static final String ACTIVITY_LOG = "Activity_log";

    String textButtonStart = getString(R.string.button_name_start);
    String textButtonStop = getString(R.string.button_name_stop);
    String textButtonPause = getString(R.string.button_name_pause);

    private MPlayerService mMPlayerService;
    private ServiceConnection mServiceConnection;
    private boolean isPlay;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mplayer);
        mButton = (Button) findViewById(R.id.play);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMPlayerService = ((MPlayerService.MPlayerBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMPlayerService = null;
            }
        };
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMPlayerService == null) {
                    Log.d(ACTIVITY_LOG, "onClick");
                    Intent playIntent = new Intent(MPlayerActivity.this, MPlayerService.class);

                    if (isPlay) {
                        playIntent.setAction(MPlayerService.ACTION_PAUSE);
                        mButton.setText(textButtonStart);
                        Log.d(ACTIVITY_LOG, "Start");
                    } else {
                        playIntent.setAction(MPlayerService.ACTION_PLAY);
                        mButton.setText(textButtonPause);
                        Log.d(ACTIVITY_LOG, "Pause");
                    }

                    bindService(playIntent, mServiceConnection, BIND_AUTO_CREATE);
                    Log.d(ACTIVITY_LOG, "bind Service  " + playIntent.getAction().toString());

                } else if (isPlay) {
                    mMPlayerService.pause();
                    mButton.setText(textButtonStart);
                } else {
                    mMPlayerService.play();
                    mButton.setText(textButtonPause);
                }
                isPlay = !isPlay;
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMPlayerService != null) {
                    unbindService(mServiceConnection);
                    mButton.setText(textButtonStart);
                    isPlay = false;
                    mMPlayerService = null;
                }
            }
        });

    }


}
