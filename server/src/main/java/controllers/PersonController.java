package controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import controllers.coders.EncoderJSON;
import request.PersonRequestAll;
import request.PersonRequestOne;
import result.PersonResultAll;
import result.PersonResultOne;
import service.PersonService;


public class PersonController extends ParentController implements HttpHandler {
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
                    PersonService ps = new PersonService();
                    ArrayList<String> list = getSingleUriComponents(exchange.getRequestURI().toString(), "person");

                    if (list.size() == 1) {
                        PersonResultOne resultOne = new PersonResultOne();
                        PersonRequestOne request = new PersonRequestOne();
                        request.setAuthToken(authToken.toLowerCase());
                        request.setPersonID(list.get(0));
                        resultOne = ps.onePerson(request);

                        // Check if there was an error
                        if (resultOne.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                        // write response body
                        String json = EncoderJSON.encodePersonOne(resultOne);
                        writeResponseBody(json, exchange.getResponseBody());
                    }
                    else {
                        PersonResultAll resultAll = new PersonResultAll();
                        PersonRequestAll request = new PersonRequestAll();
                        request.setAuthToken(authToken.toLowerCase());
                        resultAll = ps.allPersons(request);

                        // Check if there was an error
                        if (resultAll.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                        // write response body
                        String json = EncoderJSON.encodePersonAll(resultAll);
                        writeResponseBody(json, exchange.getResponseBody());
                    }
                }
            }
        }
        catch (Exception e) {  // internal server error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String message = "{ \"message\":\"Internal server error\"}";
            writeResponseBody(message, exchange.getResponseBody());
            e.printStackTrace();
        }
    }
}
