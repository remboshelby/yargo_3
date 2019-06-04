package yargo.inc.orders.fragments.order_list.order_commission.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.ShopParameters;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_commission.CommissionViewModel;
import yargo.inc.orders.fragments.order_list.order_commission.entity.PayEntity;
import yargo.inc.orders.yandex_utils.Settings;

public class CommissionDetailView extends BaseFragment {
    private static final int DOORS_SPECIAL = 2;
    private static final double DOORS_COMMISSION = 0.2;
    private static final double OTHER_COMMISSION = 0.1;

    static double commission_size;

    private OrdersItem ordersItem;

    private BigDecimal amount = BigDecimal.ZERO;
    public static final Currency RUB = Currency.getInstance("RUB");

    private static final String YANDEX_KEY = "live_NTQ0MDIxk7aBD5wWDF7ZVBHLSzSntBlZgfk2wEGMYmg";

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

        ButterKnife.bind(this, view);
        OrderListsFragment.getOrdersComponent().inject(this);

        Checkout.attach(getRoot().getSupportFragmentManager());
        Checkout.setResultCallback(new Checkout.ResultCallback() {
            @Override
            public void onResult(@NotNull String paymentToken, @NotNull PaymentMethodType paymentMethodType) {
                commissionViewModel.setPayRequisites(new PayEntity(paymentToken, paymentMethodType.name(), String.valueOf(amount), ordersItem.getName(), ordersItem.getID()));
//                getRoot().pushFragment(new SuccessTokinizeView(commissionViewModel),false);
            }
        });

        commissionViewModel.observOrderDetailData(this, new Observer<OrderDetailResponse>() {
            @Override
            public void onChanged(OrderDetailResponse orderDetailResponse) {
                setOrdersItem(orderDetailResponse.getResponse().getOrders().get(0));
                int price = ordersItem.getPrice();

                if (ordersItem.getIdSpecialization() == DOORS_SPECIAL) {
                    commission_size = DOORS_COMMISSION * price;
                } else {
                    commission_size = OTHER_COMMISSION * price;
                }
                tvStartPrice.setText(getString(R.string.order_star_price, (String.valueOf(ordersItem.getPrice()) + Html.fromHtml(" &#x20bd"))));
                tvAdress.setText((Html.fromHtml("<b>Адресс: </b>" + ordersItem.getAddress())));
                tvOrderManager.setText((Html.fromHtml("<b>Заказчик: </b>" + ordersItem.getClient())));
                tvOrderDate.setText((Html.fromHtml("<b>Дата: </b>" + commissionViewModel.dateCreator(ordersItem.getStartworking()) + " - " + commissionViewModel.dateCreator(ordersItem.getDeadline()))));
                tvFinalPrice.setText(getString(R.string.order_commisson_price, String.format("%.2f", commission_size)) + " " + Html.fromHtml(" &#x20bd"));
                tvAnatation.setText(Html.fromHtml("Для завершения заявки <b><i>\"" + ordersItem.getName() + "\" (№ " + ordersItem.getID() + ")</i></b>" +
                        ", необходимо осуществить оплату комиссионных сборов"));
            }
        });
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
        onAmountChange(new BigDecimal(String.format("%.2f", commission_size)
                .replace(",", ".")));
        makeBuy("Комиссия по заявке №" + ordersItem.getID(),
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
}
