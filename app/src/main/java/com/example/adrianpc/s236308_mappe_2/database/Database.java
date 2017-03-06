package com.example.adrianpc.s236308_mappe_2.database;

import android.content.Context;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian PC on 14/10/2016.
 */

public class Database {

    private DBhandler dBhandler;
    private List<Contact> contacts;

    public Database(Context context) {
        dBhandler = new DBhandler(context);
        contacts = dBhandler.getContacts();
    }

    public List<Contact> getBirthdayContacts(String date) {
        return dBhandler.getBirthdayContacts(date);
    }

    public void updateGeneralMessage(String oldString, String newString) {
        dBhandler.updateGeneralMessage(oldString, newString);
    }

    public void deleteContact(int id) {
        dBhandler.removeContact(id);
    }

    public void addContact(Contact contact) {
        dBhandler.addNewContact(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void updateContact(Contact contact) {
        dBhandler.updateContact(contact);
    }
}
