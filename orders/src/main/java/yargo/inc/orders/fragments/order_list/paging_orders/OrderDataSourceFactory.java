package yargo.inc.orders.fragments.order_list.paging_orders;

import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;

public class OrderDataSourceFactory extends DataSource.Factory<Integer, OrdersItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private String orderDescription;
    private MutableLiveData<OrdersDataSource> dataSourceLiveData;

    public OrderDataSourceFactory(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, String orderDescription) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.orderDescription = orderDescription;
        this.dataSourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, OrdersItem> create() {
        OrdersDataSource ordersDataSource = new OrdersDataSource(ordersRepository, compositeDisposable, orderDescription);
        dataSourceLiveData.postValue(ordersDataSource);
        return ordersDataSource;
    }
    public MutableLiveData<OrdersDataSource> getDataSourceLiveData(){
        return dataSourceLiveData;
    }
}
