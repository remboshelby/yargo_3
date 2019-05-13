package yargo.inc.orders.fragments.order_list.vacant_orders.paging_orders;

import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;

public class OrderDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private String orderName;
    private MutableLiveData<OrdersDataSource> dataSourceLiveData;

    public OrderDataSourceFactory(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, String orderName) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.orderName = orderName;
        this.dataSourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, OrderItem> create() {
        OrdersDataSource ordersDataSource = new OrdersDataSource(ordersRepository, compositeDisposable, orderName);
        dataSourceLiveData.postValue(ordersDataSource);
        return ordersDataSource;
    }
    public MutableLiveData<OrdersDataSource> getDataSourceLiveData(){
        return dataSourceLiveData;
    }
}
