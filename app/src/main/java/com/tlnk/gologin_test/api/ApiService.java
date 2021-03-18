package com.tlnk.gologin_test.api;

import com.tlnk.gologin_test.pojo.SuccessfulResponse;
import com.tlnk.gologin_test.pojo.UserInfoResponse;
import com.tlnk.gologin_test.ui.login.LoginModel;
import com.tlnk.gologin_test.ui.registration.RegistrationModel;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Alexandr Egorshin on 17.03.2021.
 */
public interface ApiService {

    @POST("user")
    Call<SuccessfulResponse> accountRegistration(@Body RegistrationModel registrationModel);

    @POST("user/login")
    Call<SuccessfulResponse> accountLogin(@Body LoginModel loginModel);

    @GET("user")
    Single<UserInfoResponse> getUserInfo();

}
