package com.example.adrianpc.s236308_mappe_2.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.utilities.Converter;
import com.example.adrianpc.s236308_mappe_2.utilities.Validator;

import java.io.File;
import java.util.Calendar;

public class CreateContactFragment extends Fragment {

    private CreateContactListener listener;
    private View view;
    private EditText name, phonenumber, birthday, message;
    private ImageView contactImg;
    private Contact contact;

    public CreateContactFragment(CreateContactListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_contact, container, false);
        name = (EditText) view.findViewById(R.id.create_fragment_name);
        phonenumber = (EditText) view.findViewById(R.id.create_fragment_phone);
        birthday = (EditText) view.findViewById(R.id.create_fragment_birthday);
        contactImg = (ImageView) view.findViewById(R.id.create_contact_image);
        message = (EditText) view.findViewById(R.id.create_birthday_message);
        setDatePickerListener();
        return view;
    }

    public Contact getContact() {
        return contact;
    }

    public void setImage(Bitmap selectedBitmap) {
        contactImg.setImageBitmap(selectedBitmap);
        contactImg.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public Contact tryContact() {
        String n = name.getText().toString();
        String b = birthday.getText().toString();
        String p = phonenumber.getText().toString();
        String msg = Validator.testContactInput(n,b,p,getContext());
        if(msg != null) {
            new AlertDialog.Builder(getActivity()).setNeutralButton(android.R.string.ok, null)
                    .setMessage(msg).show();
            return null;
        } else {
            Bitmap bitmap = ((BitmapDrawable)contactImg.getDrawable()).getBitmap();
            contact = new Contact(n, b, Converter.getImageBytes(bitmap), p);
            contact.setMessage(message.getText().toString());
            return contact;
        }
    }

    private void setDatePickerListener() {
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    boolean fired = false;
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(!fired) {
                            StringBuilder stringBuilder = new StringBuilder().append(Converter.convertToDateString(day, month+1, year));
                            int[] d = Converter.convertDateString(stringBuilder.toString());
                            if (Validator.validateBirthdate(d[2], d[1]-1, d[0])) {
                                new AlertDialog.Builder(getActivity()).setNeutralButton(android.R.string.ok, null)
                                        .setMessage(getString(R.string.INVALID_BIRTHDAY)).show();
                                fired = true;
                            } else {
                                birthday.setText(stringBuilder.toString());
                            }
                        }
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE)).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface CreateContactListener {
        void onCamClick();
    }


}
