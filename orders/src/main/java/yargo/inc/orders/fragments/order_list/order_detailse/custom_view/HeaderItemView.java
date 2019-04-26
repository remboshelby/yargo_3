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

public class HeaderItemView extends ConstraintLayout {

    @BindView(R2.id.orderDate)
    TextView orderDate;
    @BindView(R2.id.orderViewCount)
    TextView orderViewCount;
    @BindView(R2.id.orderDescription)
    TextView orderDescription;
    @BindView(R2.id.orderPrice)
    TextView orderPrice;
    @BindView(R2.id.orderPayType)
    TextView orderPayType;

    public HeaderItemView(Context context) {
        super(context);
    }

    public HeaderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.header_item_view, this);
        ButterKnife.bind(this);
    }
}
