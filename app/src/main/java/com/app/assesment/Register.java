package com.app.assesment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.app.assesment.api.RetrofitClient;
import com.app.assesment.model.Url;
import com.app.assesment.model.UserRegister;
import com.app.assesment.utils.Constant;
import com.app.assesment.utils.SessionManagment;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Register extends AppCompatActivity {
    Button Sign, Login;
    EditText objemail, objmobilno, objfirstName, objlastname, objpassword;
    SessionManagment management;
    TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutMobile, inputLayoutFirstname, inputLayoutLastname;
    AppCompatCheckBox checkBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Constant cont = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Login = findViewById(R.id.button_login);
        Sign = findViewById(R.id.idSignUp);
        objemail = findViewById(R.id.idEmail);
        objmobilno = findViewById(R.id.idPhone);
        objfirstName = findViewById(R.id.idFirstname);
        objlastname = findViewById(R.id.idLastname);
        objpassword = findViewById(R.id.idPassword);
        checkBox = findViewById(R.id.check);
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
        objfirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (objfirstName.getText().toString().trim().length() > 0) {
                    inputLayoutFirstname.setError(null);

                }
            }
        });
        objlastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (objlastname.getText().toString().trim().length() > 0) {
                    inputLayoutLastname.setError(null);

                }
            }
        });
        objlastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (objlastname.getText().toString().trim().length() > 0) {
                    inputLayoutPassword.setError(null);

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
                    inputLayoutMobile.setError(null);

                }
            }
        });
        objmobilno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (objmobilno.getText().toString().trim().length() > 0) {
                    inputLayoutMobile.setError(null);

                }
            }
        });
        inputLayoutMobile = findViewById(R.id.inputname);
        inputLayoutEmail = findViewById(R.id.inputemail);
        inputLayoutPassword = findViewById(R.id.inputpass);
        inputLayoutMobile = findViewById(R.id.inputname);
        inputLayoutFirstname = findViewById(R.id.inputfirstname);
        inputLayoutLastname = findViewById(R.id.inputlastname);
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
        sharedPreferences = getApplicationContext().getSharedPreferences("Login-Details", 0);
        editor = sharedPreferences.edit();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFirstname()) {
                    return;
                }
                if (!validateLastname()) {
                    return;
                }

                if (!validateEmail()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }

                if (!validateNumber()) {
                    return;
                }
                RegisterCall();
//                management.registerDetails(objemail, objpassword, objmobilno, objfirstName, objlastname);
            }

            private boolean validateEmail() {
                String email = objemail.getText().toString();
                if (email.isEmpty() || !isValidEmail(email)) {
                    inputLayoutEmail.setError("Enter valid Email Address");
                    requestFocus(email);
                    return false;
                } else {
                    inputLayoutEmail.setError(null);
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

            private boolean validateNumber() {
                String mobile = objmobilno.getText().toString();

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
                String firstname = objfirstName.getText().toString();

                if (firstname.isEmpty()) {
                    inputLayoutFirstname.setError("First  Name is Mandatory");
                    requestFocus(firstname);
                    return false;
                }

                return true;

            }

            private boolean validateLastname() {
                String lastname = objlastname.getText().toString();

                if (lastname.isEmpty()) {
                    inputLayoutLastname.setError("Last  Name is Mandatory");
                    requestFocus(lastname);
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
                        case R.id.idPhone:
                            validateNumber();
                            break;
                        case R.id.idEmail:
                            validateEmail();
                            break;
                        case R.id.idPassword:
                            validatePassword();
                            break;
                    }
                }

            }

        });


    }


    public void RegisterCall() {


        Call<UserRegister> call = RetrofitClient
                .getInstance()
                .getApi()
                .user_register(Url.isString(objemail), Url.isString(objpassword), Url.isString(objmobilno), Url.isString(objfirstName), Url.isString(objlastname));

        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                UserRegister dr = response.body();
                cont.showToastMessage(Register.this, dr.getMessage());

                if (dr.getStatus() == true) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {
                cont.showToastMessage(Register.this, t.getMessage());

            }
        });
    }
}
