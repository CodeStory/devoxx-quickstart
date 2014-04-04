package com.acme;

import net.codestory.http.*;
import net.codestory.http.convert.*;
import net.codestory.http.templating.*;

public class Server {
  public static void main(String[] args) {
    new WebServer(routes -> routes
        .get("/basket?emails=:emails", (context, emails) -> {
          String[] listEmails = emails.split("[,]");

          Basket basket = new Basket();

          for (String email : listEmails) {
            Developer developer = findDeveloper(email);
            if (developer != null) {
              basket.front += front(developer.tags);
              basket.back += back(developer.tags);
              basket.database += database(developer.tags);
              basket.test += test(developer.tags);
              basket.hipster += hipster(developer.tags);
              basket.sum += developer.price;
            }
          }

          return basket;
        })
    ).start();
  }

  private static Developer findDeveloper(String email) {
    Developer[] developers = TypeConvert.convertValue(Site.get().getData().get("developers"), Developer[].class);
    for (Developer developer : developers) {
      if (developer.email.equals(email)) {
        return developer;
      }
    }

    return null;
  }

  private static int front(String[] tags) {
    int score = 0;
    for (String tag : tags) {
      if (tag.equals("Javascript")) {
        score++;
      }
      if (tag.equals("Jsp")) {
        score++;
      }
      if (tag.equals("CoffeeScript")) {
        score++;
      }
    }
    return score;
  }

  private static int back(String[] tags) {
    int score = 0;
    for (String tag : tags) {
      if (tag.equals("Java")) {
        score++;
      }
      if (tag.equals("Spring")) {
        score++;
      }
      if (tag.equals("Hibernate")) {
        score++;
      }
      if (tag.equals("Node")) {
        score++;
      }
    }
    return score;
  }

  private static int database(String[] tags) {
    int score = 0;
    for (String tag : tags) {
      if (tag.equals("Javascript")) {
        score++;
      }
    }
    return score;
  }

  private static int test(String[] tags) {
    int score = 0;
    for (String tag : tags) {
      if (tag.equals("Javascript")) {
        score++;
      }
    }
    return score;
  }

  private static int hipster(String[] tags) {
    int score = 0;
    for (String tag : tags) {
      if (tag.equals("Javascript") || tag.equals("Web") || tag.equals("Java")) {
        score++;
      } else {
        score--;
      }
    }
    return score;
  }

  public static class Developer {
    String email;
    String[] tags;
    int price;
  }

  public static class Basket {
    int sum;
    int front;
    int back;
    int database;
    int test;
    int hipster;
  }
}
