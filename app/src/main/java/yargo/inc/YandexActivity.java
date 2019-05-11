package yargo.inc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.ShopParameters;
import yargo.inc.yandex_utils.AmountFormatter;
import yargo.inc.yandex_utils.Settings;

public class YandexActivity extends AppCompatActivity {
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("99999.99");
    public static final Currency RUB = Currency.getInstance("RUB");
    public static final String KEY_AMOUNT = "amount";

    Context context;

    @Nullable
    private View buyButton;
    @NonNull
    private BigDecimal amount = BigDecimal.ZERO;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        initUi();
        Checkout.attach(getSupportFragmentManager());
        // Set action on MSDK result
        Checkout.setResultCallback(new Checkout.ResultCallback() {
            @Override
            public void onResult(@NotNull String paymentToken, @NotNull PaymentMethodType paymentMethodType) {
//                startActivity(SuccessTokenizeActivity.createIntent(context, paymentToken, paymentMethodType.name()));
            }
        });
//        setContentView(R.layout.activity_yandex);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }
    private void initUi() {
        setContentView(R.layout.activity_yandex);

        setTitle(R.string.main_toolbar_title);

//        final ScrollView scrollContainer = findViewById(R.id.scroll_container);
//        if (scrollContainer != null) {
//            scrollContainer.post(new Runnable() {
//                @Override
//                public void run() {
//                    scrollContainer.fullScroll(ScrollView.FOCUS_DOWN);
//                }
//            });
//        }

        buyButton = findViewById(R.id.buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBuyClick();
            }
        });

        final EditText priceEdit = findViewById(R.id.price);
        priceEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        priceEdit.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        priceEdit.addTextChangedListener(new AmountFormatter(priceEdit, this::onAmountChange, RUB, MAX_AMOUNT));
        priceEdit.setOnEditorActionListener((v, actionId, event) -> {
            final boolean isActionDone = actionId == EditorInfo.IME_ACTION_DONE;
            if (isActionDone) {
                onBuyClick();
            }
            return isActionDone;
        });

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        priceEdit.setText(sp.getString(KEY_AMOUNT, BigDecimal.ZERO.toString()));
    }
    private void onAmountChange(@NonNull BigDecimal newAmount) {
        amount = newAmount;

        if (buyButton != null) {
            buyButton.setEnabled(validateAmount());
        }
    }
    private void onBuyClick() {
        if (validateAmount()) {
            final Settings settings = new Settings(this);
            final Set<PaymentMethodType> paymentMethodTypes = getPaymentMethodTypes(settings);

            // Start MSDK to get payment token
            Checkout.tokenize(
                    this,
                    new Amount(amount, RUB),
                    new ShopParameters(
                            getString(R.string.main_product_name),
                            getString(R.string.main_product_description),
                            "test_NTU3NDc5C97JEG4HFaIW3SVsWILN7p0dFXNjuusqBRo",
                            paymentMethodTypes
                    )
            );
        }
    }
    private boolean validateAmount() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
    @NonNull
    private static Set<PaymentMethodType> getPaymentMethodTypes(Settings settings) {
        final Set<PaymentMethodType> paymentMethodTypes = new HashSet<>();

        if (settings.isYandexMoneyAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.YANDEX_MONEY);
        }

        if (settings.isNewCardAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.BANK_CARD);
        }

        if (settings.isSberbankOnlineAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.SBERBANK);
        }

        if (settings.isGooglePayAllowed()) {
            paymentMethodTypes.add(PaymentMethodType.GOOGLE_PAY);
        }

        return paymentMethodTypes;
    }
}
