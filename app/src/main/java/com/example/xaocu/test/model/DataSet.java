package com.example.xaocu.test.model;

import java.util.List;

/**
 * Created by Iurii Kushyk on 05.09.2016.
 */
public class Dataset {
private List<String> column_names;
private List<List<Object>> data;

  public List<String> getColumn_names() {
    return column_names;
  }

  public void setColumn_names(List<String> column_names) {
    this.column_names = column_names;
  }

  public List<List<Object>> getData() {
    return data;
  }

  public void setData(List<List<Object>> data) {
    this.data = data;
  }
}
