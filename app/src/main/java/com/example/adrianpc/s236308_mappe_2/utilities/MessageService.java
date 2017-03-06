package com.example.adrianpc.s236308_mappe_2.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.database.Database;

import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by bruker on 27-Oct-16.
 */

public class MessageService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Database db = new Database(this);
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
        Iterator<Contact> contacts = db.getBirthdayContacts(date).iterator();
        while(contacts.hasNext()) {
            Contact c = contacts.next();
            Toast.makeText(this, (c.getMessage().equals("")?getString(R.string.DEFAULTBDT):c.getMessage()), Toast.LENGTH_SHORT).show();

        }
        return super.onStartCommand(intent, flags, startId);
    }
}
