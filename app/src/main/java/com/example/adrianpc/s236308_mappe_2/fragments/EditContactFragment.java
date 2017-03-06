package com.example.adrianpc.s236308_mappe_2.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.Calendar;

public class EditContactFragment extends Fragment {

    private Contact current;
    private View view;
    private EditText name, birthday, phonenumber, message;
    private ImageView contactImage;
    private boolean editMode;

    public EditContactFragment(Contact current) {
        this.current = current;
    }

    private void setContact() {
        name.setText(current.getName());
        phonenumber.setText(String.valueOf(current.getPhonenumber()));
        birthday.setText(current.getBirthdate());
        message.setText(current.getMessage());
        if(current.getUserImageResource() != null) {
            setImage(Converter.getImage(current.getUserImageResource()));
        }
    }

    public void setImage(Bitmap selectedBitmap) {
        contactImage.setImageBitmap(selectedBitmap);
        Bitmap b = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_placeholder);
        if (!selectedBitmap.sameAs(b)) {
            contactImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        editMode = false;
        view = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        name = (EditText) view.findViewById(R.id.edit_fragment_name);
        message = (EditText) view.findViewById(R.id.edit_birthday_message);
        birthday = (EditText) view.findViewById(R.id.edit_fragment_birthday);
        phonenumber = (EditText) view.findViewById(R.id.edit_fragment_phone);
        contactImage = (ImageView) view.findViewById(R.id.edit_contact_image);
        setContact();
        return view;
    }

    public Contact fieldcheck() {
        if(editMode) return null;
        String msg = Validator.testContactInput(name.getText().toString(), birthday.getText().toString(), phonenumber.getText().toString(), getContext());
        if(msg != null) {
            msg += "\n"+getString(R.string.KEEP_CONTACT);
            new AlertDialog.Builder(getActivity()).setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    name.setText(current.getName());
                    phonenumber.setText(current.getPhonenumber());
                    birthday.setText(current.getBirthdate());
                    message.setText(current.getMessage());
                }
            }).setNegativeButton(getString(R.string.NO), null).setMessage(msg).show();
            changeEditMode();
            return null;
        } else {
            Bitmap bitmap = ((BitmapDrawable)contactImage.getDrawable()).getBitmap();
            Contact c = new Contact(name.getText().toString(), birthday.getText().toString(),
                    Converter.getImageBytes(bitmap), phonenumber.getText().toString());
            c.setMessage(message.getText().toString());
            c.set_ID(current.get_ID());
            return c;
        }
    }

    public boolean changeEditMode() {
        editMode = !editMode;
        editTextFix(name);
        editTextFix(phonenumber);
        editTextFix(message);
        birthday.setOnClickListener((!editMode)?null:new View.OnClickListener() {
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
        return editMode;
    }

    private void editTextFix(EditText editText) {
        editText.setFocusable(editMode);
        editText.setFocusableInTouchMode(editMode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}
