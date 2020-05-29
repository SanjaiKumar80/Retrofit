package com.app.assesment.utils;

import android.content.Context;
import android.widget.Toast;

public class Constant {

    public final static String profileURl = "http://tonyrajesh.com/androidserver.tonyrajesh.com/android-sridevi/profile.php";
    public final static String profileupdate = "http://tonyrajesh.com/androidserver.tonyrajesh.com/android-sridevi/profile_update.php";
  //  public final static String Timing ="http://www.clive.in/android_hag/timings.json";

    public static void showToastMessage(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
