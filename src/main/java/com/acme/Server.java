package com.acme;

import net.codestory.http.WebServer;
import net.codestory.http.templating.Site;

public class Server {
  public static void main(String[] args) {
    new WebServer(routes -> {
      routes.get("/developers", Site.get().getData().get("developers"));
    }).start();
  }

}
