package com.example.xaocu.test;

import com.example.xaocu.test.mvp.DelegateType;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class BaseItem {
private @DelegateType int itemType;

  public BaseItem(@DelegateType int itemType) {
    this.itemType = itemType;
  }

  @DelegateType
  public int getItemType() {
    return itemType;
  }
}
