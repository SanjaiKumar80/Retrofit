package com.app.assesment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.assesment.api.RetrofitClient;
import com.app.assesment.utils.Constant;
import com.app.assesment.utils.SessionManagment;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.OkHttpClient;

public class ProfileActivity extends AppCompatActivity {
    SessionManagment session;
    private static final String TAG = "ProfileActivity";
    OkHttpClient client;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap;
    Button btnLogout;
    ImageButton btnedit;
    ImageButton btn;
    String email;
    TextView user_email, user_mobile, user_firstname, user_lastname;
    Constant cont = new Constant();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = new OkHttpClient();


        btn = findViewById(R.id.back_button);
        user_firstname = findViewById(R.id.lblname);
        user_lastname = findViewById(R.id.lbllastname);
        user_mobile = findViewById(R.id.lbluser_mobile);
        user_email = findViewById(R.id.lblEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnedit = findViewById(R.id.btn_edit);
        session = new SessionManagment(getApplicationContext());
        HashMap<String, String> userdetails = session.getSessionDetails();
        email = userdetails.get(SessionManagment.EMAIL);
        Log.d(TAG, "user_emailemail: " + email);
        User_Profile();
        String firstname = userdetails.get(SessionManagment.FIRSTNAME);
        Log.d(TAG, "firstname: " + firstname);
        user_firstname.setText(firstname);
        String lastname = userdetails.get(SessionManagment.LASTNAME);
        Log.d(TAG, "lastname: " + lastname);

        user_lastname.setText(lastname);
        String mobilenum = userdetails.get(SessionManagment.MOBILE);
        Log.d(TAG, "mobilenum: " + mobilenum);

        user_mobile.setText(mobilenum);
        email = userdetails.get(SessionManagment.EMAIL);
        Log.d(TAG, "user_emailemail: " + email);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Profile();
            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, Profileupdate.class);
                startActivity(i);
                finish();
            }
        });
        session.checkLogin();


        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.d(TAG, "logout: ");
                session.logoutUser();
                finish();
            }
        });
    }

    private void User_Profile() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        retrofit2.Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .Profile(email);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                final String res = response.body().toString();
                Log.d(TAG, " Response " + res);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();

                        try {

                            JSONObject object = new JSONObject(res);
                            String email = object.getString("email");
                            user_email.setText(email);
                            String password = object.getString("password");
                            Log.d(TAG, "emailprofile: " + email);
                            String firstname = object.getString("first_name");
                            user_firstname.setText(firstname);

                            String lastname = object.getString("last_name");
                            user_lastname.setText(lastname);

                            String mobile_no = object.getString("mobile_no");
                            user_mobile.setText(mobile_no);

                            Log.d(TAG, "mobilenum: " + mobile_no);
                            session.updateLoginSession(email, mobile_no, firstname, lastname);

                            cont.showToastMessage(ProfileActivity.this,object.getString("first_name")+"profile");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                cont.showToastMessage(getApplicationContext(), t.getMessage());
            }
        });

/*        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .build();
        Log.d(TAG, "user_profile: " + email);
        Request request = new Request.Builder()
                .url(cont.profileURl)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        cont.showToastMessage(ProfileActivity.this, "Something went wrong, please try again");

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.d(TAG, " Response " + res);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();

                        try {

                            JSONObject object = new JSONObject(res);
                            String email = object.getString("email");
                            user_email.setText(email);
                            String password = object.getString("password");
                            Log.d(TAG, "emailprofile: " + email);
                            String firstname = object.getString("first_name");
                            user_firstname.setText(firstname);

                            String lastname = object.getString("last_name");
                            user_lastname.setText(lastname);

                            String mobile_no = object.getString("mobile_no");
                            user_mobile.setText(mobile_no);

                            Log.d(TAG, "mobilenum: " + mobile_no);
                            session.updateLoginSession(email, mobile_no, firstname, lastname);

                            cont.showToastMessage(ProfileActivity.this, object.getString("message"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
