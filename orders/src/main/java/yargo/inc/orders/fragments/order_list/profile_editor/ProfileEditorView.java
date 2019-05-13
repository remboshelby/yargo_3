package yargo.inc.orders.fragments.order_list.profile_editor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.google.android.material.appbar.AppBarLayout;

import java.util.Arrays;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.login.User;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.profile_editor.custom_view.CustomToolBarEditor;

public class ProfileEditorView extends BaseFragment implements CustomToolBarEditor.profileListener {


    @BindView(R2.id.customToolbar)
    CustomToolBarEditor customToolbar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.editSurname)
    AutoCompleteTextView editSurname;
    @BindView(R2.id.editName)
    AutoCompleteTextView editName;
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

    @Inject
    protected ProfileEditorViewModel profileEditorViewModel;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        OrderListsFragment.getOrdersComponent().inject(this);
        return inflater.inflate(R.layout.profile_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        customToolbar.setListener(this);

        getRoot().setSupportActionBar(toolbar);

        profileEditorViewModel.getUserInfo();
        profileEditorViewModel.observUserLiveData(this, new Observer<User>() {
            @Override
            public void onChanged(User o) {
                editSurname.setText(profileEditorViewModel.getUserMutableLiveData().getValue().getSurname());
                editName.setText(profileEditorViewModel.getUserMutableLiveData().getValue().getSurname());

                editBdate.setText(profileEditorViewModel.getUserMutableLiveData().getValue().getBirthday());

                if (profileEditorViewModel.getUserMutableLiveData().getValue().getSex()==1)
                    sexRadioGroup.check(R.id.sexMale);
                else sexRadioGroup.check(R.id.sexFemale);



                Integer cityId = profileEditorViewModel.getUserMutableLiveData().getValue().getIdCity();
                String[] array = getResources().getStringArray(R.array.citiesId);
                Integer position = Arrays.asList(array).indexOf(cityId);
                spinCity.setSelection(position);
            }
        });

    }

    @Override
    public void onBackPressed() {
        getRoot().onBackPressed();
    }

    @Override
    public void onAcceptPressed() {
        profileEditorViewModel.pushUserInfo();

    }
    @OnTextChanged(R2.id.editSurname)
    void editSurname(){
        profileEditorViewModel.setSurname(editSurname.getText().toString());
    }
    @OnTextChanged(R2.id.editName)
    void editName(){
        profileEditorViewModel.setName(editName.getText().toString());
    }
    @OnItemSelected(R2.id.spinCity)
    void spinnerItemSelected(int position) {
        profileEditorViewModel.setCityId(getResources().getIntArray(R.array.citiesId)[position]);
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
                profileEditorViewModel.setBirthday(mdayOfMonth + "." + (mmonth +1) + "." + myear);
            }
            else
            {
                editBdate.setText(mdayOfMonth + ".0" + (mmonth +1) + "." + myear);
                profileEditorViewModel.setBirthday(mdayOfMonth + ".0" + (mmonth +1) + "." + myear);
            }
        }, year,month,day);
        datePickerDialog.show();
    }

    @OnClick({R2.id.sexMale, R2.id.sexFemale})
    void onRadioButtonClicked(RadioButton radioButton){
        boolean checked = radioButton.isChecked();
        if (radioButton.getId() == R.id.sexMale && checked) {
            profileEditorViewModel.setSex(1);
        }else if (radioButton.getId() == R.id.sexFemale && checked) {
            profileEditorViewModel.setSex(0);
        }
    }
}
