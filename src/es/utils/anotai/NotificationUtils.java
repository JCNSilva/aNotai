package es.utils.anotai;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import es.main.anotai.AnotaiBroadcast;

public class NotificationUtils {

	private static final int ZERO_SECOND = 0;

	public static void createNotifications(Calendar calendar, int day, int month, int year, int hour, int minute,
			Context context, String description) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, day - 1, hour, minute, ZERO_SECOND);

		calendar.set(year, month, day, hour, minute, ZERO_SECOND);

		notifyIn(calendar, description, context);

		if (calendar.get(Calendar.DAY_OF_YEAR) < calendar1.get(Calendar.DAY_OF_YEAR)) {
			notifyIn(calendar1, description, context);
		} else {
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(year, month, day - 2, hour, minute, ZERO_SECOND);

			if (calendar.get(Calendar.DAY_OF_YEAR) < calendar2.get(Calendar.DAY_OF_YEAR)) {
				notifyIn(calendar2, description, context);
			}
		}
	}

	private static void notifyIn(Calendar calendar, String description, Context context) {
		Intent myIntent = new Intent(context, AnotaiBroadcast.class);
		myIntent.putExtra("description_exam", description);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
	}

}
