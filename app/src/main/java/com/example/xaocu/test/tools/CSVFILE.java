package com.example.xaocu.test.tools;

import android.util.Pair;

import com.example.xaocu.test.R;
import com.example.xaocu.test.TestApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iurii Kushyk on 05.09.2016.
 */
public class CSVFile {
  public static List<Pair<String,String>> read(){
    InputStream inputStream = TestApp.getContext().getResources().openRawResource(R.raw.datasets_codes);
    List<Pair<String,String>> resultList = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      String csvLine;
      while ((csvLine = reader.readLine()) != null) {
        String[] row = csvLine.split(",\"");
        if (row.length == 2) {
          row[0] = row[0].replaceAll("WIKI/","");
          row[1] = row[1].replaceAll("\"", "");
          Pair<String,String> pair =new Pair<>(row[0], row[1]);
          resultList.add(pair);
        }
      }
    }
    catch (IOException ex) {
      throw new RuntimeException("Error in reading CSV file: "+ex);
    }
    finally {
      try {
        inputStream.close();
      }
      catch (IOException e) {
        throw new RuntimeException("Error while closing input stream: "+e);
      }
    }
    return resultList;
  }
}
