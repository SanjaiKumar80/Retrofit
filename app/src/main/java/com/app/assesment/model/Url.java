package com.app.assesment.model;

import android.widget.EditText;
import android.widget.TextView;

public class Url {

    public static String isString(EditText text) {
        return text.getText().toString().trim();
    }

    public static String isText(TextView text) {
        return text.getText().toString().trim();
    }

}
