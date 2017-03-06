package com.example.adrianpc.s236308_mappe_2.utilities;

import android.content.Context;
import android.content.Intent;

import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.database.Database;

import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by bruker on 27-Oct-16.
 */

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RepeatService.class);
        context.startService(i);
    }
}
