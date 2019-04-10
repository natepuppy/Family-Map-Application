package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import controllers.coders.DecoderJSON;
import controllers.coders.EncoderJSON;
import request.LoadRequest;
import result.LoadResult;
import service.ClearService;
import service.LoadService;

public class LoadController extends ParentController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Determine the HTTP request type (GET, POST, etc.)
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                // Get the request body and turn it into a request object.
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                LoadRequest request = DecoderJSON.decodeLoad(reqData);

                // delete everything from database
                ClearService cs = new ClearService();
                cs.clear();

                // process request by using service
                LoadService ls = new LoadService();
                LoadResult result = ls.load(request);

                // Check if there was an error
                if (result.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                // write response body
                String json = EncoderJSON.encodeLoad(result);
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
