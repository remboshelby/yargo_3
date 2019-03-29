package com.inc.evil.login.utils_view;

import android.content.Context;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.inc.evil.login.LoginViewModel;
import com.inc.evil.login.R;
import com.inc.evil.login.R2;
import com.inc.evil.login.fragments.LoginFragment;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class СustomEnterPasswordView extends ConstraintLayout {


    @BindView(R2.id.password)
    EditText password;
    @BindView(R2.id.imgToggleButton)
    ToggleButton imgToggleButton;

    @Inject
    protected LoginViewModel viewModel;


    public СustomEnterPasswordView(Context context) {
        super(context);
        init(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    private void init(Context context) {
        LoginFragment.getLoginComponent().inject(this);
        LayoutInflater.from(context).inflate(R.layout.enter_password_view, this);
        ButterKnife.bind(this);
    }

    public СustomEnterPasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    @OnTextChanged(R2.id.password)
    void onPasswordChanged() {
        viewModel.setPassword(password.getText().toString());
    }

    @OnCheckedChanged(R2.id.imgToggleButton)
    void onToogleStatusChanged(ToggleButton toggleButton, boolean checked){
        if (checked){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
