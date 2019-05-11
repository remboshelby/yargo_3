package yargo.inc.orders.fragments.order_list;

import androidx.lifecycle.MutableLiveData;

import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.order_list.OrderItem;

import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public class OrderListViewModel extends BaseViewModel {
    private MutableLiveData<OrderItem> currentOrder =new MutableLiveData<>();

    private CommonSharedPreferences commonSharedPreferences;

    public OrderListViewModel(CommonSharedPreferences commonSharedPreferences) {
        this.commonSharedPreferences = commonSharedPreferences;
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
}
