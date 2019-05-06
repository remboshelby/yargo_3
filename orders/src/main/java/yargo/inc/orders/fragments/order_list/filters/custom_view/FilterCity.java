package yargo.inc.orders.fragments.order_list.filters.custom_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.orders.R2;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.filters.FiltersView;
import yargo.inc.orders.fragments.order_list.filters.FiltersViewModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CityModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.utils.FilterCityAdapter;

public class FilterCity extends BaseFragment implements FilterCityAdapter.cityItemClickListener {

    @BindView(R2.id.filterCityRecycler)
    RecyclerView filterCityRecycler;
    @Inject
    protected FiltersViewModel filtersViewModel;
    @Inject
    protected CommonSharedPreferences commonSharedPreferences;
    private FilterCityAdapter filterCityAdapter;
    private ArrayList<CityModel> cityModels;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.filter_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void initRecyclerView() {
        cityModels = getCitiesArray();
        filterCityAdapter = new FilterCityAdapter(cityModels, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(filterCityRecycler.getContext(), layoutManager.getOrientation());
        filterCityRecycler.addItemDecoration(dividerItemDecoration);
        filterCityRecycler.setLayoutManager(layoutManager);
        filterCityRecycler.setAdapter(filterCityAdapter);
    }

    private ArrayList<CityModel> getCitiesArray() {
        String filteredCityName = commonSharedPreferences.getFilteredCityName();
        String[] cityId = getResources().getStringArray(R.array.citiesId);
        String[] cityName = getResources().getStringArray(R.array.citiesName);
        ArrayList<CityModel> cityModels = new ArrayList<>();
        for (int i = 0; i < cityId.length; i++) {
            if (cityName[i].equals(filteredCityName)) {
                cityModels.add(new CityModel(Integer.valueOf(cityId[i]), cityName[i], true));
            } else cityModels.add(new CityModel(Integer.valueOf(cityId[i]), cityName[i], false));
        }
        return cityModels;
    }

    private void init(@NonNull View view) {
        ((FiltersView) getParentFragment()).setTitle(getString(R.string.cities));
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this, view);
        ((FiltersView) getParentFragment()).setTitle(getString(R.string.cities));



        initRecyclerView();
    }

    @Override
    public void onCityItemClick(CityModel cityModel) {
        commonSharedPreferences.putObject(CommonSharedPreferences.FILTERED_CITY, cityModel.getId());
        cityModels.clear();
        cityModels.addAll(getCitiesArray());
        filterCityAdapter.notifyDataSetChanged();
    }
}
