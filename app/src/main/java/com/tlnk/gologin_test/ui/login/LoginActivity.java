package com.tlnk.gologin_test.ui.login;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tlnk.gologin_test.R;
import com.tlnk.gologin_test.pojo.SuccessfulResponse;
import com.tlnk.gologin_test.ui.profile.ProfileActivity;
import com.tlnk.gologin_test.ui.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inputEmail, inputPassword;
    private AppCompatButton btnSignIn;
    private TextView btnCreateAccount, errorText;
    private CheckBox btnPasswordView;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String code = getIntent().getStringExtra("isClose");
        if (code != null) {
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
            sharedPreferences.edit().putString("authToken", "").apply();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");

        init();

        if (!authToken.equals("") && isOnline()) {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            } else if (!isOnline()){
                Toast.makeText(this, "Network connection lost", Toast.LENGTH_SHORT).show();
            }

        // for sdk > 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_light));
        }

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_default, null));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_pressed, null));
                errorText.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_pressed, null));
                errorText.setVisibility(View.GONE);
            }
        });

        btnPasswordView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    inputPassword.setTransformationMethod(null);

                }
                else {
                    inputPassword.setTransformationMethod(new PasswordTransformationMethod());
                }

                inputPassword.setSelection(inputPassword.length());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = getString(R.string.email_pattern);

                if(inputEmail.getText().toString().trim().matches(emailPattern)) {
                    if (inputEmail.getText().toString().equals("") || inputPassword.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, R.string.toast_error_fill_fields, Toast.LENGTH_SHORT).show();
                    } else {
                        LoginModel loginModel = new LoginModel(inputEmail.getText().toString(), inputPassword.getText().toString(), true, "");

                        loginViewModel.getProfileData().observe(LoginActivity.this, new Observer<SuccessfulResponse>() {
                            @Override
                            public void onChanged(SuccessfulResponse successfulResponse) {
                                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                                sharedPreferences.edit().putString("authToken", successfulResponse.getToken()).apply();

                                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });

                        loginViewModel.getError().observe(LoginActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                Toast.makeText(LoginActivity.this, R.string.toast_error_bad_login, Toast.LENGTH_SHORT).show();
                            }
                        });
                        loginViewModel.pushData(loginModel);
                    }
                } else {
                    inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_error, null));
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        inputEmail = findViewById(R.id.login_input_email);
        inputPassword = findViewById(R.id.login_input_password);
        btnSignIn = findViewById(R.id.login_btn_signIn);
        btnCreateAccount = findViewById(R.id.login_createAcc);
        errorText = findViewById(R.id.login_error_text);
        btnPasswordView = findViewById(R.id.login_btn_visible);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}