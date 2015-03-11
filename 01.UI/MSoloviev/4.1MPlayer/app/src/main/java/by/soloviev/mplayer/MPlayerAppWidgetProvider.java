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
public class MPlayerAppWidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N=appWidgetIds.length;
        for (int i=0;i<N;i++){
            int appWidgetId=appWidgetIds[i];
            Intent intent= new Intent(context,MPlayerService.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            RemoteViews views =new RemoteViews(context.getPackageName(),R.layout.app_vidget_player);
            views.setOnClickPendingIntent(R.id.play,pendingIntent);
            views.setOnClickPendingIntent(R.id.stop,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);

        }


    }
}
