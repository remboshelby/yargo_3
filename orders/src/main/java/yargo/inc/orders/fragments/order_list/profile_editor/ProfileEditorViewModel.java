package yargo.inc.orders.fragments.order_list.profile_editor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewAndroidModel;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.profile_editor_model.ProfileEditResponse;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;

import static yargo.inc.common.dto.CommonSharedPreferences.APP_ID;
import static yargo.inc.common.dto.CommonSharedPreferences.AUTH_KEY;
import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public class ProfileEditorViewModel extends BaseViewAndroidModel {
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> profileChangedMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> someFieldIsChangedMLD = new MutableLiveData<>();

    @Inject
    protected CommonSharedPreferences commonSharedPreferences;
    @Inject
    protected LoginRepository loginRepository;

    private User user;

    public ProfileEditorViewModel(@NonNull Application application) {
        super(application);

        OrderListsFragment.getOrdersComponent().inject(this);
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
        user = (User) commonSharedPreferences.getObject(USER_ABOUT_RESPONSE, User.class);
        userMutableLiveData.postValue(user);
        profileChangedMLD.postValue(false);
    }

    public void pushUserInfo() {
        String authKey = (String) commonSharedPreferences.getObject(AUTH_KEY, String.class);
        String appId = (String) commonSharedPreferences.getObject(APP_ID, String.class);


        addDisposible(loginRepository.editProfile(authKey, appId, userMutableLiveData.getValue().getUsername(),
                userMutableLiveData.getValue().getSurname(),
                userMutableLiveData.getValue().getSex(),
                userMutableLiveData.getValue().getIdCity(),
                userMutableLiveData.getValue().getBirthday())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileEditResponse -> {
                    userMutableLiveData.postValue(profileEditResponse.getResponse().getUser());
                    commonSharedPreferences.putObject(commonSharedPreferences.USER_ABOUT_RESPONSE, profileEditResponse.getResponse().getUser());
                    commonSharedPreferences.putObject(commonSharedPreferences.FILTERED_CITY, profileEditResponse.getResponse().getUser().getIdCity());

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
