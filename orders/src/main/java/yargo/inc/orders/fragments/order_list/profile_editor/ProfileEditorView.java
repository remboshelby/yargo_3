package yargo.inc.orders.fragments.order_list.profile_editor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.login.User;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.profile_editor.custom_view.CustomToolBarEditor;

public class ProfileEditorView extends BaseFragment implements CustomToolBarEditor.profileListener {
    @BindView(R2.id.customToolbar)
    CustomToolBarEditor customToolbar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.editSurname)
    EditText editSurname;
    @BindView(R2.id.editName)
    EditText editName;
    @BindView(R2.id.cityId)
    TextView cityId;
    @BindView(R2.id.spinCity)
    Spinner spinCity;
    @BindView(R2.id.sexMale)
    RadioButton sexMale;
    @BindView(R2.id.sexFemale)
    RadioButton sexFemale;
    @BindView(R2.id.sexRadioGroup)
    RadioGroup sexRadioGroup;
    @BindView(R2.id.editBdate)
    AutoCompleteTextView editBdate;

    private ProfileEditorViewModel profileEditorViewModel;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.profile_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                profileEditorViewModel.setName(editName.getText().toString());
            }
        });
        editSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                profileEditorViewModel.setSurname(editSurname.getText().toString());
            }
        });

        profileEditorViewModel.observUserLiveData(this, new Observer<User>() {
            @Override
            public void onChanged(User o) {

                editSurname.setText(profileEditorViewModel.getUserMutableLiveData().getValue().getSurname());
                editName.setText(profileEditorViewModel.getUserMutableLiveData().getValue().getUsername());

                editBdate.setText(profileEditorViewModel.getUserMutableLiveData().getValue().getBirthday());

                if (profileEditorViewModel.getUserMutableLiveData().getValue().getSex() == 1)
                    sexRadioGroup.check(R.id.sexMale);
                else sexRadioGroup.check(R.id.sexFemale);

                setCitySpinnerSelection();
            }
        });

        profileEditorViewModel.observProfileChanged(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(getContext(), getString(R.string.profile_changed), Toast.LENGTH_SHORT).show();
                    profileEditorViewModel.setSomeFieldIsChangedMLD(false);
                    hideKeyboard();
                }
            }
        });

        profileEditorViewModel.observFieldChanging(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isChanged) {
                customToolbar.setBtnCheckVisibility(isChanged);
            }
        });

    }

    private void init(@NonNull View view) {
        ButterKnife.bind(this, view);
        getRoot().setSupportActionBar(toolbar);
        customToolbar.setListener(this);

        profileEditorViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProfileEditorViewModel(getActivity().getApplication());
            }
        }).get(ProfileEditorViewModel.class);
    }

    private void setCitySpinnerSelection() {
        String cityId = String.valueOf(profileEditorViewModel.getUserMutableLiveData().getValue().getIdCity());
        String[] array = getResources().getStringArray(R.array.citiesId);
        Integer position = Arrays.asList(array).indexOf(cityId);
        spinCity.setSelection(position);


    }

    @Override
    public void onBackPressed() {
        getRoot().onBackPressed();
    }

    @Override
    public void onAcceptPressed() {

        showAccomplishedProfileChanging(getContext());

    }

    @OnItemSelected(value = R2.id.spinCity, callback = OnItemSelected.Callback.ITEM_SELECTED)
    void spinnerItemSelected(int position) {
        profileEditorViewModel.setCityId(Integer.valueOf(getResources().getStringArray(R.array.citiesId)[position]));
    }

    @OnClick({R2.id.editBdate})
    void chooseBirthday() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, myear, mmonth, mdayOfMonth) -> {
            if (mmonth > 8) {
                editBdate.setText(mdayOfMonth + "." + (mmonth + 1) + "." + myear);
                profileEditorViewModel.setBirthday(mdayOfMonth + "." + (mmonth + 1) + "." + myear);
            } else {
                editBdate.setText(mdayOfMonth + ".0" + (mmonth + 1) + "." + myear);
                profileEditorViewModel.setBirthday(mdayOfMonth + ".0" + (mmonth + 1) + "." + myear);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @OnClick({R2.id.sexMale, R2.id.sexFemale})
    void onRadioButtonClicked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        if (radioButton.getId() == R.id.sexMale && checked) {
            profileEditorViewModel.setSex(1);
        } else if (radioButton.getId() == R.id.sexFemale && checked) {
            profileEditorViewModel.setSex(0);
        }
    }


    public void showAccomplishedProfileChanging(Context context) {
        AlertDialog.Builder getOrder = new AlertDialog.Builder(context);
        getOrder.setMessage(getResources().getString(R.string.profile_chandging_confirmation)).setCancelable(false).
                setPositiveButton(getResources().getString(R.string.YES), (dialogInterface, i) -> profileEditorViewModel.pushUserInfo())
                .setNegativeButton(getResources().getString(R.string.NO), (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertDialog = getOrder.create();
        alertDialog.setTitle(getResources().getString(R.string.request));
        alertDialog.show();
    }

    public void hideKeyboard() {
        if (getRoot().getCurrentFocus()!=null) {
            InputMethodManager imm = (InputMethodManager) getRoot().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getRoot().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
