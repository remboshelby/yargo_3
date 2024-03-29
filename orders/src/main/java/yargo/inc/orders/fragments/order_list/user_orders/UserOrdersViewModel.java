package yargo.inc.orders.fragments.order_list.user_orders;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.interactors.UserOrderInteractor;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.orders.fragments.order_list.user_orders.pagging_orders.UserOrderDataSourceFactory;

import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public class UserOrdersViewModel extends BaseViewModel {

    public static final int ORDER_IS_VACANT = 1;
    public static final int ORDER_IS_ASSIGNED = 3;
    public static final int ORDER_IS_INWORK = 8;
    public static final int ORDER_WAIT_PAY = 7;
    public static final int ORDER_IS_DONE = 4;
    public static final int ORDER_CLIENT_CANCEL = 5;
    public static final int ORDER_CLIENT_CANCEL__ = 5;


    private UserOrderInteractor interactor;

    private LiveData<PagedList<OrderItem>> userOrders;

    private LiveData<Boolean> isLoading;
    private LiveData<Integer> userOrderCount;
    private CompositeDisposable compositeDisposable;

    private int startPositon = 0;

    private MutableLiveData<Integer> orderCategoryId = new MutableLiveData<>();
    private MutableLiveData<OrderItem> currentOrder = new MutableLiveData<>();


    public UserOrdersViewModel(UserOrderInteractor interactor) {
        this.interactor = interactor;
        compositeDisposable = getCompositeDisposable();
        orderCategoryId.setValue(0);
    }

    public void observUserOrderCount(LifecycleOwner owner, Observer<Integer> userOrderCountValue){
        userOrderCount.observe(owner, userOrderCountValue);
    }
    public void observOrderCategoryId(LifecycleOwner owner, Observer<Integer> valOrderCategoryId){
        orderCategoryId.observe(owner, valOrderCategoryId);
    }
    public void replaceUserOrdersSubscription(LifecycleOwner owner){
        compositeDisposable.clear();
        if (userOrders!=null)  userOrders.removeObservers(owner);
        userOrders = createFiltredUsersOrders(orderCategoryId.getValue());
    }
    private LiveData<PagedList<OrderItem>> createFiltredUsersOrders(int categoryOrderId) {
        UserOrderDataSourceFactory userOrderDataSourceFactory = new UserOrderDataSourceFactory(interactor.getOrdersRepository(), compositeDisposable, categoryOrderId);
        isLoading = Transformations.switchMap(userOrderDataSourceFactory.getDataSourceLiveData(), input -> input.getIsLoading());
        userOrderCount = Transformations.switchMap(userOrderDataSourceFactory.getDataSourceLiveData(), input -> input.getTotalCount());

        return new LivePagedListBuilder<>(userOrderDataSourceFactory,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(interactor.getPageSize())
                        .build()).setInitialLoadKey(0)
                .build();
    }
    public LiveData<PagedList<OrderItem>> getUserOrders() {
        return userOrders;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public int getStartPositon() {
        return startPositon;
    }
    public void setStartPositon(int startPositon) {
        this.startPositon = startPositon;

    }

    public void clearTokenToServer() {
        String fcmToken = interactor.getFcmToken();
        String appId = interactor.getAppId();
        addDisposible(interactor.pushAppData("", appId, fcmToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(appResponse -> {
                }, throwable -> throwable.printStackTrace()));
    }

    public void setOrder(OrderItem userOrdersItem) {
        currentOrder.postValue(userOrdersItem);
    }

    public void pushAuthToken(String authKey) {
        interactor.setAuthToken(authKey);
    }

    public User getUser() {
        return interactor.getUserInfo();
    }

    public int getOrderId() {
        return currentOrder.getValue().getID();
    }

    public void setOrderCategoryId(int valOrderCategoryId) {
        orderCategoryId.postValue(valOrderCategoryId);
    }
}
