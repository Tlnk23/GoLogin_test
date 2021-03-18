package com.tlnk.gologin_test.api;

import android.content.SharedPreferences;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tlnk.gologin_test.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexandr Egorshin on 17.03.2021.
 */
public class ApiFactory {

    private static ApiFactory apiFactory;
    private static Retrofit retrofit;
    private static HttpLoggingInterceptor httpLoggingInterceptor;
    private static OkHttpClient okHttpClient;
    private static final String BASE_URL = "http://api.gologin.app/";

    private ApiFactory(String token) {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        if (!token.isEmpty())
                            requestBuilder.addHeader("Authorization", "Bearer " + token);
                        return chain.proceed(requestBuilder.build());
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiFactory getInstance(String token) {
        if (apiFactory == null) {
            apiFactory = new ApiFactory(token);
        }
        return apiFactory;
    }

    public static ApiFactory refreshToken(String token) {
        apiFactory = new ApiFactory(token);
        return apiFactory;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

}
