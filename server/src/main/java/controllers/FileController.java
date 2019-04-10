package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.*;
import java.net.HttpURLConnection;

public class FileController extends ParentController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // create the proper url
        String url = exchange.getRequestURI().toString();
        System.out.println(url);
        if (url.equals("/")) url = "/index.html";
        if (!url.equals("/index.html") && !url.equals("/css/main.css") && !url.equals("/favicon.ico")) {
            url = "/HTML/404.html";
        }

        // add the correct folder to the url
        String filePathStr = "web" + url;
        Path filePath = FileSystems.getDefault().getPath(filePathStr);

        // send back
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Files.copy(filePath, exchange.getResponseBody());
        exchange.getResponseBody().close();
    }
}
