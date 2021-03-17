package com.tlnk.gologin_test.ui.profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tlnk.gologin_test.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileId, profileEmail, profileCreatedAt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        profileId = findViewById(R.id.profile_id_text);
        profileEmail = findViewById(R.id.profile_email_text);
        profileCreatedAt = findViewById(R.id.profile_createdAt_text);
    }
}
