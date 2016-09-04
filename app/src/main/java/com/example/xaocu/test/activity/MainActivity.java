package com.example.xaocu.test.activity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.xaocu.test.BaseAdapter;
import com.example.xaocu.test.Logger;
import com.example.xaocu.test.OnDelegateClickListener;
import com.example.xaocu.test.R;
import com.example.xaocu.test.delegates.BaseDelegate;
import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.mvp.BaseMvpActivity;
import com.example.xaocu.test.mvp.view.MainView;
import com.example.xaocu.test.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView, OnDelegateClickListener {
  private static final String LOG_TAG = Logger.createTag(MainActivity.class);
  @BindView(R.id.rvView)
  RecyclerView rvView;

  private BaseAdapter<SmallItem> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);

    initRecyclerView();
    getPresenter().doSome();
  }

  private void initRecyclerView() {
    rvView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new BaseAdapter<>(this);
    rvView.setAdapter(adapter);
  }

  @NonNull
  @Override
  public MainPresenter createPresenter() {
    return new MainPresenter(this);
  }

  @Override
  public void getSomeSuccess(List<SmallItem> items) {
    adapter.addAll(items);
    adapter.notifyDataSetChanged();
    showToast("Connect to server is success");
  }

  @Override
  public void getSomeError(Throwable t) {
    showToast("Can't connect to the server");
  }

  @Override
  public void onClick(BaseDelegate.BaseViewHolder holder, View view, int position, @ClickType int clickType) {
    String email = adapter.getItem(position).getComment().getName();
    showToast(email);
  }

  private void showToast(@NonNull String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

  }
}
