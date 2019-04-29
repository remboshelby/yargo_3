package yargo.inc.orders.fragments.order_list.order_detailse.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.utils.CircleImageView_;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.order_detailse.utils.OrderDetailItem;

public class ClientAboutView extends ConstraintLayout {


    @BindView(R2.id.titleClient)
    TextView titleClient;
    @BindView(R2.id.clientAvatar)
    CircleImageView_ clientAvatar;
    @BindView(R2.id.clientInfo)
    TextView clientInfo;

    public ClientAboutView(Context context) {
        super(context);
    }

    public ClientAboutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.client_order, this);
        ButterKnife.bind(this);
    }

    public void bind(OrderDetailItem item) {
    }
}
