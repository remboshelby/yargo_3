package yargo.inc.orders.fragments.order_list.order_detailse.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.order_detailse.utils.OrderDetailItem;

public class OrderPayTypeView extends ConstraintLayout {

    @BindView(R2.id.tvPayType)
    TextView tvPayType;
    @BindView(R2.id.tvPhoneNumber)
    TextView tvPhoneNumber;

    public OrderPayTypeView(Context context) {
        super(context);
    }

    public OrderPayTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.order_pay_info, this);
        ButterKnife.bind(this);
    }

    public void bind(OrderDetailItem item) {
    }
}
