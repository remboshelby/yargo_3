package yargo.inc.orders.fragments.order_list.order_details.custom_view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailItem;

public class MapItemView extends ConstraintLayout implements OnMapReadyCallback {
    @BindView(R2.id.litleMap)
    MapView litleMap;
    @BindView(R2.id.orderAdress)
    TextView orderAdress;
    GoogleMap map;
    private OrdersItem ordersItem;

    public MapItemView(Context context) {
        super(context);
        init(context);
    }

    public MapItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.litle_map_view, this);
        ButterKnife.bind(this);
    }

    public void bind(OrderDetailItem item) {
        OrderDetailResponse orderDetailResponse = item.getOrderDetailResponse();
        ordersItem = orderDetailResponse.getResponse().getOrders().get(0);

        litleMap.onCreate(null);
        litleMap.getMapAsync(this);
        ImageSpan imageSpan = new ImageSpan(getContext(), R.drawable.ic_room_black_24dp);
        SpannableString spannableString = new SpannableString(" " + ordersItem.getAddress());
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderAdress.setText(spannableString);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        setMapLocation();
    }

    private void setMapLocation() {
        if (!ordersItem.getLatitude().isEmpty()) {
            LatLng latLng = new LatLng(Double.valueOf(ordersItem.getLatitude()), Double.valueOf(ordersItem.getLongitude()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
            map.addMarker(new MarkerOptions().position(latLng));
        }
    }
}
