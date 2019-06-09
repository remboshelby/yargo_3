package yargo.inc.orders.fragments.order_list.order_commission.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.ShopParameters;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_commission.CommissionViewModel;
import yargo.inc.orders.fragments.order_list.order_commission.entity.PayEntity;
import yargo.inc.orders.yandex_utils.Settings;

public class CommissionDetailView extends BaseFragment {

    private OrdersItem ordersItem;

    private BigDecimal amount = BigDecimal.ZERO;
    public static final Currency RUB = Currency.getInstance("RUB");

    private static final String YANDEX_KEY = "live_NTQ0MDIxk7aBD5wWDF7ZVBHLSzSntBlZgfk2wEGMYmg";

    @BindView(R2.id.contentLoadProgressBar)
    ContentLoadingProgressBar contentLoadProgressBar;
    @BindView(R2.id.moneyType)
    TextView moneyType;
    @BindView(R2.id.finalCoast)
    AutoCompleteTextView finalCoast;

    @BindView(R2.id.tvAnatation)
    TextView tvAnatation;
    @BindView(R2.id.imgPhone)
    ImageView imgPhone;
    @BindView(R2.id.tvStartPrice)
    TextView tvStartPrice;
    @BindView(R2.id.tvFinalPrice)
    TextView tvFinalPrice;
    @BindView(R2.id.btnMakePay)
    Button btnMakePay;
    @BindView(R2.id.tvOrderDate)
    TextView tvOrderDate;
    @BindView(R2.id.tvAdress)
    TextView tvAdress;
    @BindView(R2.id.tvOrderManager)
    TextView tvOrderManager;
    @BindView(R2.id.tvPayInfo)
    TextView tvPayInfo;

    static double commissionPrice;

    public CommissionViewModel commissionViewModel;

    public CommissionDetailView(CommissionViewModel commissionViewModel) {
        this.commissionViewModel = commissionViewModel;
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.comission_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        commissionViewModel.observComissionPrice(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                tvFinalPrice.setText(getString(R.string.order_commisson_price, String.format("%.2f", aDouble))+ " " + Html.fromHtml(" &#x20bd"));
                setCommissionPrice(aDouble);
            }
        });

        commissionViewModel.observOrderDetailData(this, orderDetailResponse -> {
            setOrdersItem(orderDetailResponse.getResponse().getOrders().get(0));

            tvStartPrice.setText(getString(R.string.order_star_price, (String.valueOf(ordersItem.getPrice()) + Html.fromHtml(" &#x20bd"))));
            tvAdress.setText((Html.fromHtml(getString(R.string.adress_html) + ordersItem.getAddress())));
            tvOrderManager.setText((Html.fromHtml(getString(R.string.customer_html) + ordersItem.getClient())));
            tvOrderDate.setText((Html.fromHtml(getString(R.string.date_html) + commissionViewModel.dateCreator(ordersItem.getStartworking()) + " - " + commissionViewModel.dateCreator(ordersItem.getDeadline()))));

            finalCoast.setText(ordersItem.getPrice()==0 ? "0.0" : String.valueOf(ordersItem.getPrice()));

            moneyType.setText(Html.fromHtml(" &#x20bd"));
            tvAnatation.setText(Html.fromHtml(getString(R.string.order_end_part1) + ordersItem.getName() + getString(R.string.order_end_part2) + ordersItem.getID() + getString(R.string.order_end_part3) +
                    getString(R.string.order_end_part4)));
            showContent();
        });
    }

    @OnTextChanged(R2.id.finalCoast)
    void onFinalCoastChanged(Editable editable){
        if (!editable.toString().isEmpty()) {
            Float finalCoast = Float.valueOf(editable.toString());
            commissionViewModel.setFinalCoast(finalCoast);
        }
        else commissionViewModel.setFinalCoast((float) 0.0);
    }
    private void init(@NonNull View view) {
        ButterKnife.bind(this, view);
        OrderListsFragment.getOrdersComponent().inject(this);
        Checkout.attach(getRoot().getSupportFragmentManager());
        Checkout.setResultCallback((paymentToken, paymentMethodType) -> commissionViewModel.setPayRequisites(new PayEntity(paymentToken, paymentMethodType.name(), String.valueOf(amount), ordersItem.getName(), ordersItem.getID())));
    }

    private boolean validateAmount() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public void onAmountChange(@NonNull BigDecimal newAmount) {
        amount = newAmount;
    }

    public void setOrdersItem(OrdersItem ordersItem) {
        this.ordersItem = ordersItem;
    }

    @OnClick(R2.id.btnMakePay)
    void onPayClick() {
        onAmountChange(new BigDecimal(String.format("%.2f", commissionPrice)
                .replace(",", ".")));
        makeBuy(getString(R.string.order_comission, ordersItem.getID()),
                ordersItem.getName());
    }

    public void makeBuy(String order_number, String order_about) {
        if (validateAmount()) {
            final Settings settings = new Settings(getContext());
            final Set<PaymentMethodType> paymentMethodTypes = commissionViewModel.getPaymentMethodTypes(settings);

            Checkout.tokenize(
                    getContext(),
                    new Amount(amount, RUB),
                    new ShopParameters(
                            order_number,
                            order_about,
                            YANDEX_KEY,
                            paymentMethodTypes
                    )
            );
        }
    }

    public void showContent(){
        tvAnatation.setVisibility(View.VISIBLE);
        imgPhone.setVisibility(View.VISIBLE);
        moneyType.setVisibility(View.VISIBLE);
//        tvStartPrice.setVisibility(View.VISIBLE);
        tvFinalPrice.setVisibility(View.VISIBLE);
        btnMakePay.setVisibility(View.VISIBLE);
        tvOrderDate.setVisibility(View.VISIBLE);
        tvAdress.setVisibility(View.VISIBLE);
        tvOrderManager.setVisibility(View.VISIBLE);
        tvPayInfo.setVisibility(View.VISIBLE);

        contentLoadProgressBar.setVisibility(View.GONE);
    }
    public static void setCommissionPrice(double commission_size) {
        CommissionDetailView.commissionPrice = commission_size;
    }
}
