package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.SettingModel;

public class FilterSettingsAdapter extends RecyclerView.Adapter<BaseViewHolder<SettingModel>> {
    private ArrayList<SettingModel> dataSet;

    public filterItemClickListener listener;

    public interface filterItemClickListener{
        void onFilterItemClick(SettingModel settingModel);
    }

    public FilterSettingsAdapter(ArrayList<SettingModel> settingModels, filterItemClickListener listener) {
        this.listener = listener;
        this.dataSet = settingModels;
    }

    @NonNull
    @Override
    public BaseViewHolder<SettingModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<SettingModel>(new SettingItemView(parent.getContext())) {
            @Override
            public void bind(SettingModel item) {
                ((SettingItemView)itemView).bind(item);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder <SettingModel> holder, int position) {
        SettingModel settingModel = dataSet.get(position);
        if (settingModel!=null){
            holder.bind(settingModel);
            holder.setOnClickListener(v -> listener.onFilterItemClick(settingModel));
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
