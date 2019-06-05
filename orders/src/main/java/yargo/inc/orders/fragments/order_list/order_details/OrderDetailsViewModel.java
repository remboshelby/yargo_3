package yargo.inc.orders.fragments.order_list.order_details;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order_action.ActionResponse;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;

public class OrderDetailsViewModel extends BaseViewModel {
    private MutableLiveData<OrderDetailResponse> orderDetailData = new MutableLiveData<>();
    private MutableLiveData<Integer> orderChangeResult = new MutableLiveData<>();

    @Inject
    protected OrderActionRepository orderActionRepository;

    public static final int ORDER_IS_VACANT = 1;
    public static final int ORDER_IS_ASSIGNED = 3;
    public static final int ORDER_IS_INWORK = 8;
    public static final int ORDER_WAIT_PAY = 7;
    public static final int ORDER_IS_DONE = 4;
    public static final int ORDER_CLIENT_CANCEL = 5;

    public static final int ORDER_ACTION_SOMETHING_WRONG = -1;

    public static final int ORDER_GET_SUCCESS = 0;
    public static final int ORDER_GET_FAIL = 1;
    public static final int ORDER_GET_ISBUSY = 2;
    public static final int ORDER_GET_UFULL = 3;

    public static final int ORDER_START_SUCCESS = 4;
    public static final int ORDER_STAR_ALREADY_SET = 5;

    public static final int ORDER_FINISED_SUCCESS = 6;
    public static final int ORDER_FINISHED_ALREADY_SET = 7;

    private static final String ERROR_ORDER_IS_BUSY_ORDER_STATUS_ASSIGNED = "заказ не доступен : находится в работе у другого пользователя";
    private static final String ERROR_ORDER_UFULL = "ANOTHER_ORDER_ALREADY_TAKEN";
    private static final String ERROR_START_ALREADY_SET = "STATUS_ALREADY_SET";
    private static final String ERROR_ACCOMPLISHED_ALREADY_SET = "указанный заказ недоступен: некорректный статус указанного заказа";

    public OrderDetailsViewModel() {
        OrderListsFragment.getOrdersComponent().inject(this);
    }

    public void observOrderDetailData(LifecycleOwner owner, Observer<OrderDetailResponse> observer) {
        orderDetailData.observe(owner, observer);
    }

    public void observOrderChangeResult(LifecycleOwner owner, Observer<Integer> observer) {
        orderChangeResult.observe(owner, observer);
    }

    public void getOrderDetail(int idOrder) {
        addDisposible(orderActionRepository.getOrderDetail(idOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(value -> orderDetailData.postValue(value),
                        throwable -> throwable.printStackTrace()));
    }

    public void actionGetOrder() {
        addDisposible(orderActionRepository.actionGetOrder(orderDetailData.getValue().getResponse().getOrders().get(0).getID())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(actionResponse -> {
                    ActionResponse response = actionResponse.getResponse();
                    if (response.getType().equals("take_order")) {
                        orderChangeResult.postValue(ORDER_GET_SUCCESS);
                    } else if (response.getType().equals("error")) {
                        if (response.getMessage().equals(ERROR_ORDER_IS_BUSY_ORDER_STATUS_ASSIGNED)) {
                            orderChangeResult.postValue(ORDER_GET_ISBUSY);
                        } else if (response.getErrorCode().equals(ERROR_ORDER_UFULL)) {
                            orderChangeResult.postValue(ORDER_GET_UFULL);
                        }
                    }
                }));
    }

    public void actionStartOrder() {
        addDisposible(orderActionRepository.actionStartOrder(orderDetailData.getValue().getResponse().getOrders().get(0).getID())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(actionResponse -> {
                    ActionResponse response = actionResponse.getResponse();
                    if (response.getType().equals("start_order")){
                        orderChangeResult.postValue(ORDER_START_SUCCESS);
                    }
                    else if (response.getType().equals("error")){
                        if (response.getErrorCode().equals(ERROR_START_ALREADY_SET)){
                            orderChangeResult.postValue(ORDER_STAR_ALREADY_SET);
                        }else if (response.getErrorCode().equals(ERROR_ORDER_UFULL)) {
                            orderChangeResult.postValue(ORDER_GET_UFULL);
                        }
                    }
                }));
    }
    public void actionAccomplishedOrder(){
        addDisposible(orderActionRepository.actionAccomplishedOrder(orderDetailData.getValue().getResponse().getOrders().get(0).getID())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(actionResponse -> {
                    ActionResponse response = actionResponse.getResponse();
                    if (response.getType().equals("accomplished_order")){
                        orderChangeResult.postValue(ORDER_FINISED_SUCCESS);
                    }
                    else if (response.getType().equals("error")){
                        if (response.getMessage().equals(ERROR_ACCOMPLISHED_ALREADY_SET)){
                            orderChangeResult.postValue(ORDER_FINISHED_ALREADY_SET);
                        }else if (response.getErrorCode().equals(ERROR_ORDER_UFULL)) {
                            orderChangeResult.postValue(ORDER_GET_UFULL);
                        }

                    }
                }));
    }
    public int getOrderStatusId() {
        return orderDetailData.getValue().getResponse().getOrders().get(0).getIdOrderStatus();
    }

}
