package com.example.adrianpc.s236308_mappe_2.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by bruker on 26-Oct-16.
 */

public class ImageCropActivity extends Activity {
    private final int GALLERY_ACTIVITY_CODE = 200;
    private final int RESULT_CROP = 400;
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent gallery_Intent = new Intent(getApplicationContext(), ImageSelectorActivity.class);
        startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                picturePath = data.getStringExtra("picturePath");
                performCrop(picturePath);
            }
        }

        if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                Intent returnFromGalleryIntent = new Intent();
                returnFromGalleryIntent.putExtra("bitmap",selectedBitmap);
                setResult(RESULT_OK,returnFromGalleryIntent);
                finish();
            }
        }
    }

    private void performCrop(String picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            cropIntent.putExtra("crop", "true");
            Display display = getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int aspectX = point.x;
            int aspectY = (int)(point.y*0.4);
            System.out.println(aspectX + " " + aspectY);
            cropIntent.putExtra("aspectX", aspectX);
            cropIntent.putExtra("aspectY", aspectY);
            cropIntent.putExtra("outputX", point.x);
            cropIntent.putExtra("outputY", (int)(point.y*0.4));
            cropIntent.putExtra("scaleUpIfNeeded", true);

            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, RESULT_CROP);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}