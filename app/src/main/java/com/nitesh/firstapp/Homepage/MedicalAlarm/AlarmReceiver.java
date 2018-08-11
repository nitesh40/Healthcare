package com.nitesh.firstapp.Homepage.MedicalAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Nitesh on 8/10/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        Log.e("MyActivity", "In the receiver with " + state);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);

        context.startService(serviceIntent);
    }
}
