package com.timal6ert5.scraper.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PageStatsService {

  private Pattern pattern = Pattern.compile("[a-zA-Z]([a-z'-A-Z]*[a-zA-Z])?");

  public Map<String, Long> stats(String document) {
    List<String> docTokens = Arrays.asList(document.split("\\s"));

    Map<String, Long> collected = docTokens.stream().map(s -> s.replaceAll("[,.?!()\"]", ""))
        .map(s -> s.replaceAll("^'", "")).map(s -> s.replaceAll("'$", "")).map(String::toLowerCase)
        .filter(pattern.asPredicate()).filter(s -> s.length() < 256)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return collected;
  }
}
