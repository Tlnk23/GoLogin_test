package com.tlnk.gologin_test.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tlnk.gologin_test.api.ApiFactory;
import com.tlnk.gologin_test.api.ApiService;
import com.tlnk.gologin_test.pojo.UserInfoResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alexandr Egorshin on 18.03.2021.
 */
public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProfileModel>> profileModelList = new MutableLiveData<>();
    private ArrayList<ProfileModel> profileModel = new ArrayList<>();
    private CompositeDisposable compositeDisposable;

    public MutableLiveData<ArrayList<ProfileModel>> getProfile() {
        return profileModelList;
    }

    public void loadData(String token) {
        ApiFactory apiFactory = ApiFactory.refreshToken(token);
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfoResponse>() {
                    @Override
                    public void accept(UserInfoResponse userInfoResponse) throws Exception {
                        ProfileModel currentProfile = new ProfileModel(userInfoResponse.getId(), userInfoResponse.getEmail(), userInfoResponse.getCreatedAt());
                        profileModel.add(currentProfile);

                        profileModelList.setValue(profileModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);


    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
