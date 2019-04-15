package yargo.inc.orders.fragments.order_list;

import androidx.lifecycle.LiveData;
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

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;
    private LiveData<PagedList<OrdersItem>> orders;

    private MutableLiveData<List<OrdersItem>> dataVacanListOrders = new MutableLiveData<>();

    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;

        OrderDataSourceFactory orderDataSourceFactory = new OrderDataSourceFactory(ordersRepository, getCompositeDisposable());

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ordersRepository.getPageSize())
                .build();

        orders = new LivePagedListBuilder<>(orderDataSourceFactory, config).build();
    }

    public void fecthVacantOrders() {
        addDisposible(ordersRepository.getAllVacantOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ordersItemList -> dataVacanListOrders.postValue(ordersItemList),
                        throwable -> throwable.printStackTrace()));
    }

    public void observVacantOrders(LifecycleOwner owner, Observer<List<OrdersItem>> observer) {
        dataVacanListOrders.observe(owner, observer);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    public LiveData<PagedList<OrdersItem>> getOrders() {
        return orders;
    }
}
