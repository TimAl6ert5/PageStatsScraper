package com.timal6ert5.scraper.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timal6ert5.scraper.entity.PageScrapeRecord;
import com.timal6ert5.scraper.repository.PageScrapeRepository;

@Service
public class StatsRecordingService {

  @Autowired
  private PageScrapeRepository repository;

  public PageScrapeRecord record(String url, Map<String, Long> wordstats) {
    PageScrapeRecord record = new PageScrapeRecord();
    record.setUrl(url);
    record.setWordstats(wordstats);
    return repository.save(record);
  }
}
