package com.example.adrianpc.s236308_mappe_2.utilities;

import android.content.Context;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.database.Contact;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by bruker on 16-Oct-16.
 */

public class Validator {

    public static final String nameReg = "^[a-zA-ZæøåÆØÅ ]{2,40}";
    public static final String phoneReg = "[0-9]{8}|(00|\\+)47[0-9]{8}";

    public static boolean check(String toBeChecked, String regex) {
        return toBeChecked.matches(regex);
    }

    public static boolean validateBirthdate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cdate = c.get(Calendar.DATE);
        return (year > cyear || year == cyear && month > cmonth || year == cyear && month == cmonth && day > cdate);
    }

    public static boolean validateContact(Contact contact) {
        return contact.getName().matches(nameReg) &&
                String.valueOf(contact.getPhonenumber()).matches(phoneReg);
    }

    public static String testContactInput(String name, String birthdate, String phonenumber, Context context) {
        String output = context.getString(R.string.CONTACT_MISSTAKES) + " ";
        ArrayList<String> list = new ArrayList<>();
        if(!name.matches(nameReg)) {
            list.add(context.getString(R.string.NAME));
        } if(!phonenumber.matches(phoneReg)) {
            list.add(context.getString(R.string.PHONENR));
        } if(birthdate.equals("")) {
            list.add(context.getString(R.string.BIRTHDAY));
        }
        if(list.size() == 0) return null;
        Iterator it = list.iterator();
        int length = list.size();
        while(it.hasNext()) {
            output += it.next();
            length--;
            if(length > 1) {
                output += ", ";
            } else if(length == 1){
                output += " " + context.getString(R.string.AND) + " ";
            } else {
                output += ".";
            }
        }
        return output;
    }
}
