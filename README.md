# PageStatsScraper

The purpose of this application is to accept a URL, scrape the page, determine all words that aren't part of the HTML, count the frequency of each word, save the results to a database and dump the record to a JSON formatted file in the working directory.

## Database Schema
This application is built to use a MySQL database.  The database consists of two tables.

CREATE TABLE `page_scrape_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scrape_url` varchar(2083) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `page_scrape_record_wordstats` (
  `page_scrape_record_id` bigint(20) NOT NULL,
  `stats_count` bigint(20) NOT NULL,
  `stats_token` varchar(255) NOT NULL,
  PRIMARY KEY (`page_scrape_record_id`,`stats_token`),
  CONSTRAINT `FK3ydnkeeglr9sxduhcq2l2pfrb` FOREIGN KEY (`page_scrape_record_id`) REFERENCES `page_scrape_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

## Design Review

This application is built using Spring Boot as the main framework.

The application is designed to run from the command line.

The application uses a ScraperService to get the data from a given URL.
This ScraperService uses a 3rd party library called JSoup (https://jsoup.org/).
The choice of this library was to take advantage of the built in capability to connect to a URL, get the content and convert the HTML to plain text.
An alternative to this might be to use Apache Tika (https://tika.apache.org/).

Improvements in the scraper service would include:
* better handle any bad URL's the user may provide
* handle any errors connecting and retrieving the page content and report information to the user.

Once the content is collected from a URL, the application uses the PageStatsService to build a Map of words and frequency.  This service takes advantage of Java 8 stream API to scrub the input and group/count the data as required.
Improvements in this service would include:
* take advantage of parallel streams.
* analyze the complexity of filtering/scrubing the data and improve efficiency

The persistence layer uses Spring Data with Hibernate connected to MySQL database.
The application uses StatsRecordingService to connect to the PageScrapeRepository and save a record of data, return the result.
Improvements in this service would include:
* add a date/time stamp to the `page_scrape_record` to show when the page was accessed (web content changes).
* review the word length limit (currently 256)
* support multiple languages
* translate languages

The record file dump service, RecordDumpService, uses Gson conversion.
Improvements in this service would include:
* user selectable dump file location
* add configuration to dump only words that exceed a count threshold


## To Build and Run

This application uses Gradle as the build tool.

Windows platforms, run `gradlew.bat clean build`
Mac/Linux platforms, run `./gradlew clean build`

This will produce a jar file in `<project_root>\scraper-app\build\libs` called `scraper-app.jar`.
Copy this jar file and the `application.properties` file from `<project_root>\example` folder to the desired folder location.
Edit the application.properties file for your required datasoure URL, username and password.

You can generate the database schema manually, or use the property `spring.jpa.generate-ddl=true` in the `application.properties` file to have the schema automatically generated.

Example to run the application, use the command `java -jar scraper-app.jar http://www.yahoo.com/`

