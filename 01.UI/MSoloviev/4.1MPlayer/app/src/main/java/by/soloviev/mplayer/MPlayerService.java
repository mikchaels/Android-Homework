package by.soloviev.mplayer;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;


import java.io.IOException;

/**
 * Created by USER on 01.03.2015.
 */
public class MPlayerService extends IntentService {

    private static MediaPlayer mMediaPlayer;
    public final static String ACTION_PLAY = "play";
    public final static String ACTION_PAUSE = "pause";
    public final static String ACTION_STOP = "stop";

    public MPlayerService() {
        super(MPlayerService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_PLAY:
                play();
                break;

            case ACTION_STOP:
                stop();
                break;

            case ACTION_PAUSE:
                pause();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown action '" + intent.getAction() + "'.");

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            try {
                AssetFileDescriptor file = getAssets().openFd("SZ.mp3");
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void pause() {
        // if (mMediaPlayer.isPlaying()) {
        mMediaPlayer.pause();
        // }
    }

    private void play() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private void stop() {
        if (!(mMediaPlayer == null)) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
