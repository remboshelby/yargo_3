package yargo.inc.orders.fragments.order_list.profile_editor;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.repository.OrdersRepository;

import static yargo.inc.common.dto.CommonSharedPreferences.APP_ID;
import static yargo.inc.common.dto.CommonSharedPreferences.AUTH_KEY;
import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public class ProfileEditorViewModel extends BaseViewModel {


    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    private CommonSharedPreferences commonSharedPreferences;
    public LoginRepository loginRepository;

    public ProfileEditorViewModel(CommonSharedPreferences commonSharedPreferences, LoginRepository loginRepository) {
        this.commonSharedPreferences = commonSharedPreferences;
        this.loginRepository = loginRepository;
        getUserInfo();
    }

    public void observUserLiveData(LifecycleOwner owner, Observer observer) {
        userMutableLiveData.observe(owner, observer);
    }

    public void getUserInfo() {
        userMutableLiveData.postValue((User) commonSharedPreferences.getObject(USER_ABOUT_RESPONSE, User.class));
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
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        userMutableLiveData.postValue(user);
                    }
                }));

    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setSex(int sex) {
        userMutableLiveData.getValue().setSex(sex);
    }

    public void setBirthday(String birthday) {
        userMutableLiveData.getValue().setBirthday(birthday);
    }

    public void setCityId(int cityId) {
        userMutableLiveData.getValue().setIdCity(cityId);
    }

    public void setName(String name) {
        userMutableLiveData.getValue().setUsername(name);
    }

    public void setSurname(String surname) {
        userMutableLiveData.getValue().setSurname(surname);
    }
}
