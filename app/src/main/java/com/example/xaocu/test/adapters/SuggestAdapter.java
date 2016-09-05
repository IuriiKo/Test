package com.example.xaocu.test.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iurii Kushyk on 05.09.2016.
 */
public class SuggestAdapter  extends android.widget.BaseAdapter implements Filterable {
  private List<Pair<String, String>> tickers = new ArrayList<>();
  private List<Pair<String, String>> origin = new ArrayList<>();
  private CharSequence lastSearch = "";

  public CharSequence getLastSearch() {
    return lastSearch;
  }

  public void setTickers(List<Pair<String, String>> tickers) {
    this.tickers = tickers;
  }

  @Override
  public int getCount() {
    return origin.size();
  }

  @Override
  public Object getItem(int i) {
    return origin.get(i);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    String text = origin.get(i).first;
    if (view == null) {
      view = getLayoutInflater(viewGroup).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
    }
    ((TextView)view).setText(text);
    return view;
  }

  @Override
  public Filter getFilter() {
    return new SuggestFilter();
  }


  public class SuggestFilter extends Filter{
    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
      List<Pair<String, String>> resultsList = new ArrayList<>();
      if (charSequence == null || charSequence.equals(lastSearch)) {
        return null;
      }
      lastSearch = charSequence;
      for (Pair<String, String> ticker : tickers) {
        if (ticker.first.toLowerCase().contains(charSequence.toString().toLowerCase())) {
          resultsList.add(ticker);
        }
      }
      FilterResults results = new FilterResults();
      results.values = resultsList;
      results.count = 1;
      return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
      if (filterResults == null) {
        return;
      }
      origin.clear();
      notifyDataSetChanged();
      List<Pair<String, String>> values = (List<Pair<String, String>>) filterResults.values;
      origin.addAll(values);
    }
  }

  private LayoutInflater getLayoutInflater(View view) {
    return LayoutInflater.from(view.getContext());
  }
}
