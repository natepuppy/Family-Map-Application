package com.example.familymapapplication.task;

import android.os.AsyncTask;
import com.example.familymapapplication.Client;
import request.LoginRequest;
import result.LoginResult;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResult> {

    public interface Context {
        void onLoginComplete(LoginResult result);
    }

    private Context context;
    private String serverHost = null;
    private String serverPort = null;

    public LoginTask(Context c) {
        context = c;
    }

    public LoginTask(Context c, String _serverHost, String _serverPort) {
        context = c;
        serverHost = _serverHost;
        serverPort = _serverPort;

    }

    @Override
    protected LoginResult doInBackground(LoginRequest... request) {
        LoginResult result = Client.login(serverHost, serverPort, request[0]);
        return result;
    }

    @Override
    protected void onPostExecute(LoginResult result) {
        context.onLoginComplete(result);
    }
}

