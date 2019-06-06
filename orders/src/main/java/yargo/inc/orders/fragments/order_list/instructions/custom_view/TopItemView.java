package yargo.inc.orders.fragments.order_list.instructions.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.instructions.models.SubHeadersItem;

public class TopItemView extends ConstraintLayout {

    public static final String TAG = TopItemView.class.getSimpleName();
    @BindView(R2.id.tvNumber)
    TextView tvNumber;
    @BindView(R2.id.headerContext)
    TextView headerContext;

    public TopItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.top_instruction_header, this);
        ButterKnife.bind(this);
    }

    public TopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
    public void bind(SubHeadersItem subHeadersItem){
        tvNumber.setText(subHeadersItem.getNumber());
        headerContext.setText(subHeadersItem.getText());
    }
}
