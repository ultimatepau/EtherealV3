package alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import id.sacredgeeks.ethereal.DialogMissAlarm;
import id.sacredgeeks.ethereal.R;

/**
 * Created by SacredGeeks on 3/8/2017.
 */

    public class MyAlarm extends BroadcastReceiver {
    MediaPlayer player;
    @Override
    public void onReceive(Context context, Intent intent) {

        player = MediaPlayer.create(context, R.raw.alarm);
        player.start();
        
        Intent i = new Intent(context, DialogMissAlarm.class);
        i.putExtra("ket","Test");
        i.putExtra("jam","21.00");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(i);
    }
}
