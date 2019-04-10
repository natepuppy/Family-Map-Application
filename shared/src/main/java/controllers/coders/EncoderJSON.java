package controllers.coders;

import com.google.gson.Gson;

import request.EventRequestAll;
import request.EventRequestOne;
import request.FillRequest;
import request.LoadRequest;
import request.LoginRequest;
import request.PersonRequestAll;
import request.PersonRequestOne;
import request.RegisterRequest;
import result.*;

public class EncoderJSON {
    static public String encodeRegister(RegisterResult result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }

    static public String encodeLogin(LoginResult result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeClear(ClearResult result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeFill(FillResult result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeLoad(LoadResult result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodePersonOne(PersonResultOne result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodePersonAll(PersonResultAll result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeEventOne(EventResultOne result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeEventAll(EventResultAll result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }





    static public String encodeRegister(RegisterRequest result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }

    static public String encodeLogin(LoginRequest result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }

    static public String encodeFill(FillRequest result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeLoad(LoadRequest result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodePersonOne(PersonRequestOne result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodePersonAll(PersonRequestAll result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeEventOne(EventRequestOne result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
    static public String encodeEventAll(EventRequestAll result) {
        Gson gson = new Gson();
        String x = gson.toJson(result);
        return x;
    }
}
