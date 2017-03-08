package alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import id.sacredgeeks.ethereal.DialogMissAlarm;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by SacredGeeks on 3/8/2017.
 */

    public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, DialogMissAlarm.class));
        wl.release();
    }

    public void setAlarm(Context context, long time, int id)
    {
        Intent i = new Intent(context, MyAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, i, 0);
        AlarmManager am =( AlarmManager)context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
        Toast.makeText(context, "set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, int id)
    {
        Intent intent = new Intent(context, MyAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
