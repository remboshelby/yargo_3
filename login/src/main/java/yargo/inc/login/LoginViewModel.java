package yargo.inc.login;

import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.LoginResponse;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.utils.NoConnectivityException;
import yargo.inc.login.data.LoginData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public void observeIsNetworkError(LifecycleOwner lifecycleOwner, Observer<Boolean> observer) {
        isNoInternetConnection.observe(lifecycleOwner, observer);
    }

    public void makeLoginWithPassword() {
        String t1 = loginInfo.getValue().getEmail();
        String t2 = loginInfo.getValue().getPassword();
        String t3 = loginInfo.getValue().getAppId();


        addDisposible(loginRepository.makeLoginWithPass(loginInfo.getValue().getEmail(),
                loginInfo.getValue().getPassword(),
                loginInfo.getValue().getAppId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                            data.postValue(loginResponse);
                            isNoInternetConnection.postValue(false);
                        },
                        throwable -> {
                            if (throwable instanceof NoConnectivityException) {
                                isNoInternetConnection.postValue(true);
                            }
                        }));
    }

    public void makeLoginWithToken() {
        addDisposible(loginRepository.makeLoginWithToken(loginInfo.getValue().getAuthKey(), loginInfo.getValue().getAppId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                            data.postValue(loginResponse);
                            isNoInternetConnection.postValue(false);
                        },
                        throwable -> {
                            if (throwable instanceof NoConnectivityException) {
                                isNoInternetConnection.postValue(true);
                            }
                        }));
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
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);
        String appId = (String) commonSharedPreferences.getObject(CommonSharedPreferences.APP_ID, String.class);
        loginInfo.setValue(new LoginData(authKey, appId, "", ""));
    }
}