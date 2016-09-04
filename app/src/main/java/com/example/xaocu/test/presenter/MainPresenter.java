package com.example.xaocu.test.presenter;

import com.example.xaocu.test.Logger;
import com.example.xaocu.test.TestApp;
import com.example.xaocu.test.mvp.BaseMvpPresenter;
import com.example.xaocu.test.mvp.view.MainView;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class MainPresenter extends BaseMvpPresenter<MainView> {
  private static final String LOG_TAG = Logger.createTag(MainPresenter.class);

  public MainPresenter(MainView view) {
    super(view);
  }

  public void doSome() {
    TestApp.getManager().getService().getSome("")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessDoSome, this::onErrorDoSome);
  }

  private void onSuccessDoSome(Response<ResponseBody> response) {
    if (getView() == null) {
      return;
    }

    getView().getSomeSuccess();
  }

  private void onErrorDoSome(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
    if (getView() == null) {
      return;
    }
    getView().getSomeError(t);
  }
}
