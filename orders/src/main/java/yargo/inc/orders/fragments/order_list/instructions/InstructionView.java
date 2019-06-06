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
import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.instructions.custom_view.CustomOffertsToolbar;
import yargo.inc.orders.fragments.order_list.instructions.custom_view.CustomOffertsToolbar.OnCustomOffertsToolbarClick;
import yargo.inc.orders.fragments.order_list.instructions.models.OffertsModel;

public class InstructionView extends BaseFragment implements OnCustomOffertsToolbarClick {

    @BindView(R2.id.customOffertsToolbar)
    CustomOffertsToolbar customOffertsToolbar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.mainHeader)
    TextView mainHeader;
    @BindView(R2.id.instructionList)
    RecyclerView instructionList;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.instruction_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        getRoot().setSupportActionBar(toolbar);

        customOffertsToolbar.setOnCustomOffertsToolbarClick(this);
        customOffertsToolbar.setTitle(getString(R.string.menu_instruction));

        OffertsModel offertData = getOffertData();
        offertData.getSubHeaders();

        mainHeader.setText(offertData.getHeader());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
        instructionList.setLayoutManager(layoutManager);

        OffertAdapter offertAdapter = new OffertAdapter(offertData.getSubHeaders());
        instructionList.setAdapter(offertAdapter);
    }
    private OffertsModel getOffertData() {
        String json = null;
        try {
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.instruction);
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
        getRoot().onBackPressed();
    }
}
