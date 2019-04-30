package yargo.inc.orders.fragments.order_list.vacant_orders.paging_orders;

import io.reactivex.functions.Consumer;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OrdersDataSource extends PositionalDataSource<VacantOrderItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private String orderDescription;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Integer> recordCount = new MutableLiveData<>();

    private int totalCount;

    public OrdersDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, String orderDescription) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.orderDescription = orderDescription;

        compositeDisposable.add(ordersRepository.getVacantFromDbCount()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> setTotalCount(integer),
                        throwable -> throwable.printStackTrace()));
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<VacantOrderItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrdersForView(params.requestedLoadSize, params.requestedStartPosition, orderDescription)
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
        compositeDisposable.add(ordersRepository.getAllVacantOrdersForView(params.loadSize, params.startPosition, orderDescription).
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
    public MutableLiveData<Integer> getRecordCount() {
        return recordCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        if (totalCount==0)
            isLoading.postValue(false);
    }
}
