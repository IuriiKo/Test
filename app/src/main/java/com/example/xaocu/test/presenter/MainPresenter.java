package com.example.xaocu.test.presenter;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.xaocu.test.Logger;
import com.example.xaocu.test.TestApp;
import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.model.Feed;
import com.example.xaocu.test.mvp.BaseMvpPresenter;
import com.example.xaocu.test.mvp.view.MainView;
import com.example.xaocu.test.tools.CSVFile;

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
  private Subscription subscription;
  private Subscription subscriptionGetCVCode;
  private String oldDataSet;

  public MainPresenter(MainView view) {
    super(view);
  }

  public void doSome(@NonNull String datasetCode) {
    if (datasetCode.equalsIgnoreCase(oldDataSet)) {
      return;
    }
    if (subscription != null) {
      subscription.unsubscribe();
    }
    oldDataSet = datasetCode;
    subscription = TestApp.getManager().getService().getSome(datasetCode)
        .flatMap(feed->Observable.zip(Observable.from(feed.getDataset().getData()).map(SmallItem::new).toList(), Observable.just(feed),Pair::new))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessDoSome, this::onErrorDoSome);
    addSubscription(subscription);
  }

  private void onSuccessDoSome(Pair<List<SmallItem>,Feed> data) {
    if (getView() == null) {
      return;
    }

    getView().getSomeSuccess(data);
  }

  private void onErrorDoSome(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
    if (getView() == null) {
      return;
    }
    getView().getSomeError(t);
  }

  public void findCvCode() {
    if (subscriptionGetCVCode != null) {
      subscriptionGetCVCode.unsubscribe();
    }
    subscriptionGetCVCode = Observable.defer(() -> Observable.just(CSVFile.read()))
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessfindCvCode, this::onErrorFindCvCode);
    addSubscription(subscriptionGetCVCode);
  }

  private void onSuccessfindCvCode(List<Pair<String,String>> cvList) {
    if (getView() == null) {
      return;
    }
    getView().loadCSV(cvList);
  }

  private void onErrorFindCvCode(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
  }
}
