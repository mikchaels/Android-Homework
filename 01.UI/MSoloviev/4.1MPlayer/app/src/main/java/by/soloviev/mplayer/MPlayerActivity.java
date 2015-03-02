package by.soloviev.mplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MPlayerActivity extends Activity {
    private boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mplayer);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(MPlayerActivity.this,MPlayerService.class);
                Button button=(Button)v;
                if (isPlay) {
                    playIntent.setAction(MPlayerService.ACTION_PAUSE);
                    button.setText("Start");
                } else {
                    playIntent.setAction(MPlayerService.ACTION_PLAY);
                    button.setText("Pause");
                }
                isPlay = !isPlay;
                startService(playIntent);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(MPlayerActivity.this, MPlayerService.class);
                playIntent.setAction(MPlayerService.ACTION_STOP);
                startService(playIntent);
                isPlay = false;
            }
        });
    }
}
