package yargo.inc.common.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

import butterknife.ButterKnife;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);
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
                    .replace(containerResId(), fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }
    protected int containerResId(){
        return 0;
    };

    public void hideKeyboard() {
        if (getRoot().getCurrentFocus()!=null) {
            InputMethodManager imm = (InputMethodManager) getRoot().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getRoot().getCurrentFocus().getWindowToken(), 0);
        }
    }

}
