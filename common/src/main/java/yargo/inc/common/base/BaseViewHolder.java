package yargo.inc.common.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void setOnClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
    }

    public abstract void bind(T item);

    public void bind(T item, int position){
        bind(item);
    }


}
