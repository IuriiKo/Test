package com.example.xaocu.test;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public  abstract class BaseDelegate{
  private OnDelegateClickListener listener;
  public BaseDelegate(OnDelegateClickListener listener) {
    this.listener = listener;
  }
  public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
  public abstract <I extends BaseItem> void  onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<I> items);


}
