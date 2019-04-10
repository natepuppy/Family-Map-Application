package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import controllers.coders.EncoderJSON;
import request.FillRequest;
import result.FillResult;
import service.FillService;

public class FillController extends ParentController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Determine the HTTP request type (GET, POST, etc.)
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                // Get the parameters from URI
                ArrayList<String> list = getUriComponents(exchange.getRequestURI().toString());
                String userName;
                int generations;

                if (list.size() == 1) {
                    userName = list.get(0);
                    generations = 4;
                }
                else { // This is executed when the api call contains generations as well
                    userName = list.get(0);
                    generations = Integer.parseInt(list.get(1));
                    if (generations < 0) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String json = "{ \"message\":\"Generations parameter must not be negative\"}";
                        writeResponseBody(json, exchange.getResponseBody());
                        return;
                    }
                }

                // create object with those parameters and send to service
                FillRequest request = new FillRequest(userName, generations);
                FillService fs = new FillService();
                FillResult result = fs.fill(request);

                // Check if there was an error
                if (result.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                // write response body
                String json = EncoderJSON.encodeFill(result);
                writeResponseBody(json, exchange.getResponseBody());
            }
        } catch (Exception e) { // Internal Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String message = "{ \"message\":\"Internal server error\"}";
            writeResponseBody(message, exchange.getResponseBody());
            e.printStackTrace();
        }
    }
}
