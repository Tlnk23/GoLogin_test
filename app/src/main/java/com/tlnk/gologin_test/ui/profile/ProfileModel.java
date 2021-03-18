package com.tlnk.gologin_test.ui.profile;

/**
 * Created by Alexandr Egorshin on 18.03.2021.
 */
public class ProfileModel {
    String profileId;
    String profileEmail;
    String profileCreatedAt;

    public ProfileModel(String profileId, String profileEmail, String profileCreatedAt) {
        this.profileId = profileId;
        this.profileEmail = profileEmail;
        this.profileCreatedAt = profileCreatedAt;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public String getProfileCreatedAt() {
        return profileCreatedAt;
    }
}
