package com.tlnk.gologin_test.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr Egorshin on 17.03.2021.
 */
public class SuccessfulResponse {

    @SerializedName("_id")
    String profile_id;
    @SerializedName("access_token")
    String access_token;
    @SerializedName("refresh_token")
    String refresh_token;
    @SerializedName("token")
    String token;

    public String getId() {
        return profile_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken() {
        return token;
    }
}
