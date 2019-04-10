package com.example.familymapapplication;

import java.io.*;
import java.net.*;

import controllers.coders.DecoderJSON;
import controllers.coders.EncoderJSON;
import request.LoginRequest;
import request.PersonRequestAll;
import request.RegisterRequest;
import result.LoginResult;
import result.PersonResultAll;
import result.RegisterResult;
import result.EventResultAll;
import request.EventRequestAll;


/*
	The Client class shows how to call a web API operation from
	a Java program.  This is typical of how your Android client
	app will call the web API operations of your server.
*/
public class Client {
//    static String serverHost;
//    static String serverPort;

    // This method sends a POST request /request on the server
    public static RegisterResult register(String serverHost, String serverPort, RegisterRequest request) {
        try {
            // Create a URL
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            // Connect to the server and set request
            http.connect();
            String reqData = EncoderJSON.encodeRegister(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            // Check that the response is 200
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Route successfully claimed.");
                InputStream responseBody = http.getInputStream();
                String json = readString(responseBody);  // FIXME!!!!!
                RegisterResult result = DecoderJSON.decodeRegisterResult(json);

                return result;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                // TODO: Do something
                http.getErrorStream();
                String json = readString(http.getErrorStream());
                RegisterResult result = DecoderJSON.decodeRegisterResult(json);

                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        RegisterResult result = new RegisterResult();
        result.setErrorMessage("Wrong host or port number");
        return result;
    }

    // This method sends a POST request /request on the server
    public static LoginResult login(String serverHost, String serverPort, LoginRequest request) {
        try {
            // Create a URL
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            // Connect to the server and set request
            http.connect();
            String reqData = EncoderJSON.encodeLogin(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            // Check that the response is 200
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Route successfully claimed.");
                InputStream responseBody = http.getInputStream();
                String json = readString(responseBody);  // FIXME!!!!!
                LoginResult result = DecoderJSON.decodeLoginResult(json);

                return result;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                // TODO: Do something
                http.getErrorStream();
                String json = readString(http.getErrorStream());
                LoginResult result = DecoderJSON.decodeLoginResult(json);

                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        LoginResult result = new LoginResult();
        result.setErrorMessage("Wrong host or port number");
        return result;
    }



    public static PersonResultAll personAll(String serverHost, String serverPort, PersonRequestAll request) {
        try {
            // Create a URL
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", request.getAuthToken());

            // Connect to the server and set request
            http.connect();

            // Check that the response is 200
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = http.getInputStream();
                String json = readString(responseBody);  // FIXME!!!!!
                PersonResultAll result = DecoderJSON.decodePersonAllResult(json);

                return result;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                // TODO: Do something
                http.getErrorStream();
                String json = readString(http.getErrorStream());
                PersonResultAll result = DecoderJSON.decodePersonAllResult(json);

                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        PersonResultAll result = new PersonResultAll();
        result.setErrorMessage("Wrong host or port number");
        return result;
    }



    public static EventResultAll eventAll(String serverHost, String serverPort, EventRequestAll request) {
        try {
            // Create a URL
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", request.getAuthToken());

            // Connect to the server and set request
            http.connect();

            // Check that the response is 200
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = http.getInputStream();
                String json = readString(responseBody);  // FIXME!!!!!
                EventResultAll result = DecoderJSON.decodeEventAllResult(json);

                return result;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                // TODO: Do something
                http.getErrorStream();
                String json = readString(http.getErrorStream());
                EventResultAll result = DecoderJSON.decodeEventAllResult(json);

                return result;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        EventResultAll result = new EventResultAll();
        result.setErrorMessage("Wrong host or port number");
        return result;
    }

//
//
//
//    public static String getServerHost() {
//        return serverHost;
//    }
//
//    public static void setServerHost(String serverHost) {
//        Client.serverHost = serverHost;
//    }
//
//    public static String getServerPort() {
//        return serverPort;
//    }
//
//    public static void setServerPort(String serverPort) {
//        Client.serverPort = serverPort;
//    }
//
//
//
//
//
//
//


    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    protected static String readString(InputStream is) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            InputStreamReader sr = new InputStreamReader(is);
            char[] buf = new char[1024];
            int len;
            while ((len = sr.read(buf)) > 0) {   // FIXME this line throws an error
                sb.append(buf, 0, len);
            }
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}


    /*
        The readString method shows how to read a String from an InputStream.
    */
//    private static String readString(InputStream is) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        InputStreamReader sr = new InputStreamReader(is);
//        char[] buf = new char[1024];
//        int len;
//        while ((len = sr.read(buf)) > 0) {   // error here
//            sb.append(buf, 0, len);
//        }
//        return sb.toString();
//    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
//    private static void writeString(String str, OutputStream os) throws IOException {
//        OutputStreamWriter sw = new OutputStreamWriter(os);
//        sw.write(str);
//        sw.flush();
//    }



















    // write bodies for http responses
//    protected void writeResponseBody(String json, OutputStream respBody) {
//        try {
//            writeString(json, respBody);
//            respBody.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    protected String readString(InputStream is) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        InputStreamReader sr = new InputStreamReader(is);
//        char[] buf = new char[1024];
//        int len;
//        while ((len = sr.read(buf)) > 0) {
//            sb.append(buf, 0, len);
//        }
//        return sb.toString();
//    }







/*

    // The getGameList method calls the server's "/games/list" operation to
    // retrieve a list of games running in the server in JSON format
    private static void getGameList(String serverHost, String serverPort) {

        // This method shows how to send a GET request to a server

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/register");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("GET");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(false);

            // Add an auth token to the request in the HTTP "Authorization" header
            //      http.addRequestProperty("Authorization", "afj232hj2332");

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();
            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);

                // TODO: Do something





                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // TODO: Do something

                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
    }



    */






/*

public class HttpClient {

    public String getUrl(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                return responseBodyData;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }

        return null;
    }
}

*/