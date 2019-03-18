package com.inc.evil.login;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.network.data.Login.LoginResponse;
import com.inc.evil.common.network.repository.LoginRepository;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {
    private LoginRepository loginRepository;

    private MutableLiveData<LoginResponse> data = new MutableLiveData<>();

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void observeData(LifecycleOwner owner, Observer<LoginResponse> observer){
        data.observe(owner,observer);
    }
    public void makeLoginWithPassword(String login, String password, String app_id){
        addDisposible(loginRepository.makeLoginWithPass(login, password, app_id)
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

    public void makeLoginWithToken(String auth_token, String app_id){
        addDisposible(loginRepository.makeLoginWithToken(auth_token, app_id)
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
}
