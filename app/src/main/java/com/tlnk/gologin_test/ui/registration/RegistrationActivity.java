package com.tlnk.gologin_test.ui.registration;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tlnk.gologin_test.R;
import com.tlnk.gologin_test.pojo.SuccessfulResponse;
import com.tlnk.gologin_test.ui.login.LoginActivity;
import com.tlnk.gologin_test.ui.profile.ProfileActivity;


public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText inputEmail, inputPassword, inputConfirmPassword;
    private TextView errorText, btnGoLogin;
    private AppCompatButton btnSignIn;

    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // for sdk > 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_light));
        }

        init();

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_pressed, null));
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_default, null));
            }
        });

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

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = getString(R.string.email_pattern);
                if(inputEmail.getText().toString().trim().matches(emailPattern)) {

                    if (inputPassword.getText().toString().equals("") || inputConfirmPassword.getText().toString().equals("")) {  //check for empty inputs
                        Toast.makeText(RegistrationActivity.this, R.string.toast_error_fill_fields, Toast.LENGTH_SHORT).show();
                    } else if(inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())){
                        RegistrationModel registrationModel = new RegistrationModel(inputEmail.getText().toString(), inputPassword.getText().toString(), inputConfirmPassword.getText().toString(), "aaaa");

                        registrationViewModel.getProfileData().observe(RegistrationActivity.this, new Observer<SuccessfulResponse>() {
                            @Override
                            public void onChanged(SuccessfulResponse successfulResponse) {
                                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                                sharedPreferences.edit().putString("authToken", successfulResponse.getToken()).apply();

                                Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                        
                        registrationViewModel.getError().observe(RegistrationActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                if (isOnline()){
                                    Toast.makeText(RegistrationActivity.this, R.string.toast_error_fields, Toast.LENGTH_SHORT).show();
                                } else {Toast.makeText(RegistrationActivity.this, R.string.toast_error_network, Toast.LENGTH_SHORT).show();}
                            }
                        });
                        registrationViewModel.makeRegistration(registrationModel);
                    } else {
                        Toast.makeText(RegistrationActivity.this, R.string.toast_error_password, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    inputEmail.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_form_error, null));
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        inputEmail = findViewById(R.id.registration_input_email);
        inputPassword = findViewById(R.id.registration_input_password);
        inputConfirmPassword = findViewById(R.id.registration_input_password_confirm);
        btnSignIn = findViewById(R.id.registration_btn_signUp);
        errorText = findViewById(R.id.registration_error_text);
        btnGoLogin = findViewById(R.id.registration_btn_logIn);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}