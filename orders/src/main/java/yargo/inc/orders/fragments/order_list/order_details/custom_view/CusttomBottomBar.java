package yargo.inc.orders.fragments.order_list.order_details.custom_view;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsView;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel;

import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_CLIENT_CANCEL;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_ASSIGNED;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_DONE;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_INWORK;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_VACANT;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_WAIT_PAY;

public class CusttomBottomBar extends ConstraintLayout implements CustomAlertDialog.CustomAlertDialogListener {

    @BindView(R2.id.detailBottomBar)
    Button detailBottomBar;

    private AlertDialog orderDetailAlertDialog;

    private OrderDetailsViewModel orderDetailsViewModel;
    public onClickBtnListener listener;

    public interface onClickBtnListener {
        void actionBtnClick();
        void showOffert();
    }

    public CusttomBottomBar(Context context) {
        super(context);
        init(context);
    }

    public CusttomBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        LayoutInflater.from(context).inflate(R.layout.order_detail_bottom, this);
        ButterKnife.bind(this);
    }

    public void initBottombar() {
        orderDetailsViewModel = OrderDetailsView.getOrderDetailsViewModel();
        switch (orderDetailsViewModel.getOrderStatusId()) {
            case ORDER_IS_VACANT:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                detailBottomBar.setText(getContext().getString(R.string.take_order));
                break;
            case ORDER_IS_ASSIGNED:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                detailBottomBar.setText(getContext().getString(R.string.start_order));
                break;
            case ORDER_IS_INWORK:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                detailBottomBar.setText(getContext().getString(R.string.finish_order));
                break;
            case ORDER_WAIT_PAY:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                detailBottomBar.setText(getContext().getString(R.string.order_pay_comission));
                break;
            case ORDER_IS_DONE:
                detailBottomBar.setVisibility(GONE);
                break;
            case ORDER_CLIENT_CANCEL:
                detailBottomBar.setVisibility(GONE);
                break;
        }
    }

    @OnClick(R2.id.detailBottomBar)
    void onActionClick() {
        switch (orderDetailsViewModel.getOrderStatusId()) {
            case ORDER_IS_VACANT:
                showGetOrederDialog(getContext());
                break;
            case ORDER_IS_ASSIGNED:
                showStartOrderDialog(getContext());
                break;
            case ORDER_IS_INWORK:
                showAccomplishedOrder(getContext());
                break;
            case ORDER_WAIT_PAY:
                break;
        }
    }

    public void setListener(onClickBtnListener listener) {
        this.listener = listener;
    }

    public void showStartOrderDialog(Context context){
        AlertDialog.Builder getOrder= new AlertDialog.Builder(context);
        getOrder.setMessage(getResources().getString(R.string.start_order_confirmation)).setCancelable(false).
                setPositiveButton(getResources().getString(R.string.YES), (dialogInterface, i) -> {
                    orderDetailsViewModel.actionStartOrder();
                    listener.actionBtnClick();
                })
                .setNegativeButton(getResources().getString(R.string.NO), (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertDialog = getOrder.create();
        alertDialog.setTitle(getResources().getString(R.string.request));
        alertDialog.show();
    }
    public void showAccomplishedOrder(Context context){
        AlertDialog.Builder getOrder= new AlertDialog.Builder(context);
        getOrder.setMessage(getResources().getString(R.string.accomplished_order_confirmation)).setCancelable(false).
                setPositiveButton(getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        orderDetailsViewModel.actionAccomplishedOrder();
                        listener.actionBtnClick();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.NO), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = getOrder.create();
        alertDialog.setTitle(getResources().getString(R.string.request));
        alertDialog.show();
    }

    private void showGetOrederDialog(Context context) {
        AlertDialog.Builder getOrderAlertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.alert_order_get, null);
        ((CustomAlertDialog)mView.findViewById(R.id.customAlertDialog)).setListener(this);
        getOrderAlertDialog.setView(mView);
        orderDetailAlertDialog = getOrderAlertDialog.create();
        orderDetailAlertDialog.show();
    }

    public void onbtnGetOrderYesClick(){
        orderDetailsViewModel.actionGetOrder();
        listener.actionBtnClick();
        orderDetailAlertDialog.dismiss();
    }
    public void onbtnGetOrderNoClick(){
        orderDetailAlertDialog.dismiss();
    }
    public void showOffert(){
        listener.showOffert();
    }
}
