package com.example.xaocu.test.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.xaocu.test.BaseAdapter;
import com.example.xaocu.test.Logger;
import com.example.xaocu.test.OnDelegateClickListener;
import com.example.xaocu.test.R;
import com.example.xaocu.test.adapters.SuggestAdapter;
import com.example.xaocu.test.delegates.BaseDelegate;
import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.model.Feed;
import com.example.xaocu.test.mvp.BaseMvpActivity;
import com.example.xaocu.test.mvp.view.MainView;
import com.example.xaocu.test.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView, OnDelegateClickListener {
  public static final int MIN_SEARCH_LENGTH = 4;
  @BindView(R.id.rvView)
  RecyclerView rvView;
  @BindView(R.id.etSearch)
  AutoCompleteTextView etSearch;
  private BaseAdapter<SmallItem> adapter;
  private SuggestAdapter suggestAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    unbinder = ButterKnife.bind(this);
    initSuggestView();
    initRecyclerView();
    getPresenter().findCvCode();
  }

  private void initSuggestView() {
    etSearch.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        search(((Pair<String, String>)suggestAdapter.getItem(i)).first);
        etSearch.setText(((Pair<String, String>)suggestAdapter.getItem(i)).first);
      }
    });
  }

  @OnTextChanged(R.id.etSearch)
  public void textChanged(CharSequence text) {
  }

  @OnClick(R.id.btnSearch)
  public void search(View view) {
    String text = etSearch.getText().toString();
    if (text.length() < MIN_SEARCH_LENGTH) {
      return;
    }
    search(text);
  }

  private void search(@NonNull String text) {
    adapter.clear();
    getPresenter().doSome(text);
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
  public void getSomeSuccess(Pair<List<SmallItem>,Feed> data) {
    adapter.clear();
    adapter.notifyDataSetChanged();
    adapter.setBindObject(data.second);
    adapter.addAll(data.first);
    adapter.notifyDataSetChanged();
    showToast("Connect to server is success");
  }

  @Override
  public void getSomeError(Throwable t) {
    showToast("Can't connect to the server");
  }

  @Override
  public void loadCSV(List<Pair<String, String>> csvList) {
    suggestAdapter = new SuggestAdapter();
    suggestAdapter.setTickers(csvList);
    etSearch.setAdapter(suggestAdapter);
  }

  @Override
  public void onClick(BaseDelegate.BaseViewHolder holder, View view, int position, @ClickType int clickType) {
    Object o = adapter.getItem(position).getObjects().get(1);
    showToast(o.toString());
  }

  private void showToast(@NonNull String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

  }
}
