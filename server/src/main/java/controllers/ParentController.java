package controllers;

import java.io.*;
import java.util.ArrayList;

public abstract class ParentController {

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        This simply splits up the URI and grabs the 2 or three components
     */
    public ArrayList<String> getUriComponents(String uri) {
        ArrayList<String> list = new ArrayList<String>();

        int index = uri.lastIndexOf('/');
        String generations = uri.substring(index + 1);

        uri = uri.substring(0, index);
        index = uri.lastIndexOf('/');
        String userName = uri.substring(index + 1);

        if (userName.toLowerCase().equals("fill")) {
            list.add(generations.toLowerCase());
            return list;
        }
        list.add(userName.toLowerCase());
        list.add(generations.toLowerCase());

        return list;
    }

    /*
        This simply splits up the URI for URI's with only one or two components
     */
    public ArrayList<String> getSingleUriComponents(String uri, String base) {
        ArrayList<String> list = new ArrayList<String>();

        int index = uri.lastIndexOf('/');
        String last = uri.substring(index + 1);

        if (last.toLowerCase().equals(base)) {
            return list;
        }

        if (last.equals("")) { // Make sure it doesnt equal ""
            return list;
        }
        list.add(last.toLowerCase());
        return list;
    }

    // write bodies for http responses
    protected void writeResponseBody(String json, OutputStream respBody) {
        try {
            writeString(json, respBody);
            respBody.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
