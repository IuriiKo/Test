package com.example.xaocu.test.mvp.view;

import com.example.xaocu.test.mvp.BaseMVPView;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface MainView extends BaseMVPView {
  void getSomeSuccess();
  void getSomeError(Throwable t);
}
