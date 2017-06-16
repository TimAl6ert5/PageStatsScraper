package com.timal6ert5.scraper;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.timal6ert5.scraper.entity.PageScrapeRecord;
import com.timal6ert5.scraper.service.PageStatsService;
import com.timal6ert5.scraper.service.RecordDumpService;
import com.timal6ert5.scraper.service.ScraperService;
import com.timal6ert5.scraper.service.StatsRecordingService;

@SpringBootApplication
public class ScraperApplication implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ScraperApplication.class);

  @Autowired
  private ScraperService scraperService;
  @Autowired
  private PageStatsService pageStatsService;
  @Autowired
  private StatsRecordingService statsRecordingService;
  @Autowired
  private RecordDumpService recordDumpService;

  @Override
  public void run(String... args) throws Exception {
    if (args.length != 1) {
      System.out.println("Usage: <jar> <url>");
      System.exit(1);
    }

    String scrapeUrl = args[0];
    logger.info("Scrape URL: {}", scrapeUrl);

    String scraped = scraperService.scrape(scrapeUrl);
    logger.debug("Scrape Text: {}", scraped);

    Map<String, Long> wordstats = pageStatsService.stats(scraped);
    logger.debug("Scrape Words: {}", wordstats);
    logger.info("Scrape word count: {}", wordstats.size());

    PageScrapeRecord record = statsRecordingService.record(scrapeUrl, wordstats);
    logger.info("Scrape Record ID: {}", record.getId());

    File file = recordDumpService.fileDump(record);
    logger.info("Scrape File: {}", file);
  }

  public static void main(String[] args) {
    SpringApplication.run(ScraperApplication.class, args);
  }
}
