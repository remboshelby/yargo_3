package yargo.inc.login.utils_view;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

import yargo.inc.login.LoginViewModel;
import yargo.inc.login.R;
import yargo.inc.login.R2;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import yargo.inc.login.fragments.LoginFragment;

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
    }

    private void init(Context context) {
        LoginFragment.getLoginComponent().inject(this);
        LayoutInflater.from(context).inflate(R.layout.enter_password_view, this);
        ButterKnife.bind(this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
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
    void onToogleStatusChanged(boolean checked) {
        if (checked) {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnFocusChange(R2.id.password)
    void onFocusChanged(boolean focused) {
        if (focused) {
            imgToggleButton.setVisibility(VISIBLE);
        } else {
            imgToggleButton.setVisibility(GONE);
            imgToggleButton.setChecked(false);
            onToogleStatusChanged(false);
        }
    }
}
