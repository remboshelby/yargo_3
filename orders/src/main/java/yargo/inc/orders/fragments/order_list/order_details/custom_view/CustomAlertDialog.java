package yargo.inc.orders.fragments.order_list.order_details.custom_view;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListViewModel;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;

public class CustomAlertDialog extends FrameLayout {

    @Inject
    protected OrderListViewModel orderListViewModel;
    @BindView(R2.id.chbConfirOfferts)
    CheckBox chbConfirOfferts;
    @BindView(R2.id.textViewOferts)
    TextView textViewOferts;
    @BindView(R2.id.btnGetOrderNo)
    Button btnGetOrderNo;
    @BindView(R2.id.btnGetOrderYes)
    Button btnGetOrderYes;

    public interface CustomAlertDialogListener{
        void onbtnGetOrderNoClick();
        void onbtnGetOrderYesClick();
    }

    private CustomAlertDialogListener listener;

    public CustomAlertDialog(Context context) {
        super(context);
        init(context);

    }

    private void init(Context context) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, this);
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this);

        textViewOferts.setClickable(true);
        textViewOferts.setMovementMethod(LinkMovementMethod.getInstance());

        btnGetOrderYes.setEnabled(false);
        btnGetOrderYes.setTextColor(getResources().getColor(R.color.colorAccent_shadow));

        chbConfirOfferts.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                btnGetOrderYes.setEnabled(true);
                btnGetOrderYes.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                btnGetOrderNo.setEnabled(false);
                btnGetOrderYes.setTextColor(getResources().getColor(R.color.colorAccent_shadow));
            }
        });
    }

    public CustomAlertDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setListener(CustomAlertDialogListener listener) {
        this.listener = listener;
    }
    @OnClick(R2.id.btnGetOrderYes)
    void onbtnGetOrderYesClick(){
        listener.onbtnGetOrderYesClick();
    }
    @OnClick(R2.id.btnGetOrderNo)
    void onbtnGetOrderNoClick(){
        listener.onbtnGetOrderNoClick();
    }


}
