package com.inc.evil.common.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.evil.common.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
        return inflate(inflater, container);
    }

    protected void inject(){};

    protected abstract View inflate(LayoutInflater inflater, ViewGroup container);

    public void showErrorDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle("Ошибка");
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }
    public void pushFragmentIntoFragment(BaseFragment fragment){
        if (!getChildFragmentManager().popBackStackImmediate(fragment.getClass().getSimpleName(), 0)) {
            getChildFragmentManager().beginTransaction()
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .replace(containerResId(), fragment)
                    .commit();
        }
    }
    protected int containerResId(){
        return 0;
    };

}
