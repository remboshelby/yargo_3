package yargo.inc.orders.fragments.order_list.order_commission;

import android.text.format.DateUtils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.repository.CommissionRepository;
import yargo.inc.common.network.repository.OrderActionRepository;

public class CommissionViewModel extends BaseViewModel {
    private OrderActionRepository orderActionRepository;
    private CommissionRepository commissionRepository;

    private MutableLiveData<OrderDetailResponse> orderDetailData = new MutableLiveData<>();

    public CommissionViewModel(CommissionRepository commissionRepository,OrderActionRepository orderActionRepository) {
        this.commissionRepository = commissionRepository;
        this.orderActionRepository = orderActionRepository;
    }
    public void getOrderDetail(int idOrder) {
        addDisposible(orderActionRepository.getOrderDetail(idOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(value -> orderDetailData.postValue(value),
                        throwable -> throwable.printStackTrace()));
    }
    public void observOrderDetailData(LifecycleOwner owner, Observer<OrderDetailResponse> observer) {
        orderDetailData.observe(owner, observer);
    }
    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }
    public static boolean isTomorrow(Date d) {
        return DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS);
    }
    public static String getMonth(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String monthName = new SimpleDateFormat("dd MMMM").format(cal.getTime());
        return monthName;
    }
    public String dateCreator(String date_){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.ENGLISH);
        format.setLenient(false);
        SimpleDateFormat output_format_time = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = format.parse(date_);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date_1 =null;
        if (DateUtils.isToday(date.getTime())){
            date_1 = "Сегодня, " + output_format_time.format(date);
        }else if(isYesterday(date)){
            date_1 = "Вчера, " + output_format_time.format(date);
        }else if(isTomorrow(date)){
            date_1 = "Завтра, " + output_format_time.format(date);
        }else if (date_1==null){
            try {
                String t = getMonth(date);
                date_1 = t + ", " + output_format_time.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date_1;
        }
        return date_1;
    }
}
