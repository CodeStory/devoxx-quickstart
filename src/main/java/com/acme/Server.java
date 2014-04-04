package com.acme;

import static java.util.stream.Stream.*;
import static net.codestory.http.convert.TypeConvert.*;

import java.util.*;

import net.codestory.http.*;
import net.codestory.http.templating.*;

public class Server {
  public static void main(String[] args) {
    new WebServer(routes -> routes
        .get("/basket?emails=:emails", (context, emails) -> {
          String[] listEmails = emails.split("[,]");

          Basket basket = new Basket();

          for (String email : listEmails) {
            Optional<Developer> developer = findDeveloper(email);
            if (developer.isPresent()) {
              String[] tags = developer.get().tags;

              basket.front += front(tags);
              basket.back += back(tags);
              basket.database += database(tags);
              basket.test += test(tags);
              basket.hipster += hipster(tags);
              basket.sum += developer.get().price;
            }
          }

          return basket;
        })
    ).start();
  }

  static Optional<Developer> findDeveloper(String email) {
    Developer[] developers = convertValue(Site.get().getData().get("developers"), Developer[].class);
    return of(developers).filter(developer -> developer.email.equals(email)).findFirst();
  }

  static long front(String[] tags) {
    return of(tags).filter(tag -> tag.equals("Javascript") || tag.equals("Jsp") || tag.equals("CoffeeScript")).count();
  }

  static long back(String[] tags) {
    return of(tags).filter(tag -> tag.equals("Java") || tag.equals("Spring") || tag.equals("Hibernate") || tag.equals("Node")).count();
  }

  static long database(String[] tags) {
    return of(tags).filter(tag -> tag.equals("Hibernate")).count();
  }

  static long test(String[] tags) {
    return of(tags).filter(tag -> tag.equals("Java") || tag.equals("Javascript")).count();
  }

  static long hipster(String[] tags) {
    long bonus = of(tags).filter(tag -> tag.equals("Javascript") || tag.equals("Web") || tag.equals("Java")).count();
    long malus = of(tags).filter(tag -> !(tag.equals("Javascript") || tag.equals("Web") || tag.equals("Java"))).count();
    return bonus - malus;
  }

  static class Developer {
    String email;
    String[] tags;
    int price;
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
