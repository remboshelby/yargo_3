package yargo.inc.orders.fragments.order_list.filters.custom_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R2;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.filters.FiltersViewModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CategoryModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.utils.FilterCategoryAdapter;

public class FilterCategory extends BaseFragment implements FilterCategoryAdapter.categoryItemClickListener {

    @Inject
    protected FiltersViewModel filtersViewModel;

    @BindView(R2.id.recyclerFilterCategory)
    RecyclerView recyclerFilterCategory;
    private FilterCategoryAdapter filterCategoryAdapter;

    private ArrayList<CategoryModel> categoryArray;
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.filter_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }


    private void init(@NonNull View view) {
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this, view);
        categoryArray = filtersViewModel.createCategoryArray();
        filterCategoryAdapter = new FilterCategoryAdapter(categoryArray, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerFilterCategory.getContext(), layoutManager.getOrientation());
        recyclerFilterCategory.addItemDecoration(dividerItemDecoration);
        recyclerFilterCategory.setLayoutManager(layoutManager);

        recyclerFilterCategory.setAdapter(filterCategoryAdapter);
    }

    @Override
    public void onCategoryItemClick(CategoryModel categoryModel) {
        categoryModel.setChecked(!categoryModel.isChecked());
        categoryArray.set(categoryModel.getId(), categoryModel);
        filterCategoryAdapter.notifyItemChanged(categoryModel.getId());
    }
}
