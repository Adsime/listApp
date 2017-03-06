package com.example.adrianpc.s236308_mappe_2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.database.Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Database db = new Database(context);
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
        ArrayList<Contact> contacts = (ArrayList<Contact>) db.getBirthdayContacts(date);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            if(contacts.size() < 1) {
                views.setTextViewText(R.id.widget_info, context.getString(R.string.NO_BIRTHDAYS));
                appWidgetManager.updateAppWidget(appWidgetId, views);
                continue;
            } else {
                Iterator<Contact> it = contacts.iterator();
                String s = "";
                while (it.hasNext()) {
                    Contact c = it.next();
                    int y = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(c.getBirthdate().split("-")[1]);
                    s += c.getName() + " " + y +"\n";
                }
                views.setTextViewText(R.id.widget_info, s);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }

}

