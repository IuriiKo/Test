package com.example.xaocu.test.presenter;

import android.os.Bundle;

import com.example.xaocu.test.Logger;
import com.example.xaocu.test.TestApp;
import com.example.xaocu.test.items.OnlineFeedItem;
import com.example.xaocu.test.model.BaseFeed;
import com.example.xaocu.test.mvp.BaseMvpPresenter;
import com.example.xaocu.test.mvp.view.MainView;
import com.example.xaocu.test.net.ServiceType;
import com.example.xaocu.test.tools.CSVFile;
import com.example.xaocu.test.tools.Cash;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class MainPresenter extends BaseMvpPresenter<MainView> {
  private static final String LOG_TAG = Logger.createTag(MainPresenter.class);
  public static final String CASH_KEY = "cash";
  private Subscription subscription;
  private Subscription subscriptionGetCVCode;
  private String oldConstraint;
  private Cash cash = new Cash();
  @ServiceType
  private int cashServiceType;

  public MainPresenter(MainView view) {
    super(view);
  }

  public void getData(String datasetCode) {
    if (datasetCode == null ||
        (datasetCode.equalsIgnoreCase(oldConstraint) && cashServiceType == TestApp.getManager().getServiceType()) ||
        datasetCode.isEmpty()) {
      return;
    }
    if (subscription != null) {
      subscription.unsubscribe();
    }
    oldConstraint = datasetCode;
    cashServiceType = TestApp.getManager().getServiceType();
    List<OnlineFeedItem> listFromCash = cash.getFromCash(oldConstraint, cashServiceType);
    if (listFromCash != null) {
      onSuccessGetData(listFromCash);
      return;
    }
    subscription = TestApp.getManager().getData(datasetCode)
        .map(responseBody -> CSVFile.read(responseBody.byteStream(), cashServiceType))
        .flatMap(Observable::from)
        .map(OnlineFeedItem::new)
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessGetData, this::onErrorGetData);
    addSubscription(subscription);
  }

  private void onSuccessGetData(List<OnlineFeedItem> data) {
    if (getView() == null) {
      return;
    }
    List<OnlineFeedItem> listFromCash = cash.getFromCash(oldConstraint, TestApp.getManager().getServiceType());
    if (listFromCash == null) {
      cash.addToCash(oldConstraint, data, cashServiceType);
    }
    getView().successDataLoading(data);
  }

  private void onErrorGetData(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
    if (getView() == null) {
      return;
    }
    getView().errorDataLoading(t);
  }

  public void findCvCode() {
    if (subscriptionGetCVCode != null) {
      subscriptionGetCVCode.unsubscribe();
    }
    subscriptionGetCVCode = Observable.defer(() -> Observable.just(CSVFile.readRaw()))
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessFindCvCode, this::onErrorFindCvCode);
    addSubscription(subscriptionGetCVCode);
  }

  private void onSuccessFindCvCode(List<BaseFeed> cvList) {
    if (getView() == null) {
      return;
    }
    getView().csvDataLoaded(cvList);
  }

  private void onErrorFindCvCode(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
  }

  public void onSaveInstanceState(Bundle outState) {
    outState.putSerializable(CASH_KEY, cash);
  }

  public void onRestoreState(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      cash = (Cash) savedInstanceState.getSerializable(CASH_KEY);
    }
  }
}
