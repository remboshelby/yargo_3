package com.inc.evil.login.utils_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inc.evil.login.R;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class customEnterPasswordView extends ConstraintLayout {
    public customEnterPasswordView(Context context) {
        super(context);
        init(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.enter_password_view, this);
    }

    public customEnterPasswordView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
}
