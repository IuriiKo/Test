package com.example.xaocu.test.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.OnDelegateClickListener;

import java.util.List;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class SmallItemDelegate extends BaseDelegate {


  public SmallItemDelegate(OnDelegateClickListener listener) {
    super(listener);
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
    return new SmallItemViewHolder(parent);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<BaseItem> items) {

  }

  public class SmallItemViewHolder extends BaseViewHolder{

    public SmallItemViewHolder(View itemView) {
      super(itemView);
    }

  }
}
