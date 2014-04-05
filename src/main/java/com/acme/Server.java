package com.acme;

import static java.util.stream.Stream.*;
import static net.codestory.http.convert.TypeConvert.*;

import java.util.*;
import java.util.stream.*;

import net.codestory.http.*;
import net.codestory.http.templating.*;

public class Server {
  public static void main(String[] args) {
    new WebServer(routes -> routes
        .get("/basket?emails=:emails", (context, emails) -> {
          Basket basket = new Basket();

          Stream.of(emails.split(",")).map(Server::findDeveloper).filter(dev -> dev != null).forEach(developer -> {
            String[] tags = developer.tags;
            basket.front += count("front", tags);
            basket.back += count("back", tags);
            basket.database += count("database", tags);
            basket.test += count("test", tags);
            basket.hipster += count("hipster", tags);
            basket.sum += developer.price;
          });

          return basket;
        })
    ).start();
  }

  static Developer findDeveloper(String email) {
    Developer[] developers = convertValue(Site.get().getData().get("developers"), Developer[].class);

    return of(developers).filter(developer -> developer.email.equals(email)).findFirst().orElse(null);
  }

  private static long count(String domain, String[] tags) {
    Map<String, List<String>> tagsPerDomain = (Map<String, List<String>>) Site.get().getData().get("tags");
    return of(tags).filter((t) -> tagsPerDomain.get(domain).contains(t)).count();
  }

  static class Developer {
    String email;
    String[] tags;
    long price;
  }

  static class Basket {
    long sum;
    long front;
    long back;
    long database;
    long test;
    long hipster;
  }
}
