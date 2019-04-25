package yargo.inc.orders.fragments.order_list.user_orders;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.reactivex.disposables.CompositeDisposable;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.orders.fragments.order_list.user_orders.pagging_orders.UserOrderDataSourceFactory;

public class UserOrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;

    private LiveData<PagedList<UserOrdersItem>> userOrders;

    private LiveData<Boolean> isLoading;

    private CompositeDisposable compositeDisposable;

    private MutableLiveData<Integer> orderCategoryId = new MutableLiveData<>();
    private MutableLiveData<Integer> userOrdersCount = new MutableLiveData<>();

    public UserOrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
        compositeDisposable = getCompositeDisposable();
        orderCategoryId.setValue(2);
        userOrders = createFiltredUsersOrders(orderCategoryId.getValue());
    }
    public void observUserOrderCount(LifecycleOwner owner, Observer<Integer> userOrderCountValue){
        userOrdersCount.observe(owner, userOrderCountValue);
    }
    public void observOrderCategoryId(LifecycleOwner owner, Observer<Integer> valOrderCategoryId){
        orderCategoryId.observe(owner, valOrderCategoryId);
    }
    public void replaceUserOrdersSubscription(LifecycleOwner owner){
        compositeDisposable.clear();
        userOrders.removeObservers(owner);
        userOrders = createFiltredUsersOrders(orderCategoryId.getValue());
    }
    private LiveData<PagedList<UserOrdersItem>> createFiltredUsersOrders(int categoryOrderId) {
        UserOrderDataSourceFactory userOrderDataSourceFactory = new UserOrderDataSourceFactory(ordersRepository, compositeDisposable, categoryOrderId);
        isLoading = Transformations.switchMap(userOrderDataSourceFactory.getDataSourceLiveData(), input -> input.getIsLoading());
        return new LivePagedListBuilder<>(userOrderDataSourceFactory,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(ordersRepository.getPageSize())
                        .build()).setInitialLoadKey(0)
                .build();
    }
    public LiveData<PagedList<UserOrdersItem>> getUserOrders() {
        return userOrders;
    }

    public void setOrderCategoryId(int valOrderCategoryId) {
        orderCategoryId.setValue(valOrderCategoryId);
    }
    public void setUserOrdersCount(int userOrdersCountValue) {
        userOrdersCount.postValue(userOrdersCountValue);
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public int getOrderCategoryId() {
        return orderCategoryId.getValue();
    }
}
