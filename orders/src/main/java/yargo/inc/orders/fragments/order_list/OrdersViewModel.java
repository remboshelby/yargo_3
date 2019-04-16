package yargo.inc.orders.fragments.order_list;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.orders.fragments.order_list.paging_orders.OrderDataSourceFactory;
import yargo.inc.orders.fragments.order_list.paging_orders.OrdersDataSource;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;
    private LiveData<PagedList<OrdersItem>> orders;
    private LiveData<Boolean> isLoading;

    private MutableLiveData<List<OrdersItem>> dataVacanListOrders = new MutableLiveData<>();
    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;

        OrderDataSourceFactory orderDataSourceFactory = new OrderDataSourceFactory(ordersRepository, getCompositeDisposable());

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(ordersRepository.getPageSize())
                .build();

        orders = new LivePagedListBuilder<>(orderDataSourceFactory, config).build();

        isLoading = Transformations.switchMap(orderDataSourceFactory.getDataSourceLiveData(), new Function<OrdersDataSource, LiveData<Boolean>>() {
            @Override
            public LiveData<Boolean> apply(OrdersDataSource input) {
                return input.getIsLoading();
            }
        });
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
}
