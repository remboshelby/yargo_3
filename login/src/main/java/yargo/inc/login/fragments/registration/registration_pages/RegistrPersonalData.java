package yargo.inc.login.fragments.registration.registration_pages;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationViewModel;

public class RegistrPersonalData extends BaseFragment {


    @BindView(R2.id.spinCity)
    Spinner spinCity;
    @BindView(R2.id.editSurname)
    AutoCompleteTextView editSurname;
    @BindView(R2.id.editName)
    AutoCompleteTextView editName;
    @BindView(R2.id.editEmail)
    AutoCompleteTextView editEmail;
    @BindView(R2.id.cityId)
    TextView cityId;
    @BindView(R2.id.sexMale)
    RadioButton sexMeil;
    @BindView(R2.id.sexFemale)
    RadioButton sexFemail;
    @BindView(R2.id.sexRadioGroup)
    RadioGroup sexRadioGroup;
    @BindView(R2.id.editBdate)
    AutoCompleteTextView editBdate;
    @Inject
    protected RegistrationViewModel registrationViewModel;
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        LoginFragment.getLoginComponent().inject(this);
        return inflater.inflate(R.layout.registr_personal_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        init();
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        registrationViewModel.makeRegistr();
        spinCity.setOnTouchListener((v, event) -> {
            if (event.getAction()==MotionEvent.ACTION_UP)
            {
                hideKeyboard();
            }
            return false;
        });
    }

    @OnTextChanged(R2.id.editSurname)
    void editSurname(){
        registrationViewModel.setSurname(editSurname.getText().toString());
    }
    @OnTextChanged(R2.id.editName)
    void editName(){
        registrationViewModel.setName(editName.getText().toString());
    }
    @OnTextChanged(R2.id.editEmail)
    void editEmail(){
        registrationViewModel.setEmail(editEmail.getText().toString());
    }
    @OnItemSelected(R2.id.spinCity)
    void spinnerItemSelected(int position) {
        registrationViewModel.setCityId(RegistrPersonalData.this.getResources().getStringArray(R.array.citiesId)[position]);
    }
    @OnClick(R2.id.editBdate)
    void chooseBirthday(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, myear, mmonth, mdayOfMonth) -> {
            if (mmonth>8){
                editBdate.setText(mdayOfMonth + "." + (mmonth +1) + "." + myear);
                registrationViewModel.setBirthday(mdayOfMonth + "." + (mmonth +1) + "." + myear);
            }
            else
            {
                editBdate.setText(mdayOfMonth + ".0" + (mmonth +1) + "." + myear);
                registrationViewModel.setBirthday(mdayOfMonth + ".0" + (mmonth +1) + "." + myear);
            }
        }, year,month,day);
        datePickerDialog.show();
    }

    @OnClick({R2.id.sexMale, R2.id.sexFemale})
    void onRadioButtonClicked(RadioButton radioButton){
        boolean checked = radioButton.isChecked();
        if (radioButton.getId() == R.id.sexMale && checked) {
            registrationViewModel.setSex("male");
        }else if (radioButton.getId() == R.id.sexFemale && checked) {
            registrationViewModel.setSex("female");
        }
    }
}
