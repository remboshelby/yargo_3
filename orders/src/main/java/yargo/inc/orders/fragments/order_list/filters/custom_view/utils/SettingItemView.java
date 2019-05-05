package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R2;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.SettingModel;

public class SettingItemView extends ConstraintLayout {

    @BindView(R2.id.imgIconFilterType)
    AppCompatImageView imgIconFilterType;
    @BindView(R2.id.topTitle)
    TextView topTitle;
    @BindView(R2.id.bottomTitle)
    TextView bottomTitle;
    @BindView(R2.id.imgBtnOpenNextFilter)
    AppCompatImageButton imgBtnOpenNextFilter;

    private SettingModel item;

    public SettingItemView(Context context) {
        super(context);
        init(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.setting_layout, this);
        ButterKnife.bind(this);
    }

    public void bind(SettingModel item) {
        this.item = item;
        imgIconFilterType.setImageDrawable(getResources().getDrawable(item.getPicId()));
        topTitle.setText(item.getTopLabel());
        bottomTitle.setText(item.getBottomLabel());
    }
}
