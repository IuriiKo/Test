package com.example.xaocu.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class DelegateManager<D extends BaseDelegate> {
  private List<D> delegates = new ArrayList<>();

  public DelegateManager add(D delegate) {
    delegates.add(delegate);
    return this;
  }


}
