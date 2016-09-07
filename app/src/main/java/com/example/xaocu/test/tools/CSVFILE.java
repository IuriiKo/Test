package com.example.xaocu.test.tools;

import com.example.xaocu.test.Logger;
import com.example.xaocu.test.R;
import com.example.xaocu.test.TestApp;
import com.example.xaocu.test.model.BaseFeed;
import com.example.xaocu.test.model.OnlineFeed;
import com.example.xaocu.test.model.RawFeed;
import com.example.xaocu.test.net.ServiceType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Iurii Kushyk on 05.09.2016.
 */
public class CSVFile {
  private static String LOG_TAG = Logger.createTag(CSVFile.class);

  public static List<BaseFeed> readRaw() {
    return read(TestApp.getContext().getResources().openRawResource(R.raw.datasets_codes), ServiceType.RAW);
  }

  public static List<BaseFeed> read(InputStream stream, @ServiceType int serviceType) {
    ObjectMapper mapper = new CsvMapper();
    List<BaseFeed> feeds = null;
    try {
      CsvSchema schema = CsvSchema.emptySchema().withHeader(); // use first row as header; otherwise defaults are fine
      MappingIterator<Map<String, String>> it = mapper.readerFor(Map.class)
          .with(schema)
          .readValues(stream);
      feeds = new ArrayList<>();
      while (it.hasNext()) {
        initFeed(it.next(), serviceType, feeds);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return feeds;
  }

  private static void initFeed(Map<String, String> map, @ServiceType int serviceType, List<BaseFeed> feeds) {
    switch (serviceType) {
      case ServiceType.QUANDL:
      case ServiceType.QUOTEMEDIA:
      case ServiceType.GOOGLE:
      case ServiceType.YAHOO:
        feeds.add(initOnlineFeed(map));
        break;
      case ServiceType.RAW:
        initRawFeed(map, feeds);
        break;
      default:
         new IllegalArgumentException("Wrong ServiceType = " + serviceType);
    }
  }

  private static void initRawFeed(Map<String, String> map, List<BaseFeed> feeds) {
    String firstAbbr = null;
    String secondAbbr = null;
    String firstName = null;
    String secondName = null;
    for (String s : map.keySet()) {
      if (firstAbbr == null) {
        firstAbbr = s.replace("WIKI/","");
        secondAbbr = map.get(s).replace("WIKI/","");
      } else {
        firstName = s;
        secondName = map.get(s);
      }
    }
    RawFeed firstFeed = new RawFeed();
    firstFeed.setAbbreviation(firstAbbr);
    firstFeed.setFullName(firstName);

    RawFeed secondFeed = new RawFeed();
    secondFeed.setAbbreviation(secondAbbr);
    secondFeed.setFullName(secondName);
    feeds.add(firstFeed);
    feeds.add(secondFeed);
  }

  private static BaseFeed initOnlineFeed(Map<String, String> map) {
    OnlineFeed feed = new OnlineFeed();
    feed.setOpen(map.get("Open"));
    feed.setHigh(map.get("High"));
    feed.setLow(map.get("Low"));
    feed.setClose(map.get("Close"));
    return feed;
  }
}
