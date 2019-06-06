package yargo.inc.orders.fragments.order_list.instructions.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.instructions.models.SubHeadersItem;

public class MiddleItemView extends ConstraintLayout {

    @BindView(R2.id.imageView2)
    ImageView imageView2;
    @BindView(R2.id.tvNumber)
    TextView tvNumber;
    @BindView(R2.id.headerContext)
    TextView headerContext;

    public MiddleItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.middle_instruction_header, this);
        ButterKnife.bind(this);
    }

    public MiddleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void bind(SubHeadersItem subHeadersItem){
        if (subHeadersItem.getNumber().equals("")){
            imageView2.setVisibility(GONE);
            tvNumber.setVisibility(GONE);
        }
        else if(subHeadersItem.getNumber().equals("_")){
            tvNumber.setVisibility(GONE);
        }
        else {
            tvNumber.setText(subHeadersItem.getNumber());
        }
        headerContext.setText(subHeadersItem.getText());
    }
}
