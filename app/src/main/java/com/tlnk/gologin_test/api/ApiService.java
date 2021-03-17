package com.tlnk.gologin_test.api;

import com.tlnk.gologin_test.pojo.AccountRegister;
import com.tlnk.gologin_test.pojo.RegistrationResponse;
import com.tlnk.gologin_test.ui.registration.RegistrationModel;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Alexandr Egorshin on 17.03.2021.
 */
public interface ApiService {

    @POST("user")
    Call<RegistrationResponse> accountRegistration(@Body RegistrationModel registrationModel);

}
