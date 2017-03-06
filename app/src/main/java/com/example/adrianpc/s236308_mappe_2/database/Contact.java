package com.example.adrianpc.s236308_mappe_2.database;

import java.io.Serializable;

/**
 * Created by Adrian PC on 14/10/2016.
 */

public class Contact implements Serializable{

    private int _ID;
    private String name, birthdate, phonenumber, message;
    private byte[] userImageResource;

    public Contact(String name, String birthdate, byte[] userImageResource, String phonenumber) {
        this.name = name;
        this.birthdate = birthdate;
        this.userImageResource = userImageResource;
        this.phonenumber = phonenumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getUserImageResource() {
        return userImageResource;
    }

    public void setUserImageResource(byte[] userImageResource) {
        this.userImageResource = userImageResource;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return name + " " + birthdate + " " + phonenumber;
    }
}
