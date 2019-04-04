package com.inc.evil.login;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.models.login.LoginResponse;
import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.login.data.LoginData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.inc.evil.common.dto.CommonSharedPreferences.APP_ID;
import static com.inc.evil.common.dto.CommonSharedPreferences.AUTH_KEY;

public class LoginViewModel extends BaseViewModel {
    private LoginRepository loginRepository;
    private CommonSharedPreferences commonSharedPreferences;

    private static final int PASSWORD_LENTH = 4;
    private static final int EMAIL_LENTH = 4;

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
                .subscribe(loginResponse -> data.postValue(loginResponse),
                        Throwable::printStackTrace));
    }

    public void makeLoginWithToken() {
        addDisposible(loginRepository.makeLoginWithToken(loginInfo.getValue().getAuthKey(), loginInfo.getValue().getAppId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> data.postValue(loginResponse),
                        throwable -> throwable.printStackTrace()));
    }


    public void setEmail(String emailValue) {
        loginInfo.getValue().setEmail(emailValue);
        if (!emailValue.isEmpty() && emailValue.length() > EMAIL_LENTH) {
            invisiblity.setValue(loginInfo.getValue().isFieldNotEmpty());
        } else if (emailValue.length() <= EMAIL_LENTH) {
            invisiblity.setValue(false);
        }
    }

    public void setPassword(String passwordValue) {
        loginInfo.getValue().setPassword(passwordValue);
        if (!passwordValue.isEmpty() && passwordValue.length() > PASSWORD_LENTH) {
            invisiblity.setValue(loginInfo.getValue().isFieldNotEmpty());
        } else if (passwordValue.length() <= PASSWORD_LENTH) {
            invisiblity.setValue(false);
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
