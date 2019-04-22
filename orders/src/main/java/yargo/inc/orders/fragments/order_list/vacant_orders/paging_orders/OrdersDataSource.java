package yargo.inc.orders.fragments.order_list.vacant_orders.paging_orders;

import io.reactivex.functions.Consumer;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OrdersDataSource extends PositionalDataSource<VacantOrderItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private String orderDescription;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private int totalCount;

    public OrdersDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, String orderDescription) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.orderDescription = orderDescription;

        compositeDisposable.add(ordersRepository.getAllVacantOrders(orderDescription)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItems -> OrdersDataSource.this.setTotalCount(ordersItems.size()),
                        throwable -> throwable.printStackTrace()));
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<VacantOrderItem> callback) {
        isLoading.postValue(true);

        compositeDisposable.add(ordersRepository.getAllUserOrders()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<UserOrdersItem>>() {
                    @Override
                    public void accept(List<UserOrdersItem> userOrdersItem_s) throws Exception {
                        isLoading.postValue(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));

        compositeDisposable.add(ordersRepository.getAllVacantOrdersFotView(params.requestedLoadSize, params.requestedStartPosition, orderDescription)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItems -> {
                    callback.onResult(ordersItems, params.requestedStartPosition, OrdersDataSource.this.getTotalCount());
                    isLoading.postValue(false);
                }, throwable -> throwable.printStackTrace()));
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<VacantOrderItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrdersFotView(params.loadSize, params.startPosition, orderDescription).
                observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItemList -> {
                    callback.onResult(ordersItemList);
                    isLoading.postValue(false);
                }, throwable -> throwable.printStackTrace()));
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
