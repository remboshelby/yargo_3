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
import yargo.inc.orders.fragments.order_list.filters.FiltersView;
import yargo.inc.orders.fragments.order_list.filters.FiltersViewModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.SettingModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.utils.FilterSettingsAdapter;

public class FilterSettings extends BaseFragment implements FilterSettingsAdapter.filterItemClickListener {

    @Inject
    protected FiltersViewModel filtersViewModel;

    private ArrayList<SettingModel> settingsList = new ArrayList<>();

    @BindView(R2.id.recyclerFilterSettings)
    RecyclerView recyclerFilterSettings;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        OrderListsFragment.getOrdersComponent().inject(this);
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(@NonNull View view) {
        ButterKnife.bind(this, view);
        settingsList = filtersViewModel.createSettingsArray();
        FilterSettingsAdapter filterSettingsAdapter = new FilterSettingsAdapter(settingsList, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerFilterSettings.getContext(), layoutManager.getOrientation());
        recyclerFilterSettings.addItemDecoration(dividerItemDecoration);
        recyclerFilterSettings.setLayoutManager(layoutManager);

        recyclerFilterSettings.setAdapter(filterSettingsAdapter);
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.filter_settings, container, false);
    }

    @Override
    public void onFilterItemClick(SettingModel settingModel) {
        if (settingModel.getId() == 1) ((FiltersView) getParentFragment()).pushCategoryFragment();
        else ((FiltersView) getParentFragment()).pushCityFragment();
    }
}
