package com.example.familymapapplication.task;

import android.os.AsyncTask;
import com.example.familymapapplication.Client;
import request.EventRequestAll;
import result.EventResultAll;

public class EventTask extends AsyncTask<EventRequestAll, Void, EventResultAll> {

    public interface Context {
        //void onProgressUpdate(int percent);
        void onEventTaskComplete(EventResultAll result);
    }

    private Context context;
    private String serverHost = null;
    private String serverPort = null;

    public EventTask(Context c) {
        context = c;
    }

    public EventTask(Context c, String _serverHost, String _serverPort) {
        context = c;
        serverHost = _serverHost;
        serverPort = _serverPort;
    }

    @Override
    protected EventResultAll doInBackground(EventRequestAll... request) {
        EventResultAll result = Client.eventAll(serverHost, serverPort, request[0]);
        return result;
    }

    @Override
    protected void onPostExecute(EventResultAll result) {
        context.onEventTaskComplete(result);
    }
}


// re-sync task will also call the database