package com.example.adrianpc.s236308_mappe_2.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adrianpc.s236308_mappe_2.fragments.CreateContactFragment;
import com.example.adrianpc.s236308_mappe_2.fragments.MenuFragment;
import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.database.Database;

import java.io.File;

public class CreateActivity extends AppCompatActivity implements MenuFragment.MenuListener, CreateContactFragment.CreateContactListener {

    private MenuFragment menuFragment;
    private CreateContactFragment createContactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuFragment = new MenuFragment(this, MenuFragment.MENU_FOR_CREATE);
        createContactFragment = new CreateContactFragment(this);
        setContentView(R.layout.activity_create);
        getSupportFragmentManager().beginTransaction().add(R.id.create_menu_container, menuFragment, "")
                .add(R.id.create_contact_container, createContactFragment, "").commit();
    }

    @Override
    public void onInteraction(int interactionID) {
        switch (interactionID) {
            case MenuFragment.EXIT_ID: {
                onBackPressed();
                break;
            }case MenuFragment.SAVE: {
                Contact c = createContactFragment.tryContact();
                if(c == null) {
                    return;
                }
                Database db = new Database(this);
                db.addContact(c);
                Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
                sendBroadcast(intent);
                onBackPressed();
                break;
            } case MenuFragment.SELECT_IMAGE: {
                Intent intent = new Intent(getApplicationContext(), ImageCropActivity.class);
                startActivityForResult(intent, 200);
            }
        }
    }

    @Override
    public void onCamClick() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            Bitmap bitmap = data.getExtras().getParcelable("bitmap");
            createContactFragment.setImage(bitmap);
        }
    }
}
