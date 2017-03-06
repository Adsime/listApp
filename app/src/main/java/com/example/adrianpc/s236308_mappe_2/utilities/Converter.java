package com.example.adrianpc.s236308_mappe_2.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by bruker on 16-Oct-16.
 */

public class Converter {

    public static final String splitter = "/|-";

    public static int[] convertDateString(String date) {
        String[] s = date.split(splitter);
        int[] returnValue = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            returnValue[i] = Integer.parseInt(s[i]);
        }
        return returnValue;
    }

    public static String convertToDateString(int day, int month, int year) {
        return new StringBuilder().append(day).append("/").append(month).append("-").append(year).toString();
    }

    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
