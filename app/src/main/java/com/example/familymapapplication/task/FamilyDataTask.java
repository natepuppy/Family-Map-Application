package com.example.familymapapplication.task;

import android.os.AsyncTask;
import com.example.familymapapplication.Client;
import request.PersonRequestAll;
import result.PersonResultAll;

public class FamilyDataTask extends AsyncTask<PersonRequestAll, Void, PersonResultAll> {

    public interface Context {
        //void onProgressUpdate(int percent);
        void onFamilyDataComplete(PersonResultAll result);
    }

    private Context context;
    private String serverHost = null;
    private String serverPort = null;

    public FamilyDataTask(Context c) { context = c; }

    public FamilyDataTask(Context c, String _serverHost, String _serverPort) {
        context = c;
        serverHost = _serverHost;
        serverPort = _serverPort;
    }

    @Override
    protected PersonResultAll doInBackground(PersonRequestAll... request) {
        PersonResultAll result = Client.personAll(serverHost, serverPort, request[0]);
        return result;
    }

    @Override
    protected void onPostExecute(PersonResultAll result) {
        context.onFamilyDataComplete(result);
    }
}
