package yargo.inc.login.fragments.registration;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.user_info.RegistData.PersonData;
import yargo.inc.common.network.repository.RegistrRepository;

public class RegistrationViewModel extends BaseViewModel {
    @Inject
    protected RegistrRepository registrRepository;
    @Inject
    protected CommonSharedPreferences commonSharedPreferences;

    private static final int SURNAME_LENTH = 2;
    private static final int NAME_LENTH = 2;

    private static final int PASSWORD_LENTH = 5;
    private static final int PHONE_LENTH = 7;

    public static final int REGISTR_SUCCESS = 0;
    public static final int ERROR_PHONE = 1;
    public static final int ERROR_EMAIL = 2;
    public static final int ERROR_EMAIL_AND_PHONE = 3;
    public static final int UNKNOWN_ERROR = 4;

    public static final int PASSWORD_CORRECT = 0;
    public static final int PASSWORD_IS_SHORT = 1;
    public static final int PASSWORDS_NOT_MATCH = 2;

    private MutableLiveData<PersonData> personData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isBtnNextOn = new MutableLiveData<>();

    private MutableLiveData<String> passwordConfirmation = new MutableLiveData<>();

    private MutableLiveData<Integer> registrationStatus = new MutableLiveData<>();


    public RegistrationViewModel() {
        personData.setValue(new PersonData("", "", "", "", "", false, false, "", "1", ""));
        isBtnNextOn.setValue(false);
    }

    public void observeBtnStatus(LifecycleOwner owner, Observer<Boolean> observer) {
        isBtnNextOn.observe(owner, observer);
    }

    public void observeRegistrationStatus(LifecycleOwner owner, Observer<Integer> observer) {
        registrationStatus.observe(owner, observer);
    }

    public void makeRegistr() {
        addDisposible(registrRepository.makeRegistrGet(personData.getValue().getName(), personData.getValue().getSurname(), personData.getValue().getSex(), personData.getValue().getEmail(),
                personData.getValue().getTelephonNumber(), personData.getValue().getBirthday(), personData.getValue().getPassword(), personData.getValue().getCityId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registResponse -> {
                    if (registResponse.getRegistrResponse().getType().equals("OK")) {
                        pushAuthToken(registResponse.getRegistrResponse().getAuthKey());
                        pushUser(registResponse.getRegistrResponse().getUser());
                        registrationStatus.postValue(REGISTR_SUCCESS);
                    } else if (registResponse.getRegistrResponse().getType().equals("error")) {
                        if (registResponse.getRegistrResponse().getMessage().getPhone() != null && registResponse.getRegistrResponse().getMessage().getEmail() == null) {
                            registrationStatus.postValue(ERROR_PHONE);
                        } else if (registResponse.getRegistrResponse().getMessage().getEmail() != null && registResponse.getRegistrResponse().getMessage().getPhone() == null) {
                            registrationStatus.postValue(ERROR_EMAIL);
                        } else if (!registResponse.getRegistrResponse().getMessage().getEmail().isEmpty() && !registResponse.getRegistrResponse().getMessage().getEmail().isEmpty()) {
                            registrationStatus.postValue(ERROR_EMAIL_AND_PHONE);
                        }
                    }
                }, throwable -> {
                    registrationStatus.setValue(UNKNOWN_ERROR);
                    throwable.printStackTrace();
                }));
    }

    public void setSurname(String textSurname) {
        personData.getValue().setSurname(textSurname);
        if (!textSurname.isEmpty() && textSurname.length() > SURNAME_LENTH) {
            isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setName(String textName) {
        personData.getValue().setName(textName);
        if (!textName.isEmpty() && textName.length() > NAME_LENTH) {
            isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setEmail(String textEmail) {
        if (isEmailValid(textEmail)) {
            personData.getValue().setEmail(textEmail);
            isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setCityId(String cityId) {
        personData.getValue().setCityId(cityId);
        if (!cityId.isEmpty()) {
            isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setBirthday(String birthday) {
        personData.getValue().setBirthday(birthday);
        if (!birthday.isEmpty()) {
            isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
        } else {
            isBtnNextOn.setValue(false);
        }
    }


    public void setSex(String sex) {
        personData.getValue().setSex(sex);
        if (!sex.isEmpty()) {
            isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setTelephoneNumber(String telephoneNumber) {
        personData.getValue().setTelephonNumber(telephoneNumber);
        if (!telephoneNumber.isEmpty() && telephoneNumber.length() > PHONE_LENTH) {
            isBtnNextOn.setValue(personData.getValue().isPhoneConfirm());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setRuleConfirm(boolean isConfirmd) {
        personData.getValue().setTelephoneConfirmed(isConfirmd);
        if (isConfirmd) {
            isBtnNextOn.setValue(personData.getValue().isPhoneConfirm());
        } else {
            isBtnNextOn.setValue(false);
        }
    }

    public void setPassword(String password) {
        personData.getValue().setPassword(password);
    }

    public int isPasswodCorrect(String passwordConf) {
        if (passwordConf.equals(personData.getValue().getPassword()) && !passwordConf.isEmpty() && passwordConf.length() > PASSWORD_LENTH) {
            return PASSWORD_CORRECT;
        } else if (passwordConf.length() < PASSWORD_LENTH) {
            return PASSWORD_IS_SHORT;
        } else if (passwordConf != personData.getValue().getPassword()) {
            return PASSWORDS_NOT_MATCH;
        } else {
            return PASSWORDS_NOT_MATCH;
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void getBtnStatus(int position) {
        switch (position) {
            case 0:
                isBtnNextOn.setValue(personData.getValue().isPersonEmpty());
                break;
            case 1:
                isBtnNextOn.setValue(false);
                break;
            case 2:
                isBtnNextOn.setValue(false);
                break;
        }
    }

    public String getTitle(int position) {
        switch (position) {
            case 0:
                return "О Вас";
            case 1:
                return "Мобильный телефон";
            case 2:
                return "Завершение";
            default:
                return "Регистрация";
        }
    }

    public void setRegistrationStatus() {
        this.registrationStatus.postValue(REGISTR_SUCCESS);
    }

    public void replaceVacantSubscription(LifecycleOwner owner) {
        onCleared();
        registrationStatus.removeObservers(owner);
    }

    public void pushAuthToken(String authKey) {
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authKey);
    }

    public void pushUser(User user) {
        commonSharedPreferences.putObject(CommonSharedPreferences.USER_ABOUT_RESPONSE, user);
    }
}
