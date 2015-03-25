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
                    Log.d("Activity_log", "onClick");
                    Intent playIntent = new Intent(MPlayerActivity.this, MPlayerService.class);

                    if (isPlay) {
                        playIntent.setAction(MPlayerService.ACTION_PAUSE);
                        mButton.setText("Start");
                        Log.d("Activity_log", "Start");
                    } else {
                        playIntent.setAction(MPlayerService.ACTION_PLAY);
                        mButton.setText("Pause");
                        Log.d("Activity_log", "Pause");
                    }

                    bindService(playIntent, mServiceConnection, BIND_AUTO_CREATE);
                    Log.d("Activity_log", "bind Service  " + playIntent.getAction().toString());

                } else if (isPlay) {
                    mMPlayerService.pause();
                    mButton.setText("Start");
                } else {
                    mMPlayerService.play();
                    mButton.setText("Pause");
                }
                isPlay = !isPlay;
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMPlayerService != null) {
                    unbindService(mServiceConnection);
                    mButton.setText("Start");
                    isPlay = false;
                    mMPlayerService = null;
                }
            }
        });

    }


}
