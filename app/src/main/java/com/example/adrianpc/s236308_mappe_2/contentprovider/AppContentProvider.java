package com.example.adrianpc.s236308_mappe_2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.example.adrianpc.s236308_mappe_2.database.DBhandler;
/**
 * Created by bruker on 29-Oct-16.
 */

public class AppContentProvider extends ContentProvider {

    public static final String KEY_ID = "_ID";
    public static final String KEY_NAME = "Name";
    public static final String KEY_PHONE_NR = "Phonenumber";
    public static final String KEY_BIRTHDAY = "Birthday";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_MESSAGE = "Message";
    public static final String TABLE_NAME = "Contacts";
    public static final String PROVIDER = "com.example.adrianpc.s236308_mappe_2.contentprovider";
    private static final int CONTACT = 1;
    private static final int CONTACTS = 2;
    public static final Uri CONTENT_URI = Uri.parse("content://"+PROVIDER+"/contact");
    private static final UriMatcher matcher; static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER, "contact", CONTACTS);
        matcher.addURI(PROVIDER, "contact/#", CONTACT);
    }
    private DBhandler db;

    @Override
    public boolean onCreate() {
        db = new DBhandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase dbs = db.getWritableDatabase();
        switch (matcher.match(uri)) {
            case CONTACT: {
                return dbs.rawQuery("select * from " + TABLE_NAME + " where " + KEY_ID + "=" + uri.getPathSegments().get(1), null);
            } case CONTACTS: {
                return dbs.rawQuery("select * from " + TABLE_NAME + " order by " + KEY_NAME, null);
            } default: {
                throw new IllegalArgumentException("Invalid URI: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case CONTACTS: {
                return "vnd.android.cursor.dir/vnd.example.contact";
            }case CONTACT: {
                return "vnd.android.cursor.item/vnd.example.contact";
            } default: {
                throw new IllegalArgumentException(uri.toString());
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
