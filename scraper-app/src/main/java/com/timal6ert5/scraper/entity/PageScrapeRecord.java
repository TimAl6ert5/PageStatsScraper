package com.timal6ert5.scraper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

@Entity
public class PageScrapeRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "scrape_url", nullable = false, length = 2083)
  private String url;

  @ElementCollection(fetch = FetchType.LAZY)
  @MapKeyColumn(name = "stats_token")
  @Column(name = "stats_count", nullable = false)
  private Map<String, Long> wordstats = new HashMap<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Map<String, Long> getWordstats() {
    return wordstats;
  }

  public void setWordstats(Map<String, Long> wordstats) {
    this.wordstats = wordstats;
  }

}
