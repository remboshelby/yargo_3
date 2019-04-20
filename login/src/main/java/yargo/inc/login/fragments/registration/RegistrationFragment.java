package yargo.inc.login.fragments.registration;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.registration_pages.RegistrConfirmMobile;
import yargo.inc.login.fragments.registration.registration_pages.RegistrEnd;
import yargo.inc.login.fragments.registration.registration_pages.RegistrMobilePhone;
import yargo.inc.login.fragments.registration.registration_pages.RegistrPersonalData;
import yargo.inc.login.utils_view.LockableViewPager;

public class RegistrationFragment extends BaseFragment {


    @Inject
    protected RegistrationViewModel registrationViewModel;

    @BindView(R2.id.tabLayout)
    TabLayout tabLayout;
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
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        registrationViewModel.observeBtnStatus(this, aBoolean -> {
            if (aBoolean){
                btnRegistNext.setVisibility(View.VISIBLE);
            }
            else {
                btnRegistNext.setVisibility(View.GONE);
            }
        });
        registrationСontainer.setSwipeable(false);
        registrationСontainer.setAdapter(sectionsPagerAdapter);
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
                    return new RegistrEnd();
//                    return new RegistrConfirmMobile();
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
    public void onBtnRegistNextClick(){
        if(registrationСontainer.getCurrentItem()!=2){
            registrationСontainer.setCurrentItem(registrationСontainer.getCurrentItem()+1);
        }
    }
    @OnClick(R2.id.imgBtnBackPress)
    public void onBtnBackPressClick(){
        if (registrationСontainer.getCurrentItem()>0) {
            registrationСontainer.setCurrentItem(registrationСontainer.getCurrentItem()-1);
        }
        else getRoot().onBackPressed();
    }
    public void setTvToobarNameRegistration(String label) {
        this.tvToobarNameRegistration.setText(label);
    }
}
