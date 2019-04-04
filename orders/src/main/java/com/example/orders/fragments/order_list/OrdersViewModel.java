package com.example.orders.fragments.order_list;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.models.order.OrdersItem;
import com.inc.evil.common.network.models.order.OrdersResponse;
import com.inc.evil.common.network.repository.OrdersRepository;

import java.util.List;
import java.util.UUID;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.inc.evil.common.dto.CommonSharedPreferences.APP_ID;
import static com.inc.evil.common.dto.CommonSharedPreferences.AUTH_KEY;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;
    private CommonSharedPreferences commonSharedPreferences;

    private MutableLiveData<List<OrdersItem>> dataOrderListItem = new MutableLiveData<>();

    private String appId;
    private String authKey;

    public OrdersViewModel(OrdersRepository ordersRepository, CommonSharedPreferences commonSharedPreferences) {
        this.ordersRepository = ordersRepository;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public void fecthVacantOrders() {
        this.appId = UUID.randomUUID().toString();
        this.authKey = (String) commonSharedPreferences.getObject(AUTH_KEY, String.class);
        addDisposible(ordersRepository.getAllVacantOrders(authKey, appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<OrdersItem>>() {
                               @Override
                               public void accept(List<OrdersItem> ordersItemList) throws Exception {
                                   dataOrderListItem.postValue(ordersItemList);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        }));
    }

    ;
}
