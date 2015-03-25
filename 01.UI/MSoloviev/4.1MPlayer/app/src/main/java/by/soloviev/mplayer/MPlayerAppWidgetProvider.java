package by.soloviev.mplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by user_2 on 11.03.2015.
 */
public class MPlayerAppWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int n = appWidgetIds.length;

        for (int i = 0; i < n; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intentPL = new Intent(context, MPlayerService.class)
                    .setAction(MPlayerService.ACTION_PLAY);
            Intent intentSt = new Intent(context, MPlayerService.class).
                    setAction(MPlayerService.ACTION_STOP);;



            PendingIntent pendingIntentPl = PendingIntent.getService(context, 1, intentPL, 0);
            PendingIntent pendingIntentSt = PendingIntent.getService(context, 0, intentSt, 0);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_player);


            views.setOnClickPendingIntent(R.id.play, pendingIntentPl);
            views.setOnClickPendingIntent(R.id.stop, pendingIntentSt);
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }


    }


}
