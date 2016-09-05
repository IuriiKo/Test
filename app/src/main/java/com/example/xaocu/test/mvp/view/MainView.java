package com.example.xaocu.test.mvp.view;

import android.util.Pair;

import com.example.xaocu.test.items.SmallItem;
import com.example.xaocu.test.model.Feed;
import com.example.xaocu.test.mvp.BaseMVPView;

import java.util.List;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface MainView extends BaseMVPView {
  void getSomeSuccess(Pair<List<SmallItem>,Feed> data);
  void getSomeError(Throwable t);
  void loadCSV(List<Pair<String, String>> csvList);
}
