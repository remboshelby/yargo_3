package yargo.inc.orders.fragments.order_list.instructions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseActivity;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.instructions.custom_view.CustomOffertsToolbar;
import yargo.inc.orders.fragments.order_list.instructions.models.OffertsModel;

import static yargo.inc.orders.fragments.order_list.instructions.custom_view.CustomOffertsToolbar.*;

public class OffertView extends BaseActivity implements OnCustomOffertsToolbarClick {


    @BindView(R2.id.customOffertsToolbar)
    CustomOffertsToolbar customOffertsToolbar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.offertList)
    RecyclerView offertList;
    @BindView(R2.id.mainHeader)
    TextView mainHeader;
    @BindView(R2.id.subHeader)
    TextView subHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        OffertsModel offertData = getOffertData();
        offertData.getSubHeaders();

        mainHeader.setText(offertData.getHeader());
        subHeader.setText(offertData.getHeaderTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        offertList.setLayoutManager(layoutManager);

        OffertAdapter offertAdapter = new OffertAdapter(offertData.getSubHeaders());
        offertList.setAdapter(offertAdapter);
    }

    private void init() {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.offert_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        customOffertsToolbar.setOnCustomOffertsToolbarClick(this);
        customOffertsToolbar.setTitle(getString(R.string.offerts_for_user));
    }


    private OffertsModel getOffertData() {
        String json = null;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.offerts);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "cp1251");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return (OffertsModel) gson.fromJson(json, OffertsModel.class);
    }
    public void onBtnClick(){
        onBackPressed();
    }
}
