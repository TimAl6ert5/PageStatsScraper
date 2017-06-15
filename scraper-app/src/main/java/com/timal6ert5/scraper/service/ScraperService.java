package com.timal6ert5.scraper.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

  public String scrape(String url) throws IOException {
    Document doc = Jsoup.connect(url).get();
    return doc.text();
  }
}
