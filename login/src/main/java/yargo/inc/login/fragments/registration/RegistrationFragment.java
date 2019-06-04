package yargo.inc.login.fragments.registration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.registration_pages.RegistrEnd;
import yargo.inc.login.fragments.registration.registration_pages.RegistrMobilePhone;
import yargo.inc.login.fragments.registration.registration_pages.RegistrPersonalData;
import yargo.inc.login.utils_view.LockableViewPager;

public class RegistrationFragment extends BaseFragment implements RegistrEnd.RegistrationEndListener {
    protected static RegistrationViewModel registrationViewModel;

    @Inject
    protected CommonSharedPreferences commonSharedPreferences;

    @BindView(R2.id.tabLayout)
    TabLayout tabLayout;
    private ApplicationNavigator navigator;

    @BindView(R2.id.imgBtnBackPress)
    ImageButton imgBtnBackPress;
    @BindView(R2.id.tvToobarNameRegistration)
    TextView tvToobarNameRegistration;

    @BindView(R2.id.btnRegistNext)
    Button btnRegistNext;

    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.registrationContainer)
    LockableViewPager registrationСontainer;
    private static final int REGISTR_PAGE_COUNT = 3;
    private SectionsPagerAdapter sectionsPagerAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected int containerResId() {
        return R.id.registrationContainer;
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        LoginFragment.getLoginComponent().inject(this);
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        init();

        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        registrationСontainer.setSwipeable(false);
        registrationСontainer.setAdapter(sectionsPagerAdapter);
        registrationViewModel.observeRegistrationStatus(this, status -> {
            progressDialog.cancel();

            if (status == registrationViewModel.REGISTR_SUCCESS) {
                navigator.openFragment(getRoot(), "Orders");
            } else if (status == registrationViewModel.ERROR_PHONE) {
                showErrorDialog(getString(R.string.mobile_number_incorrect));
            } else if (status == registrationViewModel.ERROR_EMAIL) {
                showErrorDialog(getString(R.string.email_incorrect));
            } else if (status == registrationViewModel.ERROR_EMAIL_AND_PHONE) {
                showErrorDialog(getString(R.string.email_and_phone_incorrect));
            } else if (status == registrationViewModel.UNKNOWN_ERROR) {
                showErrorDialog(getString(R.string.somethings_goes_wrong));
            }
        });
        registrationViewModel.observeBtnStatus(this, aBoolean -> {
            if (aBoolean) {
                btnRegistNext.setVisibility(View.VISIBLE);
            } else {
                btnRegistNext.setVisibility(View.GONE);
            }
        });
        registrationСontainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTvToobarNameRegistration(registrationViewModel.getTitle(position));
                registrationViewModel.getBtnStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        registrationСontainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(registrationСontainer));
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        registrationViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RegistrationViewModel();
            }
        }).get(RegistrationViewModel.class);
        navigator = LoginFragment.getNavigator();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.loading));
        progressDialog.show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RegistrPersonalData();
                case 1:
                    return new RegistrMobilePhone();
                case 2:
                    return new RegistrEnd(RegistrationFragment.this);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return REGISTR_PAGE_COUNT;
        }


    }

    @OnClick(R2.id.btnRegistNext)
    public void onBtnRegistNextClick() {
        if (registrationСontainer.getCurrentItem() != 2) {
            registrationСontainer.setCurrentItem(registrationСontainer.getCurrentItem() + 1);
        }
    }

    @OnClick(R2.id.imgBtnBackPress)
    public void onBtnBackPressClick() {
        if (registrationСontainer.getCurrentItem() > 0) {
            registrationСontainer.setCurrentItem(registrationСontainer.getCurrentItem() - 1);
        } else getRoot().pushFragment(new LoginFragment(), false);
    }

    public void setTvToobarNameRegistration(String label) {
        this.tvToobarNameRegistration.setText(label);
    }

    public static RegistrationViewModel getRegistrationViewModel() {
        return registrationViewModel;
    }

    @Override
    public void onDestroyView() {
        registrationViewModel = null;
        super.onDestroyView();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
