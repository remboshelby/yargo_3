package yargo.inc.orders.fragments.order_list;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.common.utils.AutoResizeTextView;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;

public class OrderItemView extends ConstraintLayout {


    @BindView(R2.id.imgOrderType)
    ImageView imgOrderType;
    @BindView(R2.id.tvOrderAbout)
    TextView tvOrderAbout;
    @BindView(R2.id.tvOrderData)
    TextView tvOrderData;
    @BindView(R2.id.tvOrderPrice)
    AutoResizeTextView tvOrderPrice;
    @BindView(R2.id.imgPayType)
    ImageView imgPayType;
    @BindView(R2.id.pbItemIsLoading)
    ProgressBar pbItemIsLoading;

    public OrderItemView(Context context) {
        super(context);
        init(context);
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.order_item, this);
        ButterKnife.bind(this);
    }

    public void bind(VacantOrderItem vacantOrderItem) {
        if (vacantOrderItem != null) {
            tvOrderAbout.setVisibility(View.VISIBLE);
            pbItemIsLoading.setVisibility(View.GONE);

            tvOrderAbout.setText(vacantOrderItem.getAddress());
            imgOrderType.setImageResource(getIconByOrderType(vacantOrderItem.getIdSpecialization()));
            tvOrderAbout.setText(vacantOrderItem.getDescription());
            tvOrderData.setText(dateCreator(vacantOrderItem.getStartworking()) + " - " + dateCreator(vacantOrderItem.getDeadline()));
            tvOrderPrice.setText(String.valueOf(vacantOrderItem.getPrice()) + Html.fromHtml(" &#x20bd"));
            imgPayType.setImageResource(vacantOrderItem.getIdPaymentMethod() == 1 ? R.drawable.ic_credit_card_yellow_24dp : android.R.color.transparent);
        } else {
            tvOrderAbout.setVisibility(View.GONE);
            pbItemIsLoading.setVisibility(View.VISIBLE);
        }
    }
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
