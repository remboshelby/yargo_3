package yargo.inc.orders.fragments.order_list.order_details.custom_view;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailItem;

public class HeaderItemView extends ConstraintLayout {


    @BindView(R2.id.orderDate)
    TextView orderDate;
    @BindView(R2.id.orderViewCount)
    TextView orderViewCount;
    @BindView(R2.id.orderName)
    TextView orderName;
    @BindView(R2.id.orderPrice)
    TextView orderPrice;
    @BindView(R2.id.orderPayType)
    TextView orderPayType;
    @BindView(R2.id.imageView)
    ImageView imageView;

    public HeaderItemView(Context context) {
        super(context);
        init(context);
    }

    public HeaderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.header_item_view, this);
        ButterKnife.bind(this);
    }

    public void bind(OrderDetailItem item) {
        OrderDetailResponse orderDetailResponse = item.getOrderDetailResponse();
        OrdersItem ordersItem = orderDetailResponse.getResponse().getOrders().get(0);

        orderDate.setText(dateCreator(ordersItem.getStartworking()) + " - " + dateCreator(ordersItem.getDeadline()));

        orderViewCount.setText(String.valueOf(ordersItem.getWatched()));

        orderName.setText(ordersItem.getDescription());
        orderPrice.setText(String.valueOf(ordersItem.getPrice()) + Html.fromHtml(" &#x20bd"));
        orderPayType.setText(ordersItem.getIdPaymentMethod() == 1 ? "Наличный расчет" : "Безналичный расчет");
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
