package com.example.xaocu.test.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.OnDelegateClickListener;
import com.example.xaocu.test.R;
import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.model.Feed;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class SmallItemDelegate extends BaseDelegate {


  public SmallItemDelegate(OnDelegateClickListener listener) {
    super(listener);
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
    return new SmallItemViewHolder(getLayoutInflater(parent).inflate(R.layout.small_item_view, parent, false));
  }

  @Override
  public void onBindViewHolder(Object object, RecyclerView.ViewHolder holder, int position, List<? extends BaseItem> items) {
    if (object == null) {
      return;
    }
    SmallItemViewHolder vh = (SmallItemViewHolder) holder;
    List<Object> objects = ((SmallItem)items.get(position)).getObjects();
    List<String> column_names = ((Feed) object).getDataset().getColumn_names();
    if (column_names == null) {
      return;
    }
    for (int i = 0; i < column_names.size(); i++) {
      String columnName = column_names.get(i);
      if ("open".equalsIgnoreCase(columnName)) {
        vh.tvOpen.setText(objects.get(i).toString());
      } else if ("high".equalsIgnoreCase(columnName)) {
        vh.tvHigh.setText(objects.get(i).toString());
      } else if ("low".equalsIgnoreCase(columnName)) {
        vh.tvLow.setText(objects.get(i).toString());
      }else if ("close".equalsIgnoreCase(columnName)) {
        vh.tvClose.setText(objects.get(i).toString());
      }
    }
  }

  public class SmallItemViewHolder extends BaseViewHolder{
    @BindView(R.id.tvOpen)
    TextView tvOpen;
    @BindView(R.id.tvHigh)
    TextView tvHigh;
    @BindView(R.id.tvLow)
    TextView tvLow;
    @BindView(R.id.tvClose)
    TextView tvClose;
    public SmallItemViewHolder(View itemView) {
      super(itemView);
    }

    @OnClick(R.id.itemLayout)
    public void onItemClick(View view) {
      onClick(this, view, getAdapterPosition(), ClickType.SIMPLE_CLICK);
    }
  }
}
