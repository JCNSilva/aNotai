package es.main.anotai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AnotaiBroadcast extends BroadcastReceiver {
    private static String taskDescription = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        taskDescription = intent.getExtras().getString("description_exam");
        Intent alarmService = new Intent(context, AlarmService.class);
        context.startService(alarmService);
    }

    public static String getTaskDescrition() {
        return taskDescription;
    }
}
