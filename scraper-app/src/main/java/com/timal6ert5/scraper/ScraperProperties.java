package com.timal6ert5.scraper;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;

@Component
@ConfigurationProperties(prefix = "scraper.temp")
public class ScraperProperties {

  private static final Logger logger = LoggerFactory.getLogger(ScraperProperties.class);

  private String prefix;
  private String suffix;
  private String directory;

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  @PostConstruct
  public void init() {
    logger.debug("Configuration: {}", this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("prefix", prefix).add("suffix", suffix)
        .add("directory", directory).toString();
  }
}
