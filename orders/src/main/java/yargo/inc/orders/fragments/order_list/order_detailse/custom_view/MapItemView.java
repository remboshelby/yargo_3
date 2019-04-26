package yargo.inc.orders.fragments.order_list.order_detailse.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;

public class MapItemView extends ConstraintLayout {

    @BindView(R2.id.litleMap)
    MapView litleMap;
    @BindView(R2.id.orderAdress)
    TextView orderAdress;

    public MapItemView(Context context) {
        super(context);
    }

    public MapItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.litle_map_view, this);
        ButterKnife.bind(this);
    }
}
