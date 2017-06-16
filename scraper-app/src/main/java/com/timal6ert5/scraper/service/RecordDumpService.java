package com.timal6ert5.scraper.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timal6ert5.scraper.ScraperProperties;
import com.timal6ert5.scraper.entity.PageScrapeRecord;

@Service
public class RecordDumpService {

  private static final Logger logger = LoggerFactory.getLogger(RecordDumpService.class);

  @Autowired
  private ScraperProperties properties;

  private Gson gson;

  public File fileDump(PageScrapeRecord record) {
    gson = new GsonBuilder().setPrettyPrinting().create();
    String jsonRecord = gson.toJson(record);

    File file = null;
    try {
      file = tempFile();
    } catch (IOException e) {
      logger.warn("Error creating file: {}", e.getMessage());
    }

    if (file != null) {
      try (FileWriter fileWriter = new FileWriter(file)) {
        fileWriter.write(jsonRecord);
      } catch (IOException e) {
        logger.warn("Error writing record to file: {}", e.getMessage());
      }
    }

    return file;
  }

  private File tempFile() throws IOException {
    File folder = new File(System.getProperty("user.dir"));
    return File.createTempFile(properties.getPrefix(), properties.getSuffix(), folder);
  }
}
