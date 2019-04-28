package yargo.inc.orders.fragments.order_list.order_detailse.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yargo.inc.common.base.BaseViewHolder;

public class OrderDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        switch (viewType){
//            case 0: return new ViewHolder0();
//            case 1: return new ViewHolder1();
//            case 2: return new ViewHolder2();
//            case 3: return new ViewHolder3();
//
//            default: return new ViewHolder0();
//        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}