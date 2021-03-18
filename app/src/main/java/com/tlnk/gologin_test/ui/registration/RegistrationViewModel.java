package com.tlnk.gologin_test.ui.registration;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlnk.gologin_test.api.ApiFactory;
import com.tlnk.gologin_test.api.ApiService;
import com.tlnk.gologin_test.pojo.SuccessfulResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr Egorshin on 18.03.2021.
 */
public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<SuccessfulResponse> profileData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<SuccessfulResponse> getProfileData() {
        return profileData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void makeRegistration(RegistrationModel registrationModel) {
        ApiFactory apiFactory = ApiFactory.getInstance("");
        ApiService apiService = apiFactory.getApiService();

        apiService.accountRegistration(registrationModel).enqueue(new Callback<SuccessfulResponse>() {
            @Override
            public void onResponse(Call<SuccessfulResponse> call, Response<SuccessfulResponse> response) {
                if (response.isSuccessful()) {
                    profileData.setValue(response.body());
                } else {
                    error.setValue("error");
                }
            }

            @Override
            public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
            }
        });
    }
}
