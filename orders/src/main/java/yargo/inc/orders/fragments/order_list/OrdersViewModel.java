package yargo.inc.orders.fragments.order_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.orders.fragments.order_list.vacant_orders.paging_orders.OrderDataSourceFactory;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;

    private LiveData<PagedList<VacantOrderItem>> vacantOrders;
    private LiveData<PagedList<VacantOrderItem>> userOrders;

    private LiveData<Boolean> isLoading;
    private CompositeDisposable compositeDisposable;


    private MutableLiveData<String> orderDescription = new MutableLiveData<>();


    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
        compositeDisposable = getCompositeDisposable();
        vacantOrders = createFiltredVacantOrders(orderDescription.getValue());
    }
    public void observSearchText(LifecycleOwner owner, Observer<String> searchString){
        orderDescription.observe(owner, searchString);
    }
    private LiveData<PagedList<VacantOrderItem>> createFiltredVacantOrders(String orderDescription) {
        if (orderDescription==null)
            orderDescription="";
        OrderDataSourceFactory orderDataSourceFactory = new OrderDataSourceFactory(ordersRepository, compositeDisposable, orderDescription);
        isLoading = Transformations.switchMap(orderDataSourceFactory.getDataSourceLiveData(), input -> input.getIsLoading());
        return new LivePagedListBuilder<>(orderDataSourceFactory,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(ordersRepository.getPageSize())
                        .build()).setInitialLoadKey(0)
                .build();

    }
    public void replaceVacantSubscription(LifecycleOwner owner){
        compositeDisposable.clear();
        vacantOrders.removeObservers(owner);
        vacantOrders = createFiltredVacantOrders(orderDescription.getValue());
    }


    //    public void fecthVacantOrders() {
//        addDisposible(ordersRepository.getAllUserOrders()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(ordersItemList -> dataVacanListOrders.postValue(ordersItemList),
//                        throwable -> throwable.printStackTrace()));
//    }
//
//    public void observVacantOrders(LifecycleOwner owner, Observer<List<VacantOrderItem>> observer) {
//        dataVacanListOrders.observe(owner, observer);
//    }


    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    public LiveData<PagedList<VacantOrderItem>> getVacantOrders() {
        return vacantOrders;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription.postValue(orderDescription);
    }
}
