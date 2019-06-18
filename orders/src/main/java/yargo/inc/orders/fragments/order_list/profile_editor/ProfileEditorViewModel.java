package yargo.inc.orders.fragments.order_list.profile_editor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewAndroidModel;
import yargo.inc.common.interactors.ProfileEditorInteractor;
import yargo.inc.common.network.models.login.User;

import static yargo.inc.common.dto.CommonSharedPreferences.APP_ID;

public class ProfileEditorViewModel extends BaseViewAndroidModel {
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> profileChangedMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> someFieldIsChangedMLD = new MutableLiveData<>();


    private ProfileEditorInteractor interactor;

    private User user;

    public ProfileEditorViewModel(@NonNull Application application, ProfileEditorInteractor interactor) {
        super(application);
        this.interactor = interactor;

        getUserInfo();
    }

    public void observUserLiveData(LifecycleOwner owner, Observer observer) {
        userMutableLiveData.observe(owner, observer);
    }

    public void observProfileChanged(LifecycleOwner owner, Observer observer) {
        profileChangedMLD.observe(owner, observer);
    }

    public void observFieldChanging(LifecycleOwner owner, Observer observer) {
        someFieldIsChangedMLD.observe(owner, observer);
    }

    public void getUserInfo() {
        user = interactor.getUserInfo();
        userMutableLiveData.postValue(user);
        profileChangedMLD.postValue(false);
    }

    public void pushUserInfo() {
        String authKey = interactor.getAuthToken();
        String appId = interactor.getAppId();


        addDisposible(interactor.editProfile(authKey, appId, userMutableLiveData.getValue().getUsername(),
                userMutableLiveData.getValue().getSurname(),
                userMutableLiveData.getValue().getSex(),
                userMutableLiveData.getValue().getIdCity(),
                userMutableLiveData.getValue().getBirthday())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileEditResponse -> {
                    User user = profileEditResponse.getResponse().getUser();
                    userMutableLiveData.postValue(user);
                    interactor.setUserInfoAndFilteredCity(user);

                    profileChangedMLD.postValue(true);
                }));

    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setSex(int sex) {
        if (user.getSex() == sex) {
            someFieldIsChangedMLD.postValue(false);
        } else {
            someFieldIsChangedMLD.postValue(true);
            userMutableLiveData.getValue().setSex(sex);
        }
    }

    public void setBirthday(String birthday) {
        if (user.getBirthday().equals(birthday)) {
            someFieldIsChangedMLD.postValue(false);
        } else {
            someFieldIsChangedMLD.postValue(true);
            userMutableLiveData.getValue().setBirthday(birthday);
        }
    }

    public void setCityId(int cityId) {
        if (user.getIdCity() == cityId) {
            someFieldIsChangedMLD.postValue(false);
        } else {
            someFieldIsChangedMLD.postValue(true);
            userMutableLiveData.getValue().setIdCity(cityId);
        }
    }

    public void setName(String name) {
        if (user.getUsername().equals(name)) {
            someFieldIsChangedMLD.postValue(false);
        } else {
            someFieldIsChangedMLD.postValue(true);
            userMutableLiveData.getValue().setUsername(name);
        }
    }

    public void setSurname(String surname) {
        if (user.getSurname().equals(surname)) {
            someFieldIsChangedMLD.postValue(false);
        } else {
            someFieldIsChangedMLD.postValue(true);
            userMutableLiveData.getValue().setSurname(surname);
        }
    }

    public void setSomeFieldIsChangedMLD(boolean someFieldIsChangedMLD) {
        this.someFieldIsChangedMLD.setValue(someFieldIsChangedMLD);
    }
}
