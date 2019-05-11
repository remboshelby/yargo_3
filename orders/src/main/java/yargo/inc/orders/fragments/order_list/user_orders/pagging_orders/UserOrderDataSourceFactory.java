package yargo.inc.orders.fragments.order_list.user_orders.pagging_orders;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import io.reactivex.disposables.CompositeDisposable;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

public class UserOrderDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private int categoryOrderId;

    private MutableLiveData<UserOrderDataSource> dataSourceLiveData;

    public UserOrderDataSourceFactory(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, int categoryOrderId) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.categoryOrderId = categoryOrderId;
        this.dataSourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, OrderItem> create() {
        UserOrderDataSource userOrderDataSource = new UserOrderDataSource(ordersRepository, compositeDisposable, categoryOrderId);
        dataSourceLiveData.postValue(userOrderDataSource);
        return userOrderDataSource;
    }

    public MutableLiveData<UserOrderDataSource> getDataSourceLiveData() {
        return dataSourceLiveData;
    }
}
