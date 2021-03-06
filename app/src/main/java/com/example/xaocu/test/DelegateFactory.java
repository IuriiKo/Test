package com.example.xaocu.test;

import android.support.annotation.Nullable;

import com.example.xaocu.test.delegates.BaseDelegate;
import com.example.xaocu.test.delegates.OnlineFeedDelegate;
import com.example.xaocu.test.mvp.DelegateType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class DelegateFactory {
  private Map<Integer, BaseDelegate> cashDelegate = new HashMap<>();

  public BaseDelegate getDelegate(@DelegateType int type, @Nullable OnDelegateClickListener clickListener) {
    BaseDelegate delegate;
    if (cashDelegate.containsKey(type)) {
      return cashDelegate.get(type);
    } else {
      switch (type) {
        case DelegateType.onlineFeedItemType:
          delegate = new OnlineFeedDelegate(clickListener);
          break;
        default:
          throw new IllegalArgumentException("DelegateFactory don't contain a delegate with type = " + type);
      }
    }

    cashDelegate.put(type, delegate);
    return delegate;
  }
}
