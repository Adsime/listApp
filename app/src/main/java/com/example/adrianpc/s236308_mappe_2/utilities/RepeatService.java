package com.example.adrianpc.s236308_mappe_2.utilities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.activities.TimePickerPreference;
import com.example.adrianpc.s236308_mappe_2.fragments.SettingsFragment;

import java.util.Calendar;

/**
 * Created by bruker on 27-Oct-16.
 */

public class RepeatService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean running = Datahandler.loadData("running", getApplicationContext()).equals("");
        if(running) {
            //Trigger time
            Calendar cal = Calendar.getInstance();
            String time = Datahandler.loadData(SettingsFragment.TIME, this);
            String[] timepoint = (time.equals("")) ? new String[]{"00", "00"} : time.split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timepoint[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(timepoint[1]));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            if(Calendar.getInstance().getTimeInMillis() > cal.getTimeInMillis()) {
                cal.add(Calendar.YEAR, 1);
            }

            PendingIntent pendingIntent = PendingIntent.getService(this, 0, new Intent(this, MessageService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        return Service.START_NOT_STICKY;
    }
}
