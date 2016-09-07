package com.example.xaocu.test.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xaocu.test.BaseAdapter;
import com.example.xaocu.test.OnDelegateClickListener;
import com.example.xaocu.test.R;
import com.example.xaocu.test.TestApp;
import com.example.xaocu.test.adapters.SuggestAdapter;
import com.example.xaocu.test.delegates.BaseDelegate;
import com.example.xaocu.test.items.OnlineFeedItem;
import com.example.xaocu.test.model.BaseFeed;
import com.example.xaocu.test.model.RawFeed;
import com.example.xaocu.test.mvp.BaseMvpActivity;
import com.example.xaocu.test.mvp.view.MainView;
import com.example.xaocu.test.net.ServiceType;
import com.example.xaocu.test.presenter.MainPresenter;
import com.example.xaocu.test.tools.NetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView, OnDelegateClickListener {
  public static final int MIN_SEARCH_LENGTH = 4;
  public static final String TEXT_KEY = "text";
  public static final String SERVICE_TYPE_KEY = "serviceType";
  @BindView(R.id.rvView)
  RecyclerView rvView;
  @BindView(R.id.etSearch)
  AutoCompleteTextView etSearch;
  @BindView(R.id.progressLayout)
  View progressLayout;
  @BindView(R.id.spinnerView)
  Spinner spinnerView;

  private BaseAdapter<OnlineFeedItem> adapter;
  private SuggestAdapter suggestAdapter;
  private Map<Integer, Integer> serviceDependence;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    unbinder = ButterKnife.bind(this);
    initServiceDependence();
    initSuggestView();
    initRecyclerView();
    initSpinner();
    if (savedInstanceState != null) {
      final int serviceType = savedInstanceState.getInt(SERVICE_TYPE_KEY);
      final String text = savedInstanceState.getString(TEXT_KEY);
      getPresenter().onRestoreState(savedInstanceState);
      spinnerView.setSelection(getSelectPosition(serviceType));
      etSearch.setText(text);
      search(text);
    } else {
      getPresenter().findCvCode();
    }
  }

  private void initSpinner() {
    String[] data = getResources().getStringArray(R.array.feeds);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinnerView.setAdapter(adapter);
    // выделяем элемент
    spinnerView.setSelection(0);
    // устанавливаем обработчик нажатия
    spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
                                 int position, long id) {
        TestApp.getManager().setServiceType(serviceDependence.get(position));
      }
      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
  }

  private void initServiceDependence() {
    serviceDependence = new HashMap<>();
    serviceDependence.put(0, ServiceType.QUANDL);
    serviceDependence.put(1, ServiceType.QUOTEMEDIA);
    serviceDependence.put(2, ServiceType.GOOGLE);
    serviceDependence.put(3, ServiceType.YAHOO);
  }

  private int getSelectPosition(int serviceType) {
    for (int i = 0; i < serviceDependence.size(); i++) {
      if (serviceDependence.get(i) == serviceType) {
        return i;
      }
    }
    return 0;
  }

  private void initSuggestView() {
    etSearch.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String abbr = ((RawFeed) suggestAdapter.getItem(i)).getAbbreviation();
        search(abbr);
        etSearch.setText(abbr);
      }
    });
  }

  @OnClick(R.id.btnSearch)
  public void clickSearchButton(View view) {
    String text = etSearch.getText().toString();
    if (text.length() < MIN_SEARCH_LENGTH) {
      return;
    }
    search(text);
  }

  private void search(@NonNull String text) {
    hideKeyboard();
    if (!NetUtils.isConnected()) {
      final String connectionLost = getString(R.string.connection_lost);
      showToast(connectionLost);
      setTitle(connectionLost);
      return;
    }
    setTitle("");
    showProgress();
    adapter.clear();
    getPresenter().getData(text);
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
  public void successDataLoading(List<OnlineFeedItem> data) {
    adapter.clear();
    adapter.notifyDataSetChanged();
    adapter.addAll(data);
    adapter.notifyDataSetChanged();
    showToast(getString(R.string.connection_success));
    showContent();
  }

  @Override
  public void errorDataLoading(Throwable t) {
    showContent();
    showToast(getString(R.string.cant_connect_to_server));
  }

  @Override
  public void csvDataLoaded(List<BaseFeed> csvList) {
    suggestAdapter = new SuggestAdapter();
    suggestAdapter.setTickers(csvList);
    etSearch.setAdapter(suggestAdapter);
    showContent();
  }

  private void showProgress() {
    progressLayout.setVisibility(View.VISIBLE);
    rvView.setVisibility(View.GONE);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getPresenter().onSaveInstanceState(outState);
    outState.putString(TEXT_KEY, etSearch.getText().toString());
    outState.putInt(SERVICE_TYPE_KEY, TestApp.getManager().getServiceType());
  }

  private void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
  private void showContent() {
    progressLayout.setVisibility(View.GONE);
    rvView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onClick(BaseDelegate.BaseViewHolder holder, View view, int position, @ClickType int clickType) {
  }

  private void showToast(@NonNull String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }
}
