package com.tlnk.gologin_test.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by Alexandr Egorshin on 18.03.2021.
 */
public class UserInfoResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("plan")
    @Expose
    private JSONObject plan;
    @SerializedName("needCard")
    @Expose
    private boolean needCard;
    @SerializedName("hasTrial")
    @Expose
    private boolean hasTrial;
    @SerializedName("trialDays")
    @Expose
    private int trialDays;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public JSONObject getPlan() {
        return plan;
    }

    public boolean isNeedCard() {
        return needCard;
    }

    public boolean isHasTrial() {
        return hasTrial;
    }

    public int getTrialDays() {
        return trialDays;
    }
}
