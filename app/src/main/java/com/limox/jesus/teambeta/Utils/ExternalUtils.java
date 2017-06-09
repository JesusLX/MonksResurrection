package com.limox.jesus.teambeta.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.limox.jesus.teambeta.R;

/**
 * Created by Jesus on 06/06/2017.
 */

public class ExternalUtils {
    public static void sendEmail(Context context, String email) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Using TeamBeta");
        try {
            context.startActivity(Intent.createChooser(i, context.getString(R.string.send_email)));
        } catch (android.content.ActivityNotFoundException ex) {
            UIUtils.toast(context, context.getString(R.string.no_email_app));
        }
    }
}
