package yargo.inc.common.network.repository;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import androidx.room.util.StringUtil;
import io.reactivex.functions.Function;
import yargo.inc.common.database.VacantOrdersDao;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.models.order.OrdersResponse;

import java.util.ArrayList;
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

    private static final int PAGE_SIZE =5;

    public OrdersRepository(OrderApiService orderApiService, VacantOrdersDao vacantOrdersDao, CommonSharedPreferences commonSharedPreferences) {
        this.orderApiService = orderApiService;
        this.vacantOrdersDao = vacantOrdersDao;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public Observable<List<OrdersItem>> getAllVacantOrdersFotView(int size, int startPos, String orderDescription){
        return getAllVacantOrders(orderDescription).map(ordersItems -> {
            int outputSize = size+startPos;
            if (outputSize>ordersItems.size())
                outputSize = ordersItems.size();

            List<OrdersItem> ordersItems1 = new ArrayList<>();
            for (int i = startPos; i< outputSize; i++)
            {ordersItems1.add(ordersItems.get(i)); }
            return ordersItems1;
        }).delay(20, TimeUnit.MILLISECONDS);
    }

    public Observable<List<OrdersItem>> getAllVacantOrders(String orderDescription) {
        String appId = UUID.randomUUID().toString();
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return Observable.concatArrayEager(getAllVacantOrdersFromDb(), getVacantOrdersFromRemote(authKey, appId))
                .materialize()
                .observeOn(Schedulers.io())
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
                .map(new Function<List<OrdersItem>, List<OrdersItem>>() {
                    @Override
                    public List<OrdersItem> apply(List<OrdersItem> ordersItems) throws Exception {
                        List<OrdersItem> fileredOrdersItem = new ArrayList<>();
                        for (OrdersItem ordersItem: ordersItems){
                            if (StringUtils.containsIgnoreCase(ordersItem.getDescription(), orderDescription))
                                fileredOrdersItem.add(ordersItem);
                        }
                        return fileredOrdersItem;
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS);
    };

    public Observable<List<OrdersItem>> getVacantOrdersFromRemote(String authKey, String appId) {
        return orderApiService.getVacantOrders(authKey, appId).map(new Function<OrdersResponse, List<OrdersItem>>() {
            @Override
            public List<OrdersItem> apply(OrdersResponse ordersResponse) throws Exception {
                return ordersResponse.getResponse().getOrders();
            }
        })
                .doOnNext(ordersItems -> {
                    safeVacantOrderInBd(ordersItems);
                });
    }

    public void safeVacantOrderInBd(final List<OrdersItem> vacantOrderList) {
        Observable.fromCallable(() -> {
            vacantOrdersDao.removeAll();
            vacantOrdersDao.insertAll(vacantOrderList);
            return vacantOrderList;
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(ordersItemList -> Log.d(TAG, "vacant order saved in DB, list count: " + ordersItemList.size()));
    }

    public Observable<List<OrdersItem>> getAllVacantOrdersFromDb() {
        return vacantOrdersDao.getAllVacantOrders().filter(ordersItemList -> ordersItemList.size() != 0).toObservable();
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

    public static int getPageSize() {
        return PAGE_SIZE;
    }
}
