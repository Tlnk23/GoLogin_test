package com.tlnk.gologin_test.ui.registration;

/**
 * Created by Alexandr Egorshin on 17.03.2021.
 */
public class RegistrationModel {
    String email;
    String password;
    String passwordConfirm;
    String googleClientId;

    public RegistrationModel(String email, String password, String passwordConfirm, String googleClientId) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.googleClientId = googleClientId;
    }
}