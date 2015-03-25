package by.soloviev.mplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by user_2 on 11.03.2015.
 */
public class MPlayerAppWidgetProvider extends AppWidgetProvider implements MPlayerService.PlayListener {
    boolean isPlay;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        String textButton="Play";
        final int n = appWidgetIds.length;

        for (int i = 0; i < n; i++) {
            int appWidgetId = appWidgetIds[i];
            MPlayerService.setPlayListener(this);
            Intent intentPL = new Intent(context, MPlayerService.class)
                    .setAction(MPlayerService.ACTION_PLAY);
            Intent intentSt = new Intent(context, MPlayerService.class);
//            Intent intentPause = new Intent(context, MPlayerService.class)
//                    .setAction(MPlayerService.ACTION_PAUSE);


            PendingIntent pendingIntentPl = PendingIntent.getService(context, 1, intentPL, 0);
            PendingIntent pendingIntentSt = PendingIntent.getService(context, 0, intentSt, 0);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_player);


            views.setOnClickPendingIntent(R.id.play, pendingIntentPl);
            views.setOnClickPendingIntent(R.id.stop, pendingIntentSt);
            Log.d("LogWidget","updateWid");
            appWidgetManager.updateAppWidget(appWidgetId, views);
//            Log.d("LogWidget","setTextView");
//             if (isPlay) {
//                pendingIntentPl = PendingIntent.getService(context, 2, intentPause, 0);
//                textButton="Pause";
//                Log.d("LogWidget","IntentPause");
//                isPlay = false;
//            }
//            views.setTextViewText(R.id.play,textButton);




        }


    }

    @Override
    public void onPlayListener() {
        isPlay = true;
        Log.d("LogWidget","playListener");
    }
}
