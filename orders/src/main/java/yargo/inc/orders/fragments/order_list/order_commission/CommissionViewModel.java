package yargo.inc.orders.fragments.order_list.order_commission;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.schedulers.Schedulers;
import ru.yandex.money.android.sdk.PaymentMethodType;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.interactors.CommissionInteractor;
import yargo.inc.common.interactors.DateInteractor;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.orders.fragments.order_list.order_commission.entity.PayEntity;
import yargo.inc.orders.yandex_utils.Settings;

public class CommissionViewModel extends BaseViewModel {

    private static final int DOORS_SPECIAL = 2;
    private static final double DOORS_COMMISSION = 0.1;
    private static final double OTHER_COMMISSION = 0.2;

    private MutableLiveData<PayEntity> payRequisites = new MutableLiveData<>();
    private MutableLiveData<OrderDetailResponse> orderDetailData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPayed = new MutableLiveData<>();
    private DateInteractor dateInteractor;
    private CommissionInteractor commissionInteractor;


    private MutableLiveData<Float> finalCoast = new MutableLiveData<>();
    private MutableLiveData<Double> comissionPrice = new MutableLiveData<>();

    public CommissionViewModel(DateInteractor dateInteractor,CommissionInteractor commissionInteractor) {
        this.dateInteractor = dateInteractor;
        this.commissionInteractor = commissionInteractor;
    }

    public void getOrderDetail(int idOrder) {
        addDisposible(commissionInteractor.getOrderDetail(idOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(value -> orderDetailData.postValue(value),
                        throwable -> throwable.printStackTrace()));
    }

    public void observOrderDetailData(LifecycleOwner owner, Observer<OrderDetailResponse> observer) {
        orderDetailData.observe(owner, observer);
    }

    public void observComissionPrice(LifecycleOwner owner, Observer<Double> observer){
        comissionPrice.observe(owner, observer);
    }

    public void observIsPayed(LifecycleOwner owner, Observer<Boolean> observer) {
        isPayed.observe(owner, observer);
    }

    public void observPayData(LifecycleOwner owner, Observer<PayEntity> observer) {
        payRequisites.observe(owner, observer);
    }

    @NonNull
    public static Set<PaymentMethodType> getPaymentMethodTypes(Settings settings) {
        final Set<PaymentMethodType> paymentMethodTypes = new HashSet<>();

        if (settings.isYandexMoneyAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.YANDEX_MONEY);
        }

        if (settings.isNewCardAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.BANK_CARD);
        }

        if (settings.isSberbankOnlineAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.SBERBANK);
        }

        if (settings.isGooglePayAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.GOOGLE_PAY);
        }
        return paymentMethodTypes;
    }

    public void setPayRequisites(PayEntity payEntity) {
        this.payRequisites.postValue(payEntity);
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed.postValue(isPayed);
    }

    public String dateCreator(String startworking) {
        return dateInteractor.dateCreator(startworking);
    }

    public void setFinalCoast(Float finalCoast) {
        this.finalCoast.postValue(finalCoast);
        calculateComissionPrice(finalCoast);
    }

    private void calculateComissionPrice(Float finalCoast) {
        if (orderDetailData.getValue().getResponse().getOrders().get(0).getIdSpecialization() == DOORS_SPECIAL) {
            comissionPrice.postValue(DOORS_COMMISSION * finalCoast);
        } else {
            comissionPrice.postValue(OTHER_COMMISSION * finalCoast);
        }
    }
    public String getAuthToken(){
       return commissionInteractor.getAuthToken();
    }
}
