package com.timal6ert5.scraper.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timal6ert5.scraper.entity.PageScrapeRecord;

public interface PageScrapeRepository extends CrudRepository<PageScrapeRecord, Long> {

  List<PageScrapeRecord> findByUrl(String url);
}
