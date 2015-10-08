package es.main.anotai;

import projeto.es.view.anotai.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import es.view.anotai.MainActivity;

public class AlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        launchNotification(AnotaiBroadcast.getTaskDescrition(),
                getApplicationContext());

    }

    private static void launchNotification(final String text, Context context) {
        final Intent resultIntent = new Intent();

        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        final PendingIntent resultPendIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Recupera o som default para notificação.        
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
		final Notification notify = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setStyle(new Notification.BigTextStyle().bigText(text))

                .setContentTitle(
                        context.getResources().getString(R.string.app_name))

                .setSound(sound)
                .setContentText(text).setAutoCancel(true)
                .setContentIntent(resultPendIntent).build();

        final NotificationManager notificManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificManager.notify(0, notify);
    }
}
