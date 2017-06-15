package com.timal6ert5.scraper.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PageStatsServiceTest {

  private PageStatsService service;

  @Before
  public void setup() {
    service = new PageStatsService();
  }

  @Test
  public void testStatsRemovesNumbersAndCurrency() {
    String document = "Sold 7 at $3.00.";
    Map<String, Long> stats = service.stats(document);

    assertThat(stats.size(), is(2));
    assertThat(stats.get("sold"), is(1L));
    assertThat(stats.get("at"), is(1L));
  }

  @Test
  public void testStatsRemovesQuotes() {
    String document = "Shouldn't 'count any' quotes.";
    Map<String, Long> stats = service.stats(document);

    assertThat(stats.size(), is(4));
    assertThat(stats.get("shouldn't"), is(1L));
    assertThat(stats.get("count"), is(1L));
    assertThat(stats.get("any"), is(1L));
    assertThat(stats.get("quotes"), is(1L));
  }

  @Test
  public void testStatsRemovesParenthesis() {
    String document = "Should (not) count parenthesis.";
    Map<String, Long> stats = service.stats(document);

    assertThat(stats.size(), is(4));
    assertThat(stats.get("should"), is(1L));
    assertThat(stats.get("not"), is(1L));
    assertThat(stats.get("count"), is(1L));
    assertThat(stats.get("parenthesis"), is(1L));
  }

  @Test
  public void testStatsCountsContractions() {
    String document = "I won't quite!";
    Map<String, Long> stats = service.stats(document);

    assertThat(stats.size(), is(3));
    assertThat(stats.get("i"), is(1L));
    assertThat(stats.get("won't"), is(1L));
    assertThat(stats.get("quite"), is(1L));
  }

  @Test
  public void testStatsBasic() {
    String document = "Row row row your boat, gently down the stream.";
    Map<String, Long> stats = service.stats(document);

    assertThat(stats.size(), is(7));
    assertThat(stats.get("row"), is(3L));
    assertThat(stats.get("your"), is(1L));
    assertThat(stats.get("boat"), is(1L));
    assertThat(stats.get("gently"), is(1L));
    assertThat(stats.get("down"), is(1L));
    assertThat(stats.get("the"), is(1L));
    assertThat(stats.get("stream"), is(1L));
  }
}
