package es.utils.anotai;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.main.anotai.AnotaiBroadcast;

public class NotificationUtils {

	public static final int ZERO_SECOND = 0;

	public static void createNotifications(Calendar calendarExam, Context context, String description) {
		int year, month, day, hour, minute;
		year = calendarExam.get(Calendar.YEAR);
		month = calendarExam.get(Calendar.MONTH);
		day = calendarExam.get(Calendar.DAY_OF_MONTH);
		hour = calendarExam.get(Calendar.HOUR_OF_DAY);
		minute = calendarExam.get(Calendar.MINUTE);
		
		calendarExam.set(Calendar.SECOND, ZERO_SECOND);
		
		Calendar today = Calendar.getInstance();
		
		/*
		 * Talvez fosse mais interessante pedir ao usuário a hora que ele acha 
		 * mais adequada para receber as notificações para suas atividades, pois 
		 * hoje, as notificações antecipadas acontecem no mesmo horário da atividade.
		 */

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, day - 1, hour, minute, ZERO_SECOND);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, day - 2, hour, minute, ZERO_SECOND);

		notifyIn(calendarExam, description, context);

		if (!calendar1.before(today)) {
			notifyIn(calendar1, description, context);
		}

		if (!calendar2.before(today)) {
			notifyIn(calendar2, description, context);
		}
	}

	private static void notifyIn(Calendar calendar, String description, Context context) {
		Intent myIntent = new Intent(context, AnotaiBroadcast.class);
		myIntent.putExtra("description_exam", description);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

		Log.v("Notity", "NOTIFICAÇÃO SETADA PARA:  " + calendar.toString());
	}

}
