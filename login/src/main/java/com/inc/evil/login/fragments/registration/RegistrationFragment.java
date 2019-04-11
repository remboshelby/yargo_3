package com.inc.evil.login.fragments.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabItem;
import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.login.R;
import com.inc.evil.login.R2;
import com.inc.evil.login.fragments.LoginFragment;
import com.inc.evil.login.utils_view.LockableViewPager;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationFragment extends BaseFragment {

    @Inject
    protected RegistrationViewModel registrationViewModel;

    @BindView(R2.id.imgBtnBackPress)
    ImageButton imgBtnBackPress;
    @BindView(R2.id.tvToobarName_registration)
    TextView tvToobarName_registration;
    @BindView(R2.id.BtnRegistNext)
    Button BtnRegistNext;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.registration_container)
    LockableViewPager registration_container;

    private static final int REGISTR_PAGE_COUNT = 3;

    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected int containerResId() {
        return R.id.registration_container;
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        LoginFragment.getLoginComponent().inject(this);
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);



        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        registration_container.setSwipeable(false);
        registration_container.setAdapter(sectionsPagerAdapter);
        registration_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        super.onViewCreated(view, savedInstanceState);
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
                    return new RegistrConfirmMobile();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return REGISTR_PAGE_COUNT;
        }
    }
}
