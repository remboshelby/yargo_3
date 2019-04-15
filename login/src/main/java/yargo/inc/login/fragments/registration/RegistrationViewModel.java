package yargo.inc.login.fragments.registration;

import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import butterknife.OnItemSelected;
import dagger.Provides;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.user_info.PersonData;
import yargo.inc.common.network.repository.RegistrRepository;

import androidx.lifecycle.MutableLiveData;

public class RegistrationViewModel extends BaseViewModel {
    private RegistrRepository registrRepository;
    private CommonSharedPreferences commonSharedPreferences;


    private static final int SURNAME_LENTH = 2;
    private static final int NAME_LENTH = 2;

    private static final int PASSWORD_LENTH = 5;
    private static final int PHONE_LENTH = 7;

    private MutableLiveData<PersonData> personData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isBtnNextOn = new MutableLiveData<>();

    public RegistrationViewModel(RegistrRepository registrRepository, CommonSharedPreferences commonSharedPreferences) {
        this.registrRepository = registrRepository;
        this.commonSharedPreferences = commonSharedPreferences;
        personData.setValue(new PersonData("", "", "", "", "", false, false, "", "male", ""));
        isBtnNextOn.setValue(false);
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
                break;
        }
    }

    public void observeBtnStatus(LifecycleOwner owner, Observer<Boolean> observer) {
        isBtnNextOn.observe(owner, observer);
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
        personData.getValue().setEmail(textEmail);
        if (isEmailValid(textEmail)) {
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

    public void click(){

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

    public void setPhoneConfirm(boolean isConfirmd) {
//        personData.getValue().setTelephoneConfirmed(isConfirmd);
//        if (isConfirmd) {
//            isBtnNextOn.setValue(personData.getValue().isPhoneConfirm());
//        } else {
//            isBtnNextOn.setValue(false);
//        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
