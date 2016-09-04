package com.example.xaocu.test.items;

import com.example.xaocu.test.BaseItem;
import com.example.xaocu.test.model.Comment;
import com.example.xaocu.test.mvp.DelegateType;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class SmallItem extends BaseItem {
  private Comment comment;
  public SmallItem(Comment comment) {
    super(DelegateType.smallItemType);
    this.comment = comment;
  }

  public Comment getComment() {
    return comment;
  }
}
