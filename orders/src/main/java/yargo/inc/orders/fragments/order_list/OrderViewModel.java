package yargo.inc.orders.fragments.order_list;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.repository.OrdersRepository;

import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public class OrderViewModel extends BaseViewModel {

    public static final int ORDER_IS_VACANT = 1;
    public static final int ORDER_USERS = 0;

    private MutableLiveData<Integer> orderCategoryId = new MutableLiveData<>();
    private CommonSharedPreferences commonSharedPreferences;
    private OrdersRepository ordersRepository;


    public OrderViewModel(CommonSharedPreferences commonSharedPreferences, OrdersRepository ordersRepository) {
        this.commonSharedPreferences = commonSharedPreferences;
        this.ordersRepository = ordersRepository;
        setOrderCategoryId(ORDER_IS_VACANT);
    }
    public void observOrderCategoryId(LifecycleOwner lifecycleOwner, Observer<Integer> observer){
        orderCategoryId.observe(lifecycleOwner, observer);
    }
    public void setOrderCategoryId(Integer orderCategoryId) {
        this.orderCategoryId.postValue(orderCategoryId);
    }
    public void pushAuthToken(String authKey) {
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authKey);
    }
    public void clearTokenToServer() {
        String fcmToken = (String) commonSharedPreferences.getObject(CommonSharedPreferences.FCM_KEY, String.class);
        String appId = (String) commonSharedPreferences.getObject(CommonSharedPreferences.APP_ID, String.class);
        addDisposible(ordersRepository.pushAppData("", appId, fcmToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(appResponse -> {
                }, throwable -> throwable.printStackTrace()));
    }

    public User getUser() {
        return (User) commonSharedPreferences.getObject(USER_ABOUT_RESPONSE, User.class);
    }
}
