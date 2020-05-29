package com.app.assesment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText edit_forgotpassword;
    private static final String TAG = "ForgotPassword";
    SessionManagment session;
    Button btnforgot;
    TextInputLayout layout_forgot;
    Constant cont = new Constant();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        edit_forgotpassword = findViewById(R.id.edit_forgot_password);
        layout_forgot = findViewById(R.id.input_forgotpass);
        btnforgot = findViewById(R.id.btn_forgotpassword);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateEmail()) {
                    return;
                } else {

                    retroresetUserPassword();
                }
            }
        });
        session = new SessionManagment(this);
    }

    private boolean ValidateEmail() {
        String email = edit_forgotpassword.getText().toString();


        if (email.isEmpty() || !isValidEmail(email)) {
            layout_forgot.setError("Enter valid Email Address");
            requestFocus(email);
            return false;
        } else {
            layout_forgot.setError(null);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(String view) {
        if (view.isEmpty()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.emailinput:
                    ValidateEmail();
                    break;
            }
        }

    }

    public void retroresetUserPassword() {

        retrofit2.Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .forgotPassword(Url.isString(edit_forgotpassword));

        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                final String java = response.body().toString();


                JSONObject object = null;
                try {
                    object = new JSONObject(java);
                    Log.d(TAG, "obj: " + object);

                    if (object.getBoolean("status")) {
                        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    cont.showToastMessage(ForgotPassword.this, object.getString("message"));


                } catch (JSONException e) {
                    cont.showToastMessage(ForgotPassword.this, "please try again later");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                cont.showToastMessage(ForgotPassword.this, t.getMessage());
            }
        });


    }

/*    public void resetUserPassword() {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", Url.isString(edit_forgotpassword))
                .build();
        Request request = new Request.Builder()
                .url(cont.profileURl)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ForgotPassword.this, "Something went wrong! Try again later", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String java = response.body().string();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {


                        try {

                            JSONObject object = new JSONObject(java);
                            Log.d(TAG, "obj: " + object);

                            if (object.getBoolean("status")) {
                                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            cont.showToastMessage(ForgotPassword.this, object.getString("message"));
                            //    Toast.makeText(ForgotPassword.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            cont.showToastMessage(ForgotPassword.this, "please try again later");
                            //   Toast.makeText(ForgotPassword.this, "please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }*/

}

