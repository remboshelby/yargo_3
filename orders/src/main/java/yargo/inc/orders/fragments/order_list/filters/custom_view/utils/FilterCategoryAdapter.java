package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CategoryModel;

public class FilterCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder<CategoryModel>> {
    private ArrayList<CategoryModel> dataSet;

    public categoryItemClickListener listener;

    public interface categoryItemClickListener{
        void onCategoryItemClick(CategoryModel categoryModel);
    }

    public FilterCategoryAdapter(ArrayList<CategoryModel> dataSet,categoryItemClickListener listener) {
        this.listener = listener;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public BaseViewHolder<CategoryModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<CategoryModel>(new CategoryItemView(parent.getContext())) {
            @Override
            public void bind(CategoryModel item) {
                ((CategoryItemView)itemView).bind(item);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<CategoryModel> holder, int position) {
        CategoryModel categoryModel = dataSet.get(position);
        if (categoryModel!=null){
            holder.bind(categoryModel);
            holder.setOnClickListener(v -> listener.onCategoryItemClick(categoryModel));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
