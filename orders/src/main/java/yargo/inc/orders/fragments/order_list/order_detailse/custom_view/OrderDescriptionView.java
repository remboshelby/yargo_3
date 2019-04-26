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

public class OrderDescriptionView extends ConstraintLayout {

    @BindView(R2.id.orderDescription)
    TextView orderDescription;
    @BindView(R2.id.orderComment)
    TextView orderComment;
    @BindView(R2.id.tilteDescription)
    TextView tilteDescription;
    @BindView(R2.id.titleComment)
    TextView titleComment;

    public OrderDescriptionView(Context context) {
        super(context);
    }

    public OrderDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.order_discription, this);
        ButterKnife.bind(this);
    }
}
