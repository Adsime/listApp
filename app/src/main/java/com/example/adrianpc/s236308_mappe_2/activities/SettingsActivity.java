package com.example.adrianpc.s236308_mappe_2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.database.Database;
import com.example.adrianpc.s236308_mappe_2.fragments.MenuFragment;
import com.example.adrianpc.s236308_mappe_2.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity implements MenuFragment.MenuListener {

    private MenuFragment menuFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        menuFragment = new MenuFragment(this, MenuFragment.MENU_FOR_MAIN);
        settingsFragment = new SettingsFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.settings_menu_container, menuFragment).commit();
        getFragmentManager().beginTransaction().add(R.id.settings_fragment_container, settingsFragment).commit();
    }

    @Override
    public void onInteraction(int interactionID) {
        switch (interactionID) {
            case MenuFragment.EXIT_ID:{
                onBackPressed();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
