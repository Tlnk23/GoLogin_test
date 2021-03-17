package com.tlnk.gologin_test.ui.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tlnk.gologin_test.R;
import com.tlnk.gologin_test.api.ApiFactory;
import com.tlnk.gologin_test.api.ApiService;
import com.tlnk.gologin_test.pojo.RegistrationResponse;
import com.tlnk.gologin_test.ui.login.LoginActivity;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText inputEmail, inputPassword, inputConfirmPassword;
    private TextView errorText;
    private AppCompatButton btnSignIn;

    private CompositeDisposable compositeDisposable;

    private RegistrationResponse registrationResponse;

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
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(inputEmail.getText().toString().trim().matches(emailPattern)) {
                    if (inputPassword.getText().toString().equals("") || inputConfirmPassword.getText().toString().equals("")) {
                        Toast.makeText(RegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();
                    } else {
                        RegistrationModel registrationModel = new RegistrationModel(inputEmail.getText().toString(), inputPassword.getText().toString(), inputConfirmPassword.getText().toString(), "");
                        makeRegistration(registrationModel);
                    }
                } else Toast.makeText(RegistrationActivity.this, "Incorrect email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        inputEmail = findViewById(R.id.registration_input_email);
        inputPassword = findViewById(R.id.registration_input_password);
        inputConfirmPassword = findViewById(R.id.registration_input_password_confirm);
        btnSignIn = findViewById(R.id.registration_btn_signUp);
        errorText = findViewById(R.id.registration_error_text);
    }

    @SuppressLint("CheckResult")
    private void makeRegistration(RegistrationModel registrationModel) {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();
        apiService.accountRegistration(registrationModel).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "AAAAA", Toast.LENGTH_SHORT).show();
                    registrationResponse = response.body();
                    Toast.makeText(RegistrationActivity.this, registrationResponse.getToken(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}