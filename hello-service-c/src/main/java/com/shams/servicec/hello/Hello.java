package com.shams.servicec.hello;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Hello {
  public static void main(String[] args) throws Exception {
    String env = System.getenv("env");
    System.out.println("Starting main program with env = " + env);
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/", new MyHandler(env));
    server.createContext("/ping", new PingHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
    System.out.println("Started main program .... ");
  }

  static class PingHandler implements HttpHandler {
    public PingHandler() {
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
      String response = "ok";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
      os.close();
    }
  }

  static class MyHandler implements HttpHandler {

    private String env;
    public MyHandler(String env) {
      this.env = env;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
      System.out.println("Handling request from " + t.getRemoteAddress().getHostString());
      String response = "This is the response from service-c from env = " + this.env;
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
      os.close();
    }
  }
}
