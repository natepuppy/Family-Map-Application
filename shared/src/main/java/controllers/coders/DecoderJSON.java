package controllers.coders;

import com.google.gson.Gson;
import request.*;
import result.EventResultAll;
import result.EventResultOne;
import result.FillResult;
import result.LoadResult;
import result.LoginResult;
import result.PersonResultAll;
import result.PersonResultOne;
import result.RegisterResult;

public class DecoderJSON {
    // Convert String in JSON form to request class
    // convert to all lowercase as well
    static public RegisterRequest decodeRegister(String json) {
        Gson g = new Gson();
        RegisterRequest newRequest = g.fromJson(json, RegisterRequest.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public LoginRequest decodeLogin(String json) {
        Gson g = new Gson();
        LoginRequest newRequest = g.fromJson(json, LoginRequest.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public FillRequest decodeFill(String json) {
        Gson g = new Gson();
        FillRequest newRequest = g.fromJson(json, FillRequest.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public LoadRequest decodeLoad(String json) {
        Gson g = new Gson();
        LoadRequest newRequest = g.fromJson(json, LoadRequest.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public PersonRequestOne decodePersonOne(String json) {
        Gson g = new Gson();
        PersonRequestOne newRequest = g.fromJson(json, PersonRequestOne.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public PersonRequestAll decodePersonAll(String json) {
        Gson g = new Gson();
        PersonRequestAll newRequest = g.fromJson(json, PersonRequestAll.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public EventRequestOne decodeEventOne(String json) {
        Gson g = new Gson();
        EventRequestOne newRequest = g.fromJson(json, EventRequestOne.class);
        newRequest.toLowerCase();
        return newRequest;
    }
    static public EventRequestAll decodeEventAll(String json) {
        Gson g = new Gson();
        EventRequestAll newRequest = g.fromJson(json, EventRequestAll.class);
        newRequest.toLowerCase();
        return newRequest;
    }


    static public RegisterResult decodeRegisterResult(String json) {
        Gson g = new Gson();
        RegisterResult newResult = g.fromJson(json, RegisterResult.class);
        return newResult;
    }
    static public LoginResult decodeLoginResult(String json) {
        Gson g = new Gson();
        LoginResult newResult = g.fromJson(json, LoginResult.class);
        return newResult;
    }
    static public FillResult decodeFillResult(String json) {
        Gson g = new Gson();
        FillResult newResult = g.fromJson(json, FillResult.class);
        return newResult;
    }
    static public LoadResult decodeLoadResult(String json) {
        Gson g = new Gson();
        LoadResult newResult = g.fromJson(json, LoadResult.class);
        return newResult;
    }
    static public PersonResultOne decodePersonOneResult(String json) {
        Gson g = new Gson();
        PersonResultOne newResult = g.fromJson(json, PersonResultOne.class);
        return newResult;
    }
    static public PersonResultAll decodePersonAllResult(String json) {
        Gson g = new Gson();
        PersonResultAll newResult = g.fromJson(json, PersonResultAll.class);
        return newResult;
    }
    static public EventResultOne decodeEventOneResult(String json) {
        Gson g = new Gson();
        EventResultOne newResult = g.fromJson(json, EventResultOne.class);
        return newResult;
    }
    static public EventResultAll decodeEventAllResult(String json) {
        Gson g = new Gson();
        EventResultAll newResult = g.fromJson(json, EventResultAll.class);
        return newResult;
    }
}
