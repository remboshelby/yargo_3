package yargo.inc.orders.fragments.order_list;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.app.AppResponse;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public class OrderListViewModel extends BaseViewModel {
    private MutableLiveData<OrderItem> currentOrder =new MutableLiveData<>();

    private CommonSharedPreferences commonSharedPreferences;
    private OrdersRepository ordersRepository;

    public OrderListViewModel(CommonSharedPreferences commonSharedPreferences, OrdersRepository ordersRepository) {
        this.commonSharedPreferences = commonSharedPreferences;
        this.ordersRepository = ordersRepository;
    }

    public void setOrder(OrderItem userOrdersItem) {
        currentOrder.postValue(userOrdersItem);
    }

    public int getOrderStatus() {
        return currentOrder.getValue()==null ? -1 : currentOrder.getValue().getIdOrderStatus();
    }
    public void setOrderStatusId(int orderStatusId){
        currentOrder.getValue().setIdOrderStatus(orderStatusId);
    }

    public void pushAuthToken(String authKey){
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authKey);
    }
    public User getUser(){
        return (User) commonSharedPreferences.getObject(USER_ABOUT_RESPONSE, User.class);
    }
    public int getOrderId(){
        return currentOrder.getValue().getID();
    }

    public void clearTokenToServer() {
        String authKey = "";
        String fcmToken = (String) commonSharedPreferences.getObject(CommonSharedPreferences.FCM_KEY, String.class);
        String appId = (String) commonSharedPreferences.getObject(CommonSharedPreferences.APP_ID, String.class);
        addDisposible(ordersRepository.pushAppData(authKey, appId, fcmToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<AppResponse>() {
                    @Override
                    public void accept(AppResponse appResponse) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }
}
