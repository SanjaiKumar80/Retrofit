package com.app.assesment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.assesment.api.RetrofitClient;
import com.app.assesment.model.Url;
import com.app.assesment.utils.Constant;
import com.app.assesment.utils.SessionManagment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Profileupdate extends AppCompatActivity {
    SessionManagment session;
    private static final String TAG = "ProfileupdateActivity";
    OkHttpClient okHttpClient;
    TextView profile_password, profile_email;
    EditText profile_mobile, profile_firstname, profile_lastname;
    private HashMap<String, String> hashMap;
    Button btnsave;
    ImageButton btn;
    TextInputLayout inputLayoutMobile, inputLayoutFirstname, inputLayoutLastname, inputLayoutEmail, inputLayoutpassword;
    Constant cont = new Constant();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        btn = findViewById(R.id.back_button);
        okHttpClient = new OkHttpClient();
        profile_firstname = findViewById(R.id.profile_firstname);
        profile_lastname = findViewById(R.id.profile_lastname);
        profile_email = findViewById(R.id.profile_email);
        profile_mobile = findViewById(R.id.profile_mobile);
        inputLayoutEmail = findViewById(R.id.inputprofile_email);
        inputLayoutMobile = findViewById(R.id.inputprofilenumber);
        inputLayoutFirstname = findViewById(R.id.inputprofilefirstname);
        inputLayoutLastname = findViewById(R.id.inputprofilelastname);
        profile_firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (profile_firstname.getText().toString().trim().length() > 0) {
                    inputLayoutFirstname.setError(null);

                }
            }
        });
        profile_lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (profile_lastname.getText().toString().trim().length() > 0) {
                    inputLayoutLastname.setError(null);

                }
            }
        });

        profile_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (profile_mobile.getText().toString().trim().length() > 0) {
                    inputLayoutMobile.setError(null);

                }
            }
        });


        btnsave = findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profileupdate.this, ProfileActivity.class);
                startActivity(i);
                finish();


            }
        });


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    RetrofitUserProfile();
                if (!validateFirstname()) {
                    return;
                }
                if (!validateNumber()) {
                    return;
                }
                if (!validateLastname()) {
                    return;
                }
              //  UserProfile();
                RetrofitUserProfile();

            }
        });

        session = new SessionManagment(this);
        Log.d(TAG, "session: " + session);
        hashMap = session.getSessionDetails();
        Log.d(TAG, "hashmap: " + hashMap);
        HashMap<String, String> user = session.getSessionDetails();

        String email = user.get(SessionManagment.EMAIL);
        String firstname = user.get(SessionManagment.FIRSTNAME);
        Log.d(TAG, "first_name: " + firstname);
        String lastname = user.get(SessionManagment.LASTNAME);
        String password = user.get(SessionManagment.PASSWORD);
        String mobile = user.get(SessionManagment.MOBILE);

        profile_email.setText(email);
        profile_email.setGravity(Gravity.CENTER_VERTICAL);

        profile_firstname.setText(firstname);
        profile_firstname.setGravity(Gravity.CENTER_VERTICAL);

        profile_lastname.setText(lastname);
        profile_lastname.setGravity(Gravity.CENTER_VERTICAL);

        profile_mobile.setText(mobile);
        profile_mobile.setGravity(Gravity.CENTER_VERTICAL);

    }


    public void RetrofitUserProfile() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
        String email = Url.isText(profile_email);
        String mobileNumber = Url.isString(profile_mobile);
        String first_name = Url.isString(profile_firstname);
        String last_name = Url.isString(profile_lastname);


        retrofit2.Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .ProfileUpdate(email, mobileNumber, first_name, last_name);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                final String res = response.body().toString();
                Log.d(TAG, "profileresponse: response =>" + res);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(res);
                            String profileemail = profile_email.getText().toString();
                            String profilemobile = profile_mobile.getText().toString();
                            String profilefirstname = profile_firstname.getText().toString();
                            String profilelastname = profile_lastname.getText().toString();
                            session.updateLoginSession(profileemail, profilemobile, profilefirstname, profilelastname);
                            Log.d(TAG, "updatedetails" + "" + profilefirstname + "" + profilelastname + "" + profilemobile);


                            if (jsonObject.getBoolean("status")) {
                                Log.d(TAG, "run: ");

                                Intent i = new Intent(Profileupdate.this, ProfileActivity.class);
                                startActivity(i);
                                finish();


                            }
                            cont.showToastMessage(getApplicationContext(), jsonObject.getString("message"));

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }


                    }

                });
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                cont.showToastMessage(getApplicationContext(), t.getMessage());
            }
        });


    }


/*   private void  UserProfile() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();


        RequestBody form = new FormBody.Builder()
                .add("email", Url.isText(profile_email))

                .add("mobile_no", Url.isString(profile_mobile))
                .add("first_name", Url.isString(profile_firstname))
                .add("last_name", Url.isString(profile_lastname))
                .build();


        Request request = new Request.Builder()
                .url(cont.profileupdate)
                .post(form)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();
                        cont.showToastMessage(getApplicationContext(), "Something went wrong, please try again");
                        //    Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();

                    }
                });

            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.d(TAG, "profileresponse: response =>" + res);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(res);

                            String profileemail = profile_email.getText().toString();
                            String profilemobile = profile_mobile.getText().toString();
                            String profilefirstname = profile_firstname.getText().toString();
                            String profilelastname = profile_lastname.getText().toString();
                            session.updateLoginSession(profileemail, profilemobile, profilefirstname, profilelastname);
                            Log.d(TAG, "updatedetails" + "" + profilefirstname + "" + profilelastname + "" + profilemobile);


                            if (jsonObject.getBoolean("status")) {
                                Log.d(TAG, "run: ");

                                Intent i = new Intent(Profileupdate.this, ProfileActivity.class);
                                startActivity(i);
                                finish();


                            }
                            cont.showToastMessage(getApplicationContext(), jsonObject.getString("message"));

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }


                    }

                });

            }
        });
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private boolean validateNumber() {
        String mobile = profile_mobile.getText().toString();

        if (mobile.isEmpty()) {
            inputLayoutMobile.setError("please Enter mobile number");
            requestFocus(mobile);
            return false;
        } else if (mobile.length() < 10) {
            inputLayoutMobile.setError("Invalid Number");
            return false;


        }

        return true;

    }

    private boolean validateFirstname() {
        String firstname = profile_firstname.getText().toString();

        if (firstname.isEmpty()) {
            inputLayoutFirstname.setError("First  Name is Mandatory");
            requestFocus(firstname);
            return false;
        }

        return true;

    }

    private boolean validateLastname() {
        String lastname = profile_lastname.getText().toString();

        if (lastname.isEmpty()) {
            inputLayoutLastname.setError("Last Name is Mandatory");
            requestFocus(lastname);
            return false;
        }

        return true;

    }


    private void requestFocus(String view) {
        if (view.isEmpty()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

