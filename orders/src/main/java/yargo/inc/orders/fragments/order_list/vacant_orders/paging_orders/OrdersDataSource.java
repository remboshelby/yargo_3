package yargo.inc.orders.fragments.order_list.vacant_orders.paging_orders;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

public class OrdersDataSource extends PositionalDataSource<OrderItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private String orderName;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Integer> totalCount = new MutableLiveData<>();

    public OrdersDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, String orderName) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.orderName = orderName;

        compositeDisposable.add(ordersRepository.getVacantFromDbCount(orderName)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                               @Override
                               public void accept(Integer integer) throws Exception {
                                   OrdersDataSource.this.setTotalCount(integer);
                                   Log.d(OrdersDataSource.class.getSimpleName(), " count " + integer);
                               }
                           },
                        throwable -> throwable.printStackTrace()));
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<OrderItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrdersForView(params.requestedLoadSize, params.requestedStartPosition, orderName)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItems -> {
                    callback.onResult(ordersItems, params.requestedStartPosition, getTotalCount().getValue());
                    isLoading.postValue(false);
                }, throwable -> throwable.printStackTrace()));
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<OrderItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrdersForView(params.loadSize, params.startPosition, orderName).
                observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItemList -> {
                    callback.onResult(ordersItemList);
                    isLoading.postValue(false);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Integer> getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount.postValue(totalCount);
        if (totalCount == 0)
            isLoading.postValue(false);
    }
}
