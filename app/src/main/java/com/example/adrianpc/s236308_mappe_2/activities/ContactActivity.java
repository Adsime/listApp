package com.example.adrianpc.s236308_mappe_2.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adrianpc.s236308_mappe_2.fragments.EditContactFragment;
import com.example.adrianpc.s236308_mappe_2.fragments.MenuFragment;
import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.database.Database;

public class ContactActivity extends AppCompatActivity implements MenuFragment.MenuListener {

    private EditContactFragment editContactFragment;
    private MenuFragment menuFragment;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(this);
        Contact contact = (Contact)getIntent().getSerializableExtra("contact");
        setContentView(R.layout.activity_edit_contact);
        menuFragment = new MenuFragment(this, MenuFragment.MENU_FOR_CONTACT);
        editContactFragment = new EditContactFragment(contact);
        getSupportFragmentManager().beginTransaction().add(R.id.edit_contact_info_container, editContactFragment, "")
                .add(R.id.edit_menu_container, menuFragment, "").commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onInteraction(int interactionID) {
        switch (interactionID) {
            case MenuFragment.EXIT_ID :{
                onBackPressed();
                break;
            } case MenuFragment.SAVE: {
                menuFragment.selectMenu(editContactFragment.changeEditMode()?menuFragment.MENU_FOR_EDIT:menuFragment.MENU_FOR_CONTACT);
                Contact c = editContactFragment.fieldcheck();
                if(c != null) {
                    db.updateContact(c);
                } else {
                    menuFragment.selectMenu(menuFragment.MENU_FOR_EDIT);
                    break;
                }
                break;
            }case MenuFragment.SELECT_IMAGE: {
                Intent intent = new Intent(getApplicationContext(), ImageCropActivity.class);
                startActivityForResult(intent, 200);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            Bitmap bitmap = data.getExtras().getParcelable("bitmap");
            editContactFragment.setImage(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
