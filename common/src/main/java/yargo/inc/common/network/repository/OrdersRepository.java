package yargo.inc.common.network.repository;

import android.content.Context;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import io.reactivex.Notification;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import yargo.inc.common.R;
import yargo.inc.common.database.OrdersDao;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.models.order_list.OrderResponse;
import yargo.inc.common.network.models.order_list.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static yargo.inc.common.dto.CommonSharedPreferences.FILTERED_CITY;

public class OrdersRepository {
    private OrderApiService orderApiService;
    private OrdersDao ordersDao;

    private static final String TAG = OrdersRepository.class.getSimpleName();
    private CommonSharedPreferences commonSharedPreferences;

    private static final int PAGE_SIZE =5;
    private Context mContext;

    public OrdersRepository(OrderApiService orderApiService, OrdersDao ordersDao, CommonSharedPreferences commonSharedPreferences, Context context) {
        this.orderApiService = orderApiService;
        this.ordersDao = ordersDao;
        this.mContext = context;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public Observable<List<OrderItem>> getAllVacantOrdersForView(int size, int startPos, String orderDescription){
        return getAllVacantOrders(orderDescription).map(ordersItems -> {
            int outputSize = size+startPos;
            if (outputSize>ordersItems.size())
                outputSize = ordersItems.size();

            List<OrderItem> orderItems1 = new ArrayList<>();
            for (int i = startPos; i< outputSize; i++)
            {
                orderItems1.add(ordersItems.get(i)); }
            return orderItems1;
        }).delay(20, TimeUnit.MILLISECONDS);
    }

    public Observable<List<OrderItem>> getAllUserOrdersForView(int size, int startPos, int categoryOrderId){
        return getAllUserOrders(categoryOrderId).map(new Function<List<OrderItem>, List<OrderItem>>() {
            @Override
            public List<OrderItem> apply(List<OrderItem> ordersItems) throws Exception {
                int outputSize = size + startPos;
                if (outputSize > ordersItems.size())
                    outputSize = ordersItems.size();

                List<OrderItem> userOrderItems1 = new ArrayList<>();
                for (int i = startPos; i < outputSize; i++) {
                    userOrderItems1.add(ordersItems.get(i));
                }
                return userOrderItems1;
            }
        }).delay(20, TimeUnit.MILLISECONDS);
    }


    public Observable<List<OrderItem>> getAllVacantOrders(String orderName) {
        String appId = (String) commonSharedPreferences.getObject(CommonSharedPreferences.APP_ID, String.class);
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return Observable.concatArrayEager(getAllVacantOrdersFromDb(orderName), getVacantOrdersFromRemote(authKey, appId))
                .materialize()
                .observeOn(Schedulers.io())
                .map(new Function<Notification<List<OrderItem>>, Notification<List<OrderItem>>>() {
                    @Override
                    public Notification<List<OrderItem>> apply(Notification<List<OrderItem>> listNotification) throws Exception {
                        if (listNotification.getError() != null) {
                            Log.e(TAG, "vacantOrders Exeception: " + listNotification.getError().toString());
                        }
                        return listNotification;
                    }
                })
                .filter(new Predicate<Notification<List<OrderItem>>>() {
                    @Override
                    public boolean test(Notification<List<OrderItem>> listNotification) throws Exception {
                        return !listNotification.isOnError();
                    }
                })
                .dematerialize(new Function<Notification<List<OrderItem>>, Notification<List<OrderItem>>>() {
                    @Override
                    public Notification<List<OrderItem>> apply(Notification<List<OrderItem>> listNotification) throws Exception {
                        return listNotification;
                    }
                })
//                .map(vacantOrderItems -> {
//                    List<OrderItem> fileredOrderItem = new ArrayList<>();
//                    for (OrderItem orderItem : vacantOrderItems){
//                        //TODO Нужно добавить Фильтрацию по городам, а то отображаются ненужные заказы
//                        if (StringUtils.containsIgnoreCase(orderItem.getName(), orderName))
//                            fileredOrderItem.add(orderItem);
//                    }
//                    return fileredOrderItem;
//                })
                .debounce(400, TimeUnit.MILLISECONDS);
    };

    public Observable<List<OrderItem>> getAllUserOrders(int categoryOrderId){
        String appId = (String) commonSharedPreferences.getObject(CommonSharedPreferences.APP_ID, String.class);
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return Observable.concatArrayEager(getAllUserOrdersFromDb(), getUserOrdersFromRemote(authKey,appId))
                .materialize()
                .observeOn(Schedulers.io())
                .map(new Function<Notification<List<OrderItem>>, Notification<List<OrderItem>>>() {
                    @Override
                    public Notification<List<OrderItem>> apply(Notification<List<OrderItem>> listNotification) throws Exception {
                        if (listNotification.getError() != null) {
                            Log.e(TAG, "vacantOrders Exeception: " + listNotification.getError().toString());
                        }
                        return listNotification;
                    }
                })
                .filter(new Predicate<Notification<List<OrderItem>>>() {
                    @Override
                    public boolean test(Notification<List<OrderItem>> listNotification) throws Exception {
                         return !listNotification.isOnError();
                    }
                }).dematerialize(new Function<Notification<List<OrderItem>>, Notification<List<OrderItem>>>() {
                    @Override
                    public Notification<List<OrderItem>> apply(Notification<List<OrderItem>> listNotification) throws Exception {
                        return listNotification;
                    }
                })
                .map(new Function<List<OrderItem>, List<OrderItem>>() {
                    @Override
                    public List<OrderItem> apply(List<OrderItem> userOrdersItems) throws Exception {
                        List<OrderItem> filteredUserOrderItem = new ArrayList<>();
                        for (OrderItem userOrdersItem: userOrdersItems) {
                            if (userOrdersItem.getIdOrderStatus()==categoryOrderId)
                                filteredUserOrderItem.add(userOrdersItem);
                        }
                        return filteredUserOrderItem;
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS);
    }

    public Observable<List<OrderItem>> getVacantOrdersFromRemote(String authKey, String appId) {
        return orderApiService.getVacantOrders(authKey, appId).map(new Function<OrderResponse, List<OrderItem>>() {
            @Override
            public List<OrderItem> apply(OrderResponse ordersResponse) throws Exception {
                pushAuthToken(ordersResponse.getResponse().getAuthKey());
                return ordersResponse.getResponse().getOrders();
            }
        }).doOnNext(ordersItems -> {
                 safeVacantOrderInBd(ordersItems);
                });
    }
    public Observable<List<OrderItem>> getUserOrdersFromRemote(String authKey, String appId) {
        return orderApiService.getUsersOrders(authKey, appId).map(new Function<OrderResponse, List<OrderItem>>() {
            @Override
            public List<OrderItem> apply(OrderResponse userOrderResponse) throws Exception {
                pushAuthToken(userOrderResponse.getResponse().getAuthKey());
                return userOrderResponse.getResponse().getOrders();
            }
        })
        .doOnNext(userOrdersItems -> safeUserOrderInBd(userOrdersItems));
    }

    public void safeVacantOrderInBd(final List<OrderItem> vacantOrderList) {
        Observable.fromCallable(() -> {
//            ordersDao.removeAll();
            ordersDao.insertAll(vacantOrderList);
            return vacantOrderList;
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(ordersItemList -> Log.d(TAG, "vacant order saved in DB, list count: " + ordersItemList.size()));
    }
    public void safeUserOrderInBd(final List<OrderItem> userOrdersItems) {
        Observable.fromCallable(() -> {
//            ordersDao.removeAll();
            ordersDao.insertAll(userOrdersItems);
                  return userOrdersItems;
    }).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(ordersItemList -> Log.d(TAG, "vacant order saved in DB, list count: " + ordersItemList.size()));
}


    public Observable<List<OrderItem>> getAllVacantOrdersFromDb(String orderName) {
        int cityId =(int)commonSharedPreferences.getIntObject(FILTERED_CITY, int.class);

        String[] filterItems = mContext.getResources().getStringArray(R.array.item_name);
        String parametrs ="(";
        String final_parametrs = "";
        
        if ((Boolean) commonSharedPreferences.getBooleanObject(filterItems[0], Boolean.class))
            parametrs = parametrs + "1,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[1], Boolean.class))
            parametrs = parametrs + "2,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[2], Boolean.class))
            parametrs = parametrs + "3,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[3], Boolean.class))
            parametrs = parametrs + "4,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[4], Boolean.class))
            parametrs = parametrs + "5,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[5], Boolean.class))
            parametrs = parametrs + "6,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[6], Boolean.class))
            parametrs = parametrs + "7,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[7], Boolean.class))
            parametrs = parametrs + "8,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[8], Boolean.class))
            parametrs = parametrs + "9,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[9], Boolean.class))
            parametrs = parametrs + "10,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[10], Boolean.class))
            parametrs = parametrs + "11,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[11], Boolean.class))
            parametrs = parametrs + "12,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[12], Boolean.class))
            parametrs = parametrs + "13,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[13], Boolean.class))
            parametrs = parametrs + "14,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[14], Boolean.class))
            parametrs = parametrs + "15,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[15], Boolean.class))
            parametrs = parametrs + "16,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[16], Boolean.class))
            parametrs = parametrs + "17,";
        if ((Boolean)commonSharedPreferences.getBooleanObject(filterItems[17], Boolean.class))
            parametrs = parametrs + "18,";

        final_parametrs = parametrs.substring(0, parametrs.length() - 1);

        final_parametrs = final_parametrs + ")";
        if (final_parametrs.equals(")")){
            String t = "";
        }

        return ordersDao.getAllVacantOrders(orderName, cityId).filter(new Predicate<List<OrderItem>>() {
            @Override
            public boolean test(List<OrderItem> ordersItemList) throws Exception {
               return ordersItemList.size() != 0;
            }
        }).toObservable();
    }
    //возращаем количество вакантных заказов из кэша
    public Observable<Integer> getVacantFromDbCount(String orderName){
        int cityId =(int)commonSharedPreferences.getIntObject(FILTERED_CITY, int.class);

        return ordersDao.getAllVacantOrders(orderName, cityId).map(new Function<List<OrderItem>, Integer>() {
            @Override
            public Integer apply(List<OrderItem> orderItems) throws Exception {
                return orderItems.size();
            }
        }).toObservable();
    }
    //возращаем количество заказов пользователя из кэша
    public Observable<Integer> getUsersOrdersFromDbCount(int categoryOrderId){
        return ordersDao.getAllUserOrdersStatus(categoryOrderId).map(new Function<List<OrderItem>, Integer>() {
            @Override
            public Integer apply(List<OrderItem> UserOrdersItem) throws Exception {
                return UserOrdersItem.size();
            }
        }).toObservable();
    }
    public Observable<List<OrderItem>> getAllUserOrdersFromDb() {
        return ordersDao.getAllUserOrders().filter(new Predicate<List<OrderItem>>() {
            @Override
            public boolean test(List<OrderItem> ordersItemList) throws Exception {
                return ordersItemList.size() != 0;
            }
        }).toObservable();
    }

    public static int getPageSize() {
        return PAGE_SIZE;
    }

    public void pushAuthToken(String authKey){
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authKey);
    }
}
