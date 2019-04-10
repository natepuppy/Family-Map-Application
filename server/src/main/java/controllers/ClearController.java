package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import controllers.coders.EncoderJSON;
import result.ClearResult;
import service.ClearService;

public class ClearController extends ParentController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Determine the HTTP request type (GET, POST, etc.)
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                // process request by using service
                ClearService rs = new ClearService();
                ClearResult result = rs.clear();

                // Check if there was an error
                if (result.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                // write response body
                String json = EncoderJSON.encodeClear(result);
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
