package com.example.xaocu.test.mvp.view;

import com.example.xaocu.test.items.OnlineFeedItem;
import com.example.xaocu.test.model.BaseFeed;
import com.example.xaocu.test.mvp.BaseMVPView;

import java.util.List;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface MainView extends BaseMVPView {
  void successDataLoading(List<OnlineFeedItem> data);
  void errorDataLoading(Throwable t);
  void csvDataLoaded(List<BaseFeed> csvList);
}
