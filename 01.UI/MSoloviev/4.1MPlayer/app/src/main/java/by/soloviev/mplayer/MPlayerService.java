package by.soloviev.mplayer;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by USER on 20.03.2015.
 */
public class MPlayerService extends Service {
    public final static String ACTION_PLAY = "by.soloviev.mplayer.play";
    public final static String ACTION_PAUSE = "by.soloviev.mplayer.pause";
    public final static String ACTION_STOP = "stop";
    final static String LOG_TAG = "logMPService";
    private static MediaPlayer mMediaPlayer;
    private static PlayListener mPlayListener;
    private String mTrack = "SZ.mp3";


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        initMPlayer();
        actionFromIntent(intent);
        return new MPlayerBinder();
    }

    private void actionFromIntent(Intent intent) {
        Log.d(LOG_TAG, "actionFromIntent");
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

    private void initMPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            try {
                AssetFileDescriptor file = getAssets().openFd(mTrack);
                mMediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "onRebind");
        initMPlayer();
        actionFromIntent(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "onUnbind");
        stop();
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initMPlayer();
        actionFromIntent(intent);
        return START_REDELIVER_INTENT;
    }


    void pause() {
        Log.d(LOG_TAG, "MyService pause");
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    void play() {
        Log.d(LOG_TAG, "play");
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            if (mPlayListener != null) {
                mPlayListener.onPlayListener();
            }
            Log.d(LOG_TAG, "start");

        }
    }

    void stop() {
        Log.d(LOG_TAG, "stop");
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "Destroy");
        super.onDestroy();
        stop();
    }

    class MPlayerBinder extends Binder {
        MPlayerService getService() {
            return MPlayerService.this;
        }
    }

     static void setPlayListener(PlayListener playListener) {
        mPlayListener = playListener;
    }

    interface PlayListener {
        void onPlayListener();
    }

}
