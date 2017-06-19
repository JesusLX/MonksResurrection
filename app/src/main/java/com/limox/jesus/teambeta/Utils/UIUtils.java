package com.limox.jesus.teambeta.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.limox.jesus.teambeta.R;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.FileNotFoundException;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Jesus on 27/04/2017.
 */

public class UIUtils {

    public static void hideKeyboard(Activity activity, View view) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText())
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void loadImage(Context context, String imgUrl, ImageView ivImage) {
        Picasso.with(context).load(imgUrl).into(ivImage);
    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    public static void snackBar(View view, String s) {
        Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
    }

    public static void snackBar(View view, int s) {
        Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showColorPicker(Activity context, ColorPickerSwatch.OnColorSelectedListener onColorSelectedListener, int selectedColor) {
        int[] mColors = context.getResources().getIntArray(R.array.materialColors);

        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                mColors,
                selectedColor == -1 ? mColors[0] : selectedColor,
                5, // Number of columns
                ColorPickerDialog.SIZE_SMALL,
                true // True or False to enable or disable the serpentine effect
                //0, // stroke width
                //Color.BLACK // stroke color
        );
        dialog.setOnColorSelectedListener(onColorSelectedListener);
        dialog.show(context.getFragmentManager(), "colors");

    }

    public static int parseColor(String color) {
        return Color.parseColor("#" + color);
    }
}
