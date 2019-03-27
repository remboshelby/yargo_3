package com.inc.evil.login;

import android.app.Application;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.data.Login.LoginResponse;
import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.login.data.LoginData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.inc.evil.common.dto.CommonSharedPreferences.APP_ID;
import static com.inc.evil.common.dto.CommonSharedPreferences.AUTH_KEY;

public class LoginViewModel extends BaseViewModel {
    private LoginRepository loginRepository;
    private CommonSharedPreferences commonSharedPreferences;

    MutableLiveData<LoginResponse> data = new MutableLiveData<>();
    MutableLiveData<LoginData> loginInfo = new MutableLiveData<>();
    MutableLiveData<Boolean> invisiblity = new MutableLiveData<>();


    public LoginViewModel(LoginRepository loginRepository, CommonSharedPreferences commonSharedPreferences) {
        this.loginRepository = loginRepository;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public void observeData(LifecycleOwner owner, Observer<LoginResponse> observer) {
        data.observe(owner, observer);
    }

    public void observeFieldsContent(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        invisiblity.observe(lifecycleOwner, observer);
    }

    public void makeLoginWithPassword() {
        addDisposible(loginRepository.makeLoginWithPass(loginInfo.getValue().getEmail(),
                loginInfo.getValue().getPassword(),
                loginInfo.getValue().getAppId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        data.postValue(loginResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void makeLoginWithToken() {
        addDisposible(loginRepository.makeLoginWithToken(loginInfo.getValue().getAuthKey(),loginInfo.getValue().getAppId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        data.postValue(loginResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }


    public void setEmail(String emailValue) {
        loginInfo.getValue().setEmail(emailValue);
        if (!emailValue.isEmpty() && emailValue.length()>5) {
            invisiblity.setValue(loginInfo.getValue().isFieldNotEmpty());
        }
    }

    public void setPassword(String password) {
        loginInfo.getValue().setPassword(password);
        if (!password.isEmpty() && password.length()>5) {
            invisiblity.setValue(loginInfo.getValue().isFieldNotEmpty());
        }
    }

    public void setAuthKey(String authKey) {
        loginInfo.getValue().setAuthKey(authKey);
    }

    public boolean isAuthKeyExist() {
        return !loginInfo.getValue().getAuthKey().isEmpty();
    }

    public void initLoginInfo() {
        String authKey = (String) commonSharedPreferences.getObject(AUTH_KEY, String.class);
        String appId = (String) commonSharedPreferences.getObject(APP_ID, String.class);
        loginInfo.setValue(new LoginData(authKey, appId, "", ""));
    }
}
