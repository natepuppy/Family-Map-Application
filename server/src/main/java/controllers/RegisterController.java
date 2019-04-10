package controllers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import controllers.coders.DecoderJSON;
import controllers.coders.EncoderJSON;

import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

public class RegisterController extends ParentController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Register Controller");

        try {
            // Determine the HTTP request type (GET, POST, etc.)
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                // Get the request body and turn it into a request object.
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                RegisterRequest request = DecoderJSON.decodeRegister(reqData);

                // process request by using service
                RegisterService rs = new RegisterService();
                RegisterResult result = rs.register(request);

                // Check if there was an error
                if (result.getErrorMessage() == null) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                // write response body
                String json = EncoderJSON.encodeRegister(result);
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