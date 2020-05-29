package com.app.assesment.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.app.assesment.MainActivity;
import com.app.assesment.model.Url;

import java.util.HashMap;

public class SessionManagment {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Context _context;
    private static final String PREF_NAME = "Login-Details";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String PASSWORD = "password";
    public static final String MOBILE = "mobile_no";
    public static final String FIRSTNAME = "first_name";
    public static final String LASTNAME = "last_name";
    private static final String IS_LOGIN = "IsLoggedIn";
    int PRIVATE_MODE = 0;

    public SessionManagment(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void createLoginSession(String uName, String uPassword, String mobile, String firstname, String lastname) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(EMAIL, uName);
        editor.putString(PASSWORD, uPassword);
        editor.putString(MOBILE, mobile);
        editor.putString(FIRSTNAME, firstname);
        editor.putString(LASTNAME, lastname);
        editor.commit();
    }


    public void registerDetails(EditText editemail, EditText editpassword, EditText editmobilno, EditText editfirstName, EditText editlastname) {
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(EMAIL, Url.isString(editemail));
        editor.putString(PASSWORD, Url.isString(editpassword));
        editor.putString(MOBILE, Url.isString(editmobilno));
        editor.putString(FIRSTNAME, Url.isString(editfirstName));
        editor.putString(LASTNAME, Url.isString(editlastname));
        editor.commit();
    }

    public HashMap<String, String> getSessionDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(PASSWORD, pref.getString(PASSWORD, null));
        user.put(MOBILE, pref.getString(MOBILE, null));
        user.put(FIRSTNAME, pref.getString(FIRSTNAME, null));
        user.put(LASTNAME, pref.getString(LASTNAME, null));
        return user;
    }

    public void checkLogin() {
        if (!this.IsLoggedIn()) {
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }

    public void updateLoginSession(String email, String mobile_no, String first_name, String last_name) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(EMAIL, email);
        //  editor.putString(PASSWORD,password);
        editor.putString(MOBILE, mobile_no);
        editor.putString(FIRSTNAME, first_name);
        editor.putString(LASTNAME, last_name);

        editor.commit();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean IsLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


}
