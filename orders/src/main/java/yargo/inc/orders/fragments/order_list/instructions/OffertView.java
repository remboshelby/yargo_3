package yargo.inc.orders.fragments.order_list.instructions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;

public class OffertView extends BaseFragment {
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.offert_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OffertsModel offertData = getOffertData();
        offertData.getSubHeaders();
    }

    private OffertsModel getOffertData() {
        String json = null;
        try {
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.offerts);
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
}
