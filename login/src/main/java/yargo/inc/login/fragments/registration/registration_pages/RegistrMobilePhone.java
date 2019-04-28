package yargo.inc.login.fragments.registration.registration_pages;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationFragment;
import yargo.inc.login.fragments.registration.RegistrationViewModel;

public class RegistrMobilePhone extends BaseFragment {


    @BindView(R2.id.btnGetCode)
    Button btnGetCode;
    @BindView(R2.id.phoneCode)
    EditText phoneCode;
    @BindView(R2.id.phoneNumber)
    AutoCompleteTextView phoneNumber;
    @BindView(R2.id.tvInfo)
    TextView tvInfo;
    @BindView(R2.id.cBSogl)
    CheckBox cBSogl;
    @BindView(R2.id.tvOfferstPolitic)
    TextView tvOfferstPolitic;
    @Inject
    protected RegistrationViewModel registrationViewModel;
    private ProgressDialog progressDialog;


    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        LoginFragment.getLoginComponent().inject(this);
        return inflater.inflate(R.layout.registr_mobile_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        phoneCode.setText("+7");

        super.onViewCreated(view, savedInstanceState);
    }

    @OnTextChanged(R2.id.phoneNumber)
    void onPhoneTextChanged() {
        registrationViewModel.setTelephoneNumber(phoneCode.getText().toString() + phoneNumber.getText().toString());
    }

    @OnCheckedChanged(R2.id.cBSogl)
    void onCheck(boolean checked) {
        registrationViewModel.setRuleConfirm(checked);
    }

    @OnClick(R2.id.btnGetCode)
    void onBtnGetCodeClick(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        Runnable progressRunnable = () -> {
            progressDialog.cancel();
            ((RegistrationFragment)getParentFragment()).onBtnRegistNextClick();
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 5000);
    }


}
