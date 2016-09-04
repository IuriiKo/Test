package com.example.xaocu.test.mvp.view;

import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.mvp.BaseMVPView;

import java.util.List;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface MainView extends BaseMVPView {
  void getSomeSuccess(List<SmallItem> comments);
  void getSomeError(Throwable t);
}
