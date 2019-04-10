package controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import controllers.coders.EncoderJSON;
import dao.DataAccessException;
import request.EventRequestAll;
import request.EventRequestOne;
import result.EventResultAll;
import result.EventResultOne;
import service.EventService;

public class EventController extends ParentController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Determine the HTTP request type (GET, POST, etc.)
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");
                    ArrayList<String> list = getSingleUriComponents(exchange.getRequestURI().toString(), "event");

                    if (list.size() == 1) getOneEvent(authToken, list, exchange);
                    else getAllEvents(authToken, exchange);
                }
            }
        }
        catch (Exception e) { // Internal Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String message = "{ \"message\":\"Internal server error\"}";
            writeResponseBody(message, exchange.getResponseBody());
            e.printStackTrace();
        }
    }

    private void getOneEvent(String authToken, ArrayList<String> list, HttpExchange exchange) throws Exception {
        EventService es = new EventService();
        EventResultOne resultOne = new EventResultOne();
        EventRequestOne request = new EventRequestOne();
        request.setAuthToken(authToken.toLowerCase());
        request.setEventID(list.get(0));
        resultOne = es.oneEvent(request);

        // Check if there was an error
        if (resultOne.getErrorMessage() == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String json = EncoderJSON.encodeEventOne(resultOne);
            writeResponseBody(json, exchange.getResponseBody());
        }
        else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String json = "{ \"message\":\"" + resultOne.getErrorMessage() + "\"}";
            writeResponseBody(json, exchange.getResponseBody());
        }
    }

    private void getAllEvents(String authToken,HttpExchange exchange) throws Exception {
        EventService es = new EventService();
        EventResultAll resultAll = new EventResultAll();
        EventRequestAll request = new EventRequestAll();
        request.setAuthToken(authToken.toLowerCase());
        resultAll = es.allEvents(request);

        // Check if there was an error
        if (resultAll.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

        // write response body
        String json = EncoderJSON.encodeEventAll(resultAll);
        writeResponseBody(json, exchange.getResponseBody());
    }
}






//                    if (list.size() == 1) {  // specific user
//                        EventResultOne resultOne = new EventResultOne();
//                        EventRequestOne request = new EventRequestOne();
//                        request.setAuthToken(authToken.toLowerCase());
//                        request.setEventID(list.get(0));
//                        resultOne = es.oneEvent(request);
//
//                        // Check if there was an error
//                        if (resultOne.getErrorMessage() == null) {
//                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                            String json = EncoderJSON.encodeEventOne(resultOne);
//                            writeResponseBody(json, exchange.getResponseBody());
//                        }
//                        else {
//                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//                            String json = "{ \"message\":\"" + resultOne.getErrorMessage() + "\"}";
//                            writeResponseBody(json, exchange.getResponseBody());
//                        }
//                    }
//                    else {  // all users
//                        EventResultAll resultAll = new EventResultAll();
//                        EventRequestAll request = new EventRequestAll();
//                        request.setAuthToken(authToken.toLowerCase());
//                        resultAll = es.allEvents(request);
//
//                        // Check if there was an error
//                        if (resultAll.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//
//                        // write response body
//                        String json = EncoderJSON.encodeEventAll(resultAll);
//                        writeResponseBody(json, exchange.getResponseBody());
//                    }

