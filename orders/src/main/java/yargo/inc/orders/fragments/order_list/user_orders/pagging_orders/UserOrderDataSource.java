package yargo.inc.orders.fragments.order_list.user_orders.pagging_orders;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.network.repository.OrdersRepository;

public class UserOrderDataSource extends PositionalDataSource<OrderItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private int categoryOrderId;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Integer> totalCount= new MutableLiveData<>();

    public UserOrderDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, int categoryOrderId) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.categoryOrderId = categoryOrderId;

        compositeDisposable.add(ordersRepository.getUsersOrdersFromDbCount(categoryOrderId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(totalCount1 -> UserOrderDataSource.this.setTotalCount(totalCount1),
                        Throwable::printStackTrace));
    }

    public MutableLiveData<Integer> getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount.postValue(totalCount);
        if (totalCount == 0)
            isLoading.postValue(false);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<OrderItem> callback) {
        isLoading.postValue(true);

        compositeDisposable.add(ordersRepository.getAllUserOrdersForView(params.requestedLoadSize, params.requestedStartPosition, categoryOrderId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<OrderItem>>() {
                    @Override
                    public void accept(List<OrderItem> userOrdersItems) throws Exception {
                        isLoading.postValue(false);
                        callback.onResult(userOrdersItems, params.requestedStartPosition, getTotalCount().getValue());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<OrderItem> callback) {
        isLoading.postValue(true);

        compositeDisposable.add(ordersRepository.getAllUserOrdersForView(params.loadSize, params.startPosition, categoryOrderId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<OrderItem>>() {
                    @Override
                    public void accept(List<OrderItem> userOrdersItems) throws Exception {
                        callback.onResult(userOrdersItems);
                        isLoading.postValue(false);
                    }
                }, throwable -> throwable.printStackTrace()));


    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

}
