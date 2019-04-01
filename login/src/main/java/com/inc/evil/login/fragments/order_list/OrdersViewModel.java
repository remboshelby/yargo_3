package com.inc.evil.login.fragments.order_list;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.data.order.OrdersResponse;
import com.inc.evil.common.network.repository.OrdersRepository;

import androidx.lifecycle.MutableLiveData;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;
    private CommonSharedPreferences commonSharedPreferences;

    MutableLiveData<OrdersResponse> dataOrders = new MutableLiveData<>();

    public OrdersViewModel(OrdersRepository ordersRepository, CommonSharedPreferences commonSharedPreferences) {
        this.ordersRepository = ordersRepository;
        this.commonSharedPreferences = commonSharedPreferences;
    }
}
