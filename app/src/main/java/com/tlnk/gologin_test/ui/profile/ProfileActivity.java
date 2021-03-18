package com.tlnk.gologin_test.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tlnk.gologin_test.R;
import com.tlnk.gologin_test.ui.login.LoginActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileId, profileEmail, profileCreatedAt;
    private ProfileViewModel profileViewModel;
    int backPressedQ = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // for sdk > 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_light));
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");

        init();

        profileViewModel.getProfile().observe(this, new Observer<ArrayList<ProfileModel>>() {
            @Override
            public void onChanged(ArrayList<ProfileModel> profileModels) {
                profileId.setText(profileModels.get(0).getProfileId());
                profileEmail.setText(profileModels.get(0).getProfileEmail());
                profileCreatedAt.setText(profileModels.get(0).getProfileCreatedAt());
            }
        });

        profileViewModel.loadData(authToken);
    }

    private void init() {
        profileId = findViewById(R.id.profile_id_text);
        profileEmail = findViewById(R.id.profile_email_text);
        profileCreatedAt = findViewById(R.id.profile_createdAt_text);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public void onBackPressed()
    {
        if (this.backPressedQ == 1)
        {
//            System.exit(0);
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.putExtra("isClose", "true");
            startActivity(intent);
        }
        else
        {
            this.backPressedQ++;
            Toast.makeText(this, R.string.toast_click, Toast.LENGTH_SHORT).show();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                backPressedQ = 0;
            }
        }, 5000);
    }
}
