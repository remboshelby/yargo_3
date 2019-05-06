package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CategoryModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CityModel;

public class FilterCityAdapter extends RecyclerView.Adapter<BaseViewHolder<CityModel>> {
    private ArrayList<CityModel> dataSet;
    public cityItemClickListener listener;

    public interface cityItemClickListener{
        void onCityItemClick(CityModel cityModel);
    }
    public FilterCityAdapter(ArrayList<CityModel> dataSet,cityItemClickListener cityItemClickListener ) {
        this.listener = cityItemClickListener;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public BaseViewHolder<CityModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<CityModel>(new CityItemView(parent.getContext())) {
            @Override
            public void bind(CityModel item) {
                ((CityItemView)itemView).bind(item);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<CityModel> holder, int position) {
        CityModel cityModel = dataSet.get(position);
        if (cityModel!=null){
            holder.bind(cityModel);
            holder.setOnClickListener(v -> listener.onCityItemClick(cityModel));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
