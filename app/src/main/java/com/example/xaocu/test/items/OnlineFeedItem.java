package com.example.xaocu.test.items;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.model.BaseFeed;
import com.example.xaocu.test.mvp.DelegateType;

import java.io.Serializable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class OnlineFeedItem extends BaseItem implements Serializable {
  BaseFeed feeds;

  public OnlineFeedItem(BaseFeed feeds) {
    super(DelegateType.onlineFeedItemType);
    this.feeds = feeds;
  }

  public BaseFeed getFeeds() {
    return feeds;
  }
}
