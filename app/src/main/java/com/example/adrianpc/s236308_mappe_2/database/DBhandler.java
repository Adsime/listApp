package com.example.adrianpc.s236308_mappe_2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruker on 16-Oct-16.
 */

public class DBhandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Contact_Database";
    public static final String TABLE_NAME = "Contacts";
    public static final String KEY_ID = "_ID";
    public static final String KEY_NAME = "Name";
    public static final String KEY_PHONE_NR = "Phonenumber";
    public static final String KEY_BIRTHDAY = "Birthday";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_MESSAGE = "Message";
    public static final String[] ALL_COLUMNS = {KEY_ID, KEY_NAME, KEY_PHONE_NR, KEY_BIRTHDAY, KEY_IMAGE};
    public static final int VERSION = 3;

    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "create table " + TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_NAME + " text," +
                KEY_PHONE_NR + " text," +
                KEY_BIRTHDAY + " text,"  +
                KEY_IMAGE + " blob," +
                KEY_MESSAGE + " text);";

        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_PHONE_NR, contact.getPhonenumber());
        contentValues.put(KEY_BIRTHDAY, contact.getBirthdate());
        contentValues.put(KEY_IMAGE, contact.getUserImageResource());
        contentValues.put(KEY_MESSAGE, contact.getMessage());
        db.update(TABLE_NAME, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(contact.get_ID())});
        db.close();
    }

    public List<Contact> getContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + KEY_NAME, null);
        ArrayList<Contact> list = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getString(2));
                contact.set_ID(cursor.getInt(0));
                contact.setMessage(cursor.getString(5));
                list.add(contact);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Contact> getBirthdayContacts(String date) {
        String[] dm = date.split("-");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +
                " where " + KEY_BIRTHDAY + " like '" + "%" + dm[0] + "%" + "' order by " + KEY_NAME, null);
        ArrayList<Contact> list = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getString(2));
                contact.set_ID(cursor.getInt(0));
                contact.setMessage(cursor.getString(5));
                list.add(contact);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void updateGeneralMessage(String oldString, String newString) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MESSAGE, newString);
        db.update(TABLE_NAME, contentValues, KEY_MESSAGE + " = ?", new String[]{oldString});
        db.close();
    }

    public void removeContact(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        db.close();
    }

    public void addNewContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE_NR , contact.getPhonenumber());
        values.put(KEY_BIRTHDAY, contact.getBirthdate());
        values.put(KEY_IMAGE, contact.getUserImageResource());
        values.put(KEY_MESSAGE, contact.getMessage());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
