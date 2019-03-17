package com.inc.evil.common.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public BaseActivity getRoot(){
        return ((BaseActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inject();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void inject(){};

    protected abstract View infate(LayoutInflater inflater, ViewGroup container);
}
