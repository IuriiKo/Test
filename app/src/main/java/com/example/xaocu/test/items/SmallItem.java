package com.example.xaocu.test.items;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.mvp.DelegateType;

import java.util.List;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class SmallItem extends BaseItem {
  List objects;

  public SmallItem(List objects) {
    super(DelegateType.smallItemType);
    this.objects = objects;
  }

  public List getObjects() {
    return objects;
  }
}
