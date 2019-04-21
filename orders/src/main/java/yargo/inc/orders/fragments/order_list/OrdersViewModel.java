package yargo.inc.orders.fragments.order_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import yargo.inc.orders.fragments.order_list.paging_orders.OrderDataSourceFactory;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;
    private LiveData<PagedList<OrdersItem>> orders;
    private LiveData<Boolean> isLoading;
    private CompositeDisposable compositeDisposable;


    private MutableLiveData<String> orderDescription = new MutableLiveData<>();

    private MutableLiveData<List<OrdersItem>> dataVacanListOrders = new MutableLiveData<>();

    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
        compositeDisposable = getCompositeDisposable();
        orders = createFiltredOrders(orderDescription.getValue());
    }
    public void observSearchText(LifecycleOwner owner, Observer<String> searchString){
        orderDescription.observe(owner, searchString);
    }
    private LiveData<PagedList<OrdersItem>> createFiltredOrders(String orderDescription) {
        if (orderDescription==null)
            orderDescription="";
        OrderDataSourceFactory orderDataSourceFactory = new OrderDataSourceFactory(ordersRepository, compositeDisposable, orderDescription);
        isLoading = Transformations.switchMap(orderDataSourceFactory.getDataSourceLiveData(), input -> input.getIsLoading());
        return new LivePagedListBuilder<>(orderDataSourceFactory,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(ordersRepository.getPageSize())
                        .build()).setInitialLoadKey(0)
                .build();

    }
    public void replaceSubscription(LifecycleOwner owner){
        compositeDisposable.clear();
        orders.removeObservers(owner);
        orders = createFiltredOrders(orderDescription.getValue());
    }
    //    public void fecthVacantOrders() {
//        addDisposible(ordersRepository.getAllVacantOrders()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(ordersItemList -> dataVacanListOrders.postValue(ordersItemList),
//                        throwable -> throwable.printStackTrace()));
//    }
//
//    public void observVacantOrders(LifecycleOwner owner, Observer<List<OrdersItem>> observer) {
//        dataVacanListOrders.observe(owner, observer);
//    }


    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    public LiveData<PagedList<OrdersItem>> getOrders() {
        return orders;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription.postValue(orderDescription);
    }
}
