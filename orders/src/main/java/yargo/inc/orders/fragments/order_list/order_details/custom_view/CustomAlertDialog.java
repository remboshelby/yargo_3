package yargo.inc.orders.fragments.order_list.order_details.custom_view;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel;

public class CustomAlertDialog extends FrameLayout {

    @Inject
    protected UserOrdersViewModel userOrdersViewModel;
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
        void showOffert();
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
        SpannableString link = makeLinkSpan( " оферты", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showOffert();
            }
        });
        textViewOferts.append(link);
        makeLinksFocusable(textViewOferts);

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
    private SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
        SpannableString link = new SpannableString(text);
        link.setSpan(new ClickableString(listener), 0, text.length(),
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return link;
    }

    private void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
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

    private static class ClickableString extends ClickableSpan {
        private View.OnClickListener mListener;
        public ClickableString(View.OnClickListener listener) {
            mListener = listener;
        }
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
    }
}
