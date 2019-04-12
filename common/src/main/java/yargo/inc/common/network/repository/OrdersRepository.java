package yargo.inc.common.network.repository;

import android.util.Log;

import yargo.inc.common.database.VacantOrdersDao;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.models.order.OrdersResponse;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class OrdersRepository {
    private OrderApiService orderApiService;
    private VacantOrdersDao vacantOrdersDao;
    private static final String TAG = OrdersRepository.class.getSimpleName();
    private CommonSharedPreferences commonSharedPreferences;

    public OrdersRepository(OrderApiService orderApiService, VacantOrdersDao vacantOrdersDao, CommonSharedPreferences commonSharedPreferences) {
        this.orderApiService = orderApiService;
        this.vacantOrdersDao = vacantOrdersDao;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public Observable<OrdersResponse> getVacantOrders(String authKey, String appId) {
        return orderApiService.getVacantOrders(authKey, appId);
    }

    public Observable<OrdersResponse> getUsersOrders(String authKey, String appId) {
        return orderApiService.getUsersOrders(authKey, appId);
    }

    public Observable<OrdersResponse> getOrderWatchedCount(String authKey, String appId, String idOrder) {
        return orderApiService.getOrderWatchedCount(authKey, appId, idOrder);
    }

    public Observable<List<OrdersItem>> getAllVacantOrders() {
        String appId = UUID.randomUUID().toString();
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return Observable.concatArrayEager(getAllVacantOrdersFromDb(), getVacantOrdersFromRemote(authKey, appId))
                .materialize()
                .observeOn(AndroidSchedulers.mainThread())
                .map(listNotification -> {
                    if (listNotification.getError() != null) {
                        Log.e(TAG, "vacantOrders Exeception: " + listNotification.getError().toString());
                    }
                    return listNotification;
                })
                .filter(new Predicate<Notification<List<OrdersItem>>>() {
                    @Override
                    public boolean test(Notification<List<OrdersItem>> listNotification) throws Exception {
                        return !listNotification.isOnError();
                    }
                })
                .dematerialize(listNotification -> listNotification)
                .debounce(400, TimeUnit.MILLISECONDS);
    };

    public Observable<List<OrdersItem>> getVacantOrdersFromRemote(String authKey, String appId) {
        return orderApiService.getVacantOrders(authKey, appId).map(ordersResponse -> ordersResponse.getResponse().getOrders())
                .doOnNext(ordersItems -> {
                    safeVacantOrderInBd(ordersItems);
                });
    }

    public void safeVacantOrderInBd(final List<OrdersItem> vacantOrderList) {
        Observable.fromCallable(() -> {
            vacantOrdersDao.insertAll(vacantOrderList);
            return vacantOrderList;
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(ordersItemList -> Log.d(TAG, "vacant order saved in DB, list count: " + ordersItemList.size()));
    }

    public Observable<List<OrdersItem>> getAllVacantOrdersFromDb() {
        return vacantOrdersDao.getAllVacantOrders().filter(new Predicate<List<OrdersItem>>() {
            @Override
            public boolean test(List<OrdersItem> ordersItemList) throws Exception {
                return ordersItemList.size() != 0;
            }
        }).toObservable();
    }

}
