package yargo.inc.orders.fragments.order_list.order_commission.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListViewModel;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_commission.CommissionViewModel;
import yargo.inc.orders.fragments.order_list.order_commission.entity.PayEntity;

public class SuccessTokinizeView extends BaseFragment {


    @BindView(R2.id.webView)
    WebView webView;

    @Inject
    protected CommonSharedPreferences commonSharedPreferences;

    private CommissionViewModel commissionViewModel;

    ProgressDialog progressDialog;
    private static final String TITLE = SuccessTokinizeView.class.getSimpleName();
    private PayEntity payEntity;

    public SuccessTokinizeView(PayEntity payEntity,CommissionViewModel commissionViewModel) {
        this.payEntity = payEntity;
        this.commissionViewModel = commissionViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderListsFragment.getOrdersComponent().inject(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Загрузка...");
        ButterKnife.bind(this, view);

        payEntity.getAmmount();

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        String authToken = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);
        webView.loadUrl("http://api.yargo.pro/payment/create?" +
                "LoginForm[auth_key]=" + authToken +
                "&Payment[payment_token]=" + payEntity.getPayToken() +
                "&Payment[amount]=" + payEntity.getAmmount() +
                "&Payment[currency]=RUB" +
                "&Payment[id_order]=" + payEntity.getOrderId() +
                "&Payment[description]=" + payEntity.getDescription());
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.success_tokinez, container, false);
    }

    class MyWebViewClient extends WebViewClient {
        private final String secure = "https://checkout.gateline.net/secure3d/rf3d";
        private final String FINISH_PAGE = "http://api.yargo.pro/payment/success-confirmed";
        private final long LOADING_ERROR_TIMEOUT = TimeUnit.SECONDS.toMillis(45);

        // WebView instance is kept in WeakReference because of mPageLoadingTimeoutHandlerTask
        private WeakReference<WebView> mReference;
        private boolean mLoadingFinished = false;
        private boolean mLoadingError = false;
        private long mLoadingStartTime = 0;

        // Helps to handle case when onReceivedError get called before onPageStarted
        // Problem cached on Nexus 7; Android 5
        private String mOnErrorUrl;

        // Helps to know what page is loading in the moment
        // Allows check url to prevent onReceivedError/onPageFinished calling for wrong url
        // Helps to prevent double call of onPageStarted
        // These problems cached on many devices
        private String mUrl;

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String url) {
            if (mUrl != null && !mLoadingError) {
                Log.e(TITLE, "onReceivedError: " + errorCode + ", " + description);
                mLoadingError = true;
            } else {
                mOnErrorUrl = removeLastSlash(url);
            }
        }

        // We need startsWith because some extra characters like ? or # are added to the url occasionally
        // However it could cause a problem if your server load similar links, so fix it if necessary
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            url = removeLastSlash(url);
            if (!startsWith(url, mUrl) && !mLoadingFinished) {
                Log.e(TITLE, "shouldOverrideUrlLoading: " + url);
                if (url.equals(secure))progressDialog.show();
                mUrl = null;
                onPageStarted(view, url, null);
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favIcon) {
            url = removeLastSlash(url);

            progressDialog.show();
            if (startsWith(url, mOnErrorUrl)) {
                mUrl = url;
                mLoadingError = true;

                mLoadingFinished = false;
                if (url.equals(secure)) {
                    view.setVisibility(View.GONE);
                    progressDialog.show();
                } else view.setVisibility(View.VISIBLE);
                onPageFinished(view, url);
            }
            if (mUrl == null) {
                Log.e(TITLE, "onPageStarted: " + url);
                mUrl = url;
                mLoadingError = false;
                mLoadingFinished = false;
                mLoadingStartTime = System.currentTimeMillis();
                view.removeCallbacks(mPageLoadingTimeoutHandlerTask);
                view.postDelayed(mPageLoadingTimeoutHandlerTask, LOADING_ERROR_TIMEOUT);
                mReference = new WeakReference<>(view);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e(TITLE, "onPageFinished: " + url);
            url = removeLastSlash(url);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (startsWith(url, mUrl) && !mLoadingFinished) {
                Log.e(TITLE, "Page ");
                mLoadingFinished = true;
                view.removeCallbacks(mPageLoadingTimeoutHandlerTask);
                if (url.equals(secure)) {
                    view.setVisibility(View.GONE);
                } else view.setVisibility(View.VISIBLE);

                if (url.contains(FINISH_PAGE)) {
                    commissionViewModel.setIsPayed(true);
                    view.setVisibility(View.GONE);
                }
                mOnErrorUrl = null;
                mUrl = null;
            } else if (mUrl == null) {
                // On some devices (e.g. Lg Nexus 5) onPageStarted sometimes not called at all
                // The only way I found to fix it is to reset WebViewClient
                view.setWebViewClient(new MyWebViewClient());
                mLoadingFinished = true;
            }
        }

        private String removeLastSlash(String url) {
            while (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            return url;
        }

        // We need startsWith because some extra characters like ? or # are added to the url occasionally
        // However it could cause a problem if your server load similar links, so fix it if necessary
        private boolean startsWith(String str, String prefix) {
            return str != null && prefix != null && str.startsWith(prefix);
        }

        private final Runnable mPageLoadingTimeoutHandlerTask = new Runnable() {
            @Override
            public void run() {
                Log.e(TITLE, "Web page loading timeout: " + mUrl);
                mUrl = null;
                mLoadingFinished = true;
                long loadingTime = System.currentTimeMillis() - mLoadingStartTime;
                if (mReference != null) {
                    WebView webView = mReference.get();
                    if (webView != null) {
                        webView.stopLoading();
                    }
                }

                //              Toast.makeText(getRoot(), "Web page loading error: " + loadingTime, Toast.LENGTH_SHORT).show();
            }
        };
    }

    ;
}
