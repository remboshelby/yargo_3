package yargo.inc.orders.fragments.order_list.user_orders.pagging_orders;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

public class UserOrderDataSource extends PositionalDataSource<UserOrdersItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private int categoryOrderId;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private int totalCount;

    public UserOrderDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable, int categoryOrderId) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
        this.categoryOrderId = categoryOrderId;

        compositeDisposable.add(ordersRepository.getAllUserOrders(categoryOrderId)
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .subscribe(userOrdersItems -> setTotalCount(userOrdersItems.size())
                , throwable -> throwable.printStackTrace()));
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<UserOrdersItem> callback) {
        isLoading.postValue(true);

        compositeDisposable.add(ordersRepository.getAllUserOrdersForView(params.requestedLoadSize, params.requestedStartPosition, categoryOrderId)
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<UserOrdersItem>>() {
            @Override
            public void accept(List<UserOrdersItem> userOrdersItems) throws Exception {
                isLoading.postValue(false);
                callback.onResult(userOrdersItems, params.requestedStartPosition, getTotalCount());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<UserOrdersItem> callback) {
        isLoading.postValue(true);

        compositeDisposable.add(ordersRepository.getAllUserOrdersForView(params.loadSize, params.startPosition, categoryOrderId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<UserOrdersItem>>() {
                    @Override
                    public void accept(List<UserOrdersItem> userOrdersItems) throws Exception {
                        callback.onResult(userOrdersItems);
                        isLoading.postValue(false);
                    }
                }, throwable -> throwable.printStackTrace()));


    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
