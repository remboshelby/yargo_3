package yargo.inc.common.network.repository;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import io.reactivex.Notification;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import yargo.inc.common.database.UserOrdersDao;
import yargo.inc.common.database.VacantOrdersDao;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.models.user_order.UserOrderResponse;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.common.network.models.vacant_order.OrdersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class OrdersRepository {
    private OrderApiService orderApiService;
    private VacantOrdersDao vacantOrdersDao;
    private UserOrdersDao userOrdersDao;

    private static final String TAG = OrdersRepository.class.getSimpleName();
    private CommonSharedPreferences commonSharedPreferences;

    private static final int PAGE_SIZE =5;

    public OrdersRepository(OrderApiService orderApiService, VacantOrdersDao vacantOrdersDao,UserOrdersDao userOrdersDao, CommonSharedPreferences commonSharedPreferences) {
        this.orderApiService = orderApiService;
        this.vacantOrdersDao = vacantOrdersDao;
        this.userOrdersDao = userOrdersDao;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public Observable<List<VacantOrderItem>> getAllVacantOrdersFotView(int size, int startPos, String orderDescription){
        return getAllVacantOrders(orderDescription).map(ordersItems -> {
            int outputSize = size+startPos;
            if (outputSize>ordersItems.size())
                outputSize = ordersItems.size();

            List<VacantOrderItem> vacantOrderItems1 = new ArrayList<>();
            for (int i = startPos; i< outputSize; i++)
            {
                vacantOrderItems1.add(ordersItems.get(i)); }
            return vacantOrderItems1;
        }).delay(20, TimeUnit.MILLISECONDS);
    }


    public Observable<List<VacantOrderItem>> getAllVacantOrders(String orderDescription) {
        String appId = UUID.randomUUID().toString();
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return Observable.concatArrayEager(getAllVacantOrdersFromDb(), getVacantOrdersFromRemote(authKey, appId))
                .materialize()
                .observeOn(Schedulers.io())
                .map(new Function<Notification<List<VacantOrderItem>>, Notification<List<VacantOrderItem>>>() {
                    @Override
                    public Notification<List<VacantOrderItem>> apply(Notification<List<VacantOrderItem>> listNotification) throws Exception {
                        if (listNotification.getError() != null) {
                            Log.e(TAG, "vacantOrders Exeception: " + listNotification.getError().toString());
                        }
                        return listNotification;
                    }
                })
                .filter(new Predicate<Notification<List<VacantOrderItem>>>() {
                    @Override
                    public boolean test(Notification<List<VacantOrderItem>> listNotification) throws Exception {
                        return !listNotification.isOnError();
                    }
                })
                .dematerialize(new Function<Notification<List<VacantOrderItem>>, Notification<List<VacantOrderItem>>>() {
                    @Override
                    public Notification<List<VacantOrderItem>> apply(Notification<List<VacantOrderItem>> listNotification) throws Exception {
                        return listNotification;
                    }
                })
                .map(vacantOrderItems -> {
                    List<VacantOrderItem> fileredVacantOrderItem = new ArrayList<>();
                    for (VacantOrderItem vacantOrderItem : vacantOrderItems){
                        //TODO Нужно добавить Фильтрацию по городам, а то отображаются ненужные заказы
                        if (StringUtils.containsIgnoreCase(vacantOrderItem.getDescription(), orderDescription))
                            fileredVacantOrderItem.add(vacantOrderItem);
                    }
                    return fileredVacantOrderItem;
                })
                .debounce(400, TimeUnit.MILLISECONDS);
    };

    public Observable<List<UserOrdersItem>> getAllUserOrders(){
        String appId = UUID.randomUUID().toString();
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return Observable.concatArrayEager(getAllUserOrdersFromDb(), getUserOrdersFromRemote(authKey,appId))
                .materialize()
                .observeOn(Schedulers.io())
                .map(new Function<Notification<List<UserOrdersItem>>, Notification<List<UserOrdersItem>>>() {
                    @Override
                    public Notification<List<UserOrdersItem>> apply(Notification<List<UserOrdersItem>> listNotification) throws Exception {
                        if (listNotification.getError() != null) {
                            Log.e(TAG, "vacantOrders Exeception: " + listNotification.getError().toString());
                        }
                        return listNotification;
                    }
                })
                .filter(new Predicate<Notification<List<UserOrdersItem>>>() {
                    @Override
                    public boolean test(Notification<List<UserOrdersItem>> listNotification) throws Exception {
                        return !listNotification.isOnError();
                    }
                }).dematerialize(new Function<Notification<List<UserOrdersItem>>, Notification<List<UserOrdersItem>>>() {
                    @Override
                    public Notification<List<UserOrdersItem>> apply(Notification<List<UserOrdersItem>> listNotification) throws Exception {
                        return listNotification;
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS);
    }

    public Observable<List<VacantOrderItem>> getVacantOrdersFromRemote(String authKey, String appId) {
        return orderApiService.getVacantOrders(authKey, appId).map(new Function<OrdersResponse, List<VacantOrderItem>>() {
            @Override
            public List<VacantOrderItem> apply(OrdersResponse ordersResponse) throws Exception {
                return ordersResponse.getResponse().getOrders();
            }
        })
                .doOnNext(ordersItems -> {
                    safeVacantOrderInBd(ordersItems);
                });
    }
    public Observable<List<UserOrdersItem>> getUserOrdersFromRemote(String authKey, String appId) {
        return orderApiService.getUsersOrders(authKey, appId).map(new Function<UserOrderResponse, List<UserOrdersItem>>() {
            @Override
            public List<UserOrdersItem> apply(UserOrderResponse userOrderResponse) throws Exception {
                return userOrderResponse.getOrders();
            }
        })
        .doOnNext(userOrdersItems -> safeUserOrderInBd(userOrdersItems));
    }

    public void safeVacantOrderInBd(final List<VacantOrderItem> vacantOrderList) {
        Observable.fromCallable(() -> {
            vacantOrdersDao.removeAll();
            vacantOrdersDao.insertAll(vacantOrderList);
            return vacantOrderList;
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(ordersItemList -> Log.d(TAG, "vacant order saved in DB, list count: " + ordersItemList.size()));
    }
    public void safeUserOrderInBd(final List<UserOrdersItem> userOrdersItems) {
        Observable.fromCallable(() -> {
            userOrdersDao.removeAll();
            userOrdersDao.insertAll(userOrdersItems);
            return userOrdersItems;
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(ordersItemList -> Log.d(TAG, "vacant order saved in DB, list count: " + ordersItemList.size()));
    }


    public Observable<List<VacantOrderItem>> getAllVacantOrdersFromDb() {
        return vacantOrdersDao.getAllVacantOrders().filter(ordersItemList -> ordersItemList.size() != 0).toObservable();
    }
    public Observable<List<UserOrdersItem>> getAllUserOrdersFromDb() {
        return userOrdersDao.getAllUserOrders().filter(ordersItemList -> ordersItemList.size() != 0).toObservable();
    }

//    public Observable<OrdersResponse> getVacantOrders(String authKey, String appId) {
//        return orderApiService.getVacantOrders(authKey, appId);
//    }
//
//    public Observable<OrdersResponse> getUsersOrders(String authKey, String appId) {
//        return orderApiService.getUsersOrders(authKey, appId);
//    }
//
//    public Observable<OrdersResponse> getOrderWatchedCount(String authKey, String appId, String idOrder) {
//        return orderApiService.getOrderWatchedCount(authKey, appId, idOrder);
//    }

    public static int getPageSize() {
        return PAGE_SIZE;
    }
}
