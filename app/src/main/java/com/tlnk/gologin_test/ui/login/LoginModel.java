package com.tlnk.gologin_test.ui.login;

/**
 * Created by Alexandr Egorshin on 18.03.2021.
 */
public class LoginModel {
    String username;
    String password;
    Boolean fromApp;
    String googleClientId;

    public LoginModel(String userName, String userPassword, Boolean fromApp, String googleClientId) {
        this.username = userName;
        this.password = userPassword;
        this.fromApp = fromApp;
        this.googleClientId = googleClientId;
    }
}
