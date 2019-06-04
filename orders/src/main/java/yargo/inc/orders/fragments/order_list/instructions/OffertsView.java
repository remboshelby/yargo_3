package yargo.inc.orders.fragments.order_list.instructions;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.instructions.models.OffertsModel;

public class OffertsView extends BaseFragment {
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.offert_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        OffertsModel offertsModel = gson.fromJson(json, OffertsModel.class);
        offertsModel.getHeader();
    }
}
