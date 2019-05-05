package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R2;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CategoryModel;

public class CategoryItemView extends ConstraintLayout {

    @BindView(R2.id.categoryImg)
    AppCompatImageView categoryImg;
    @BindView(R2.id.categoryTitle)
    TextView categoryTitle;
    @BindView(R2.id.categoryChecked)
    CheckBox categoryChecked;

    public CategoryItemView(Context context) {
        super(context);
        init(context);
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.category_item_layout, this);
        ButterKnife.bind(this);
    }

    public void bind(CategoryModel item) {
        categoryImg.setImageDrawable(getResources().getDrawable(item.getCategoryImg()));
        categoryTitle.setText(item.getTitleCategory());
        categoryChecked.setChecked(item.isChecked());
    }
}
