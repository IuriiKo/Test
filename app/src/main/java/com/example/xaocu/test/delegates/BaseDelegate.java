package com.example.xaocu.test.delegates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.OnDelegateClickListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public abstract class BaseDelegate {
  private OnDelegateClickListener listener;

  public BaseDelegate(OnDelegateClickListener listener) {
    this.listener = listener;
  }

  public abstract BaseViewHolder onCreateViewHolder(ViewGroup parent);

  public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<? extends BaseItem> items);

  public class BaseViewHolder extends RecyclerView.ViewHolder implements OnDelegateClickListener {
    public BaseViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }


    @Override
    public void onClick(BaseViewHolder holder, View view, int position, @ClickType int clickType) {
      if (position == RecyclerView.NO_POSITION || listener == null) {
        return;
      }
      listener.onClick(holder, view, position, clickType);
    }
  }

  protected LayoutInflater getLayoutInflater(View view) {
    return LayoutInflater.from(view.getContext());
  }
}
