package yargo.inc.orders.fragments.order_list.paging_orders;

import java.util.List;

import io.reactivex.functions.Consumer;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OrdersDataSource extends PositionalDataSource<OrdersItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private int totalCount;

    public OrdersDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;

        compositeDisposable.add(ordersRepository.getAllVacantOrders()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItems -> OrdersDataSource.this.setTotalCount(ordersItems.size()), throwable -> throwable.printStackTrace()));
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<OrdersItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrdersFotView(params.requestedLoadSize, params.requestedStartPosition)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItems -> {
                    callback.onResult(ordersItems, params.requestedStartPosition, OrdersDataSource.this.getTotalCount());
                    isLoading.postValue(false);
                }, throwable -> throwable.printStackTrace()));
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<OrdersItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrdersFotView(params.loadSize, params.startPosition).
                observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(ordersItemList -> {
                    callback.onResult(ordersItemList);
                    isLoading.postValue(false);
                }));
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
