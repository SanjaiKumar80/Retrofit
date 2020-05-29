package com.app.assesment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.app.assesment.api.RetrofitClient;
import com.app.assesment.model.Url;
import com.app.assesment.model.UserLogin;
import com.app.assesment.utils.Constant;
import com.app.assesment.utils.SessionManagment;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button Register, Login, forgotPassword;
    EditText objemail, objpassword;
    private static final String TAG = "MainActivity";
    SessionManagment management;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AppCompatCheckBox checkBox;
    TextInputLayout inputLayoutEmail, inputLayoutPassword;
    Constant cont = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inital();
        OnClick();
        management = new SessionManagment(getApplicationContext());
        if (management.IsLoggedIn()) {
            Log.d(TAG, "loggedin: " + management.IsLoggedIn());
            Intent s = new Intent(this, Feed.class);
            startActivity(s);
            this.finish();
        }
    }

    public void inital() {
        inputLayoutEmail = findViewById(R.id.emailinput);
        inputLayoutPassword = findViewById(R.id.passinput);
        checkBox = findViewById(R.id.check);
        Register = findViewById(R.id.idRegister);
        objemail = findViewById(R.id.idEmail);
        objpassword = findViewById(R.id.idPassword);
        Login = findViewById(R.id.idLogin);
        forgotPassword = findViewById(R.id.btn_forgotpassword);
        objemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (objemail.getText().toString().trim().length() > 0) {
                    inputLayoutEmail.setError(null);

                }
            }
        });
        objpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (objpassword.getText().toString().trim().length() > 0) {
                    inputLayoutPassword.setError(null);

                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    objpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    objpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void OnClick() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (!validateEmail()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                LogIn();

            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
            }
        });

    }

    private boolean validateEmail() {
        String email = objemail.getText().toString();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Enter valid Email Address");
            requestFocus(email);
            return false;
        } else {
            inputLayoutEmail.setError(null);
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        String password = objpassword.getText().toString();

        if (password.isEmpty()) {
            inputLayoutPassword.setError("please Enter a Password");
            requestFocus(password);
            return false;
        } else if (password.length() < 6) {
            inputLayoutPassword.setError("password should  atleast 6 characters");
            return false;
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
                    validateEmail();
                    break;
                case R.id.idPassword:
                    validatePassword();
                    break;
            }
        }

    }


    public void LogIn() {

        sharedPreferences = getApplicationContext().getSharedPreferences("Login-Details", 0);

        editor = sharedPreferences.edit();
        Call<UserLogin> call = RetrofitClient
                .getInstance()
                .getApi()
                .user_login(Url.isString(objemail), Url.isString(objpassword));
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                UserLogin dr = response.body();
                if (dr.getStatus() == true) {
                    String email_login = dr.getData().getEmail();
                    String password_login = dr.getData().getPassword();
                    String mobile_login = dr.getData().getMobileNo();
                    String first_name = dr.getData().getFirstName();
                    String last_name = dr.getData().getLastName();
                    management.createLoginSession(email_login, password_login, mobile_login, first_name, last_name);

                    cont.showToastMessage(MainActivity.this, dr.getMessage());

                    Intent i = new Intent(getApplicationContext(), Feed.class);
                    startActivity(i);

                } else {
                    cont.showToastMessage(MainActivity.this, dr.getMessage());

                }

            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                cont.showToastMessage(MainActivity.this, t.getMessage());

            }
        });
    }
}
