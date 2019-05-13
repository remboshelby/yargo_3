package yargo.inc.orders.fragments.order_list.vacant_orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.reactivex.disposables.CompositeDisposable;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.orders.fragments.order_list.vacant_orders.paging_orders.OrderDataSourceFactory;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

public class VacantOrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;

    private LiveData<PagedList<OrderItem>> vacantOrders;

    private LiveData<Boolean> isLoading;
    private LiveData<Integer> ordersCount;

    private CompositeDisposable compositeDisposable;

    private MutableLiveData<String> orderDescription = new MutableLiveData<>();
    private MutableLiveData<Integer> vacantOrdersCount = new MutableLiveData<>();

    public VacantOrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
        compositeDisposable = getCompositeDisposable();
        vacantOrders = createFiltredVacantOrders(orderDescription.getValue());

    }

    public void observSearchText(LifecycleOwner owner, Observer<String> searchString){
        orderDescription.observe(owner, searchString);
    }
    public void observVacantOrderCount(LifecycleOwner owner, Observer<Integer> userOrderCountValue){
        vacantOrdersCount.observe(owner, userOrderCountValue);
    }
    private LiveData<PagedList<OrderItem>> createFiltredVacantOrders(String orderName) {
        if (orderName ==null)
            orderName ="";
        OrderDataSourceFactory orderDataSourceFactory = new OrderDataSourceFactory(ordersRepository, compositeDisposable, orderName);
        isLoading = Transformations.switchMap(orderDataSourceFactory.getDataSourceLiveData(), input -> input.getIsLoading());
        ordersCount = Transformations.switchMap(orderDataSourceFactory.getDataSourceLiveData(), input -> input.getRecordCount());
        return new LivePagedListBuilder<>(orderDataSourceFactory,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(ordersRepository.getPageSize())
                        .build()).setInitialLoadKey(0)
                .build();

    }
    public void replaceVacantSubscription(LifecycleOwner owner){
        compositeDisposable.clear();
        vacantOrders.removeObservers(owner);
        vacantOrders = createFiltredVacantOrders(orderDescription.getValue());
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    public LiveData<PagedList<OrderItem>> getVacantOrders() {
        return vacantOrders;
    }


    public void setOrderDescription(String orderDescription) {
        this.orderDescription.postValue(orderDescription);
    }

}
