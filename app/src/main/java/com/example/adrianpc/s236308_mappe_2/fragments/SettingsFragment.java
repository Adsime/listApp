package com.example.adrianpc.s236308_mappe_2.fragments;

import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.View;
import android.widget.Toast;


import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.activities.TimePickerPreference;
import com.example.adrianpc.s236308_mappe_2.database.Database;
import com.example.adrianpc.s236308_mappe_2.utilities.Datahandler;
import com.example.adrianpc.s236308_mappe_2.utilities.RepeatService;

import java.util.Calendar;

public class SettingsFragment extends PreferenceFragment {

    private String currentMessage;
    public static final String TIME = "time", MESSAGE = "message";
    private Database db;
    private View view;
    private EditTextPreference message;
    private SwitchPreference serviceSwitch;
    private Context context;
    private TimePickerPreference time;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment(Context context) {
        this.context = context;
        db = new Database(context);
        view = ((Activity)context).getWindow().getDecorView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);
        time = (TimePickerPreference) getPreferenceManager().findPreference("time_preference");
        message = (EditTextPreference) getPreferenceManager().findPreference("message_preference");
        serviceSwitch = (SwitchPreference) getPreferenceManager().findPreference("on_off_preference");
        setValues();
        setListeners();
    }

    private void setListeners() {
        time.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                time.setSummary((String) o);
                Datahandler.saveData(TIME, (String)o, context);
                Toast.makeText(context, (String)o, Toast.LENGTH_SHORT).show();
                context.stopService(new Intent(context, RepeatService.class));
                context.startService(new Intent(context, RepeatService.class));
                return true;
            }
        });

        message.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = (String)o;
                db.updateGeneralMessage(currentMessage, s);
                message.setSummary((s.equals(""))?getString(R.string.DEFAULTBDT):s);
                Datahandler.saveData(MESSAGE, ((s.equals(""))?getString(R.string.DEFAULTBDT):s), context);
                currentMessage = s;
                return true;
            }
        });

        serviceSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                System.out.println(o);
                if((boolean)o) {
                    Datahandler.saveData("running", "", context);
                    context.startService(new Intent(context, RepeatService.class));
                    Toast.makeText(context, getString(R.string.SERVICE_STARTED), Toast.LENGTH_SHORT).show();
                } else {
                    Datahandler.saveData("running", "false", context);
                    context.stopService(new Intent(context, RepeatService.class));
                    Toast.makeText(context, getString(R.string.SERVICE_STOPPED), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void setValues() {
        currentMessage = (Datahandler.loadData(MESSAGE, context).equals("")?
                getString(R.string.DEFAULTBDT):Datahandler.loadData(MESSAGE, context));
        String t = Datahandler.loadData(TIME, context);
        String defaultTime = "00:00";
        time.setSummary((t.equals("")?defaultTime:t));
        if(t != "") {
            time.setPersistent(true);
            time.setDefaultValue(t);
            time.setText(t);
        } else {
            time.setPersistent(true);
            time.setDefaultValue(defaultTime);
            time.setText(defaultTime);
        }
        t = Datahandler.loadData(MESSAGE, context);
        message.setSummary((t.equals(""))? getString(R.string.DEFAULTBDT):t);
        message.setText((t.equals(""))? getString(R.string.DEFAULTBDT):t);

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
