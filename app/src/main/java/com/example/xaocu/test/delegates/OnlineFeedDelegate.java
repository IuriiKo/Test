package com.example.xaocu.test.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.OnDelegateClickListener;
import com.example.xaocu.test.R;
import com.example.xaocu.test.items.OnlineFeedItem;
import com.example.xaocu.test.model.Feed;
import com.example.xaocu.test.model.OnlineFeed;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class OnlineFeedDelegate extends BaseDelegate {


  public OnlineFeedDelegate(OnDelegateClickListener listener) {
    super(listener);
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
    return new SmallItemViewHolder(getLayoutInflater(parent).inflate(R.layout.small_item_view, parent, false));
  }

  @Override
  public void onBindViewHolder(Object object, RecyclerView.ViewHolder holder, int position, List<? extends BaseItem> items) {
    SmallItemViewHolder vh = (SmallItemViewHolder) holder;
    OnlineFeed feed = ((OnlineFeed)((OnlineFeedItem)items.get(position)).getFeeds());
    vh.tvOpen.setText(feed.getOpen());
    vh.tvHigh.setText(feed.getHigh());
    vh.tvLow.setText(feed.getLow());
    vh.tvClose.setText(feed.getClose());
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
