package yargo.inc.orders.fragments.order_list;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.paging_orders.OrderDataSourceFactory;
import yargo.inc.orders.fragments.order_list.paging_orders.OrdersDataSource;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;
    private LiveData<PagedList<OrdersItem>> orders;
    private LiveData<Boolean> isLoading;

    private MutableLiveData<List<OrdersItem>> dataVacanListOrders = new MutableLiveData<>();

    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;

        OrderDataSourceFactory orderDataSourceFactory = new OrderDataSourceFactory(ordersRepository, getCompositeDisposable());

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(ordersRepository.getPageSize())
                .build();

        orders = new LivePagedListBuilder<>(orderDataSourceFactory, config).build();

        isLoading = Transformations.switchMap(orderDataSourceFactory.getDataSourceLiveData(), new Function<OrdersDataSource, LiveData<Boolean>>() {
            @Override
            public LiveData<Boolean> apply(OrdersDataSource input) {
                return input.getIsLoading();
            }
        });
    }
//    public void fecthVacantOrders() {
//        addDisposible(ordersRepository.getAllVacantOrders()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(ordersItemList -> dataVacanListOrders.postValue(ordersItemList),
//                        throwable -> throwable.printStackTrace()));
//    }
//
//    public void observVacantOrders(LifecycleOwner owner, Observer<List<OrdersItem>> observer) {
//        dataVacanListOrders.observe(owner, observer);
//    }

    public int getIconByOrderType(int orderType) {
        switch (orderType) {
            case 1:
                return R.drawable.icon_santech; //Сантехнические работы
            case 2:
                return R.drawable.icon_electro;//Электромонтажные работы
            case 3:
                return R.drawable.icon_svarka;//Сварочный работы
            case 4:
                return R.drawable.icon_paint;//Отделочные работы
            case 5:
                return R.drawable.icon_dors; // Двери
            case 6:
                return R.drawable.icon_lock;//Замки
            case 7:
                return R.drawable.icon_mebel;//Мебель
            case 8:
                return R.drawable.icon_repair;//Мелкий ремонт
            case 9:
                return R.drawable.icon_window;//Окна
            case 10:
                return R.drawable.icon_gradusnik;//Отопление
            case 11:
                return R.drawable.icon_ventel;//Вентеляция
            case 12:
                return R.drawable.icon_drel; //слесарные работы
            case 13:
                return R.drawable.icon_pila;//столярные работы
            case 14:
                return R.drawable.icon_tocar; //Токарные работы
            case 15:
                return R.drawable.icon_wall;//Общестроительные работы
            case 16:
                return R.drawable.icon_it;
            case 17:
                return R.drawable.icon_clean;
            case 18:
                return R.drawable.icon_bit;
            default:
                return R.drawable.icon_santech;
        }
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    public LiveData<PagedList<OrdersItem>> getOrders() {
        return orders;
    }
}
