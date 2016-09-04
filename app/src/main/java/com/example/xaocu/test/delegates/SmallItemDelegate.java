package com.example.xaocu.test.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.OnDelegateClickListener;
import com.example.xaocu.test.R;
import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.model.Comment;

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
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<? extends BaseItem> items) {
    SmallItemViewHolder vh = (SmallItemViewHolder) holder;
    Comment comment = ((SmallItem)items.get(position)).getComment();
    vh.tvName.setText(comment.getName());
    vh.tvEmail.setText(comment.getEmail());
    vh.tvBody.setText(comment.getBody());
  }


  public class SmallItemViewHolder extends BaseViewHolder{
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvBody)
    TextView tvBody;
    public SmallItemViewHolder(View itemView) {
      super(itemView);
    }

    @OnClick(R.id.itemLayout)
    public void onItemClick(View view) {
      onClick(this, view, getAdapterPosition(), ClickType.SIMPLE_CLICK);
    }
  }
}
