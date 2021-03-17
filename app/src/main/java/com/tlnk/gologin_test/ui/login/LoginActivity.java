package com.tlnk.gologin_test.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tlnk.gologin_test.R;
import com.tlnk.gologin_test.ui.profile.ProfileActivity;
import com.tlnk.gologin_test.ui.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inputEmail, inputPassword;
    private AppCompatButton btnSignIn;
    private TextView btnCreateAccount, errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");

        if (!authToken.equals("")) {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            }

        // for sdk > 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_light));
        }

        init();

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
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(inputEmail.getText().toString().trim().matches(emailPattern)) {
                    if (inputEmail.getText().toString().equals("") || inputPassword.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect email", Toast.LENGTH_SHORT).show();
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
    }

}