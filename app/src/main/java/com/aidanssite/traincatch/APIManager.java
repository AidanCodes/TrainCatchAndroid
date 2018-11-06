package com.aidanssite.traincatch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIManager extends AsyncTask<Void, Void, String> {

    public static final int GOOGLE_MAPS = 0;
    public static final int SEPTA = 1;

    private String[][] params;
    private String baseURL, APIKey = "";

    public APIManager (int APIID, String[][] params) {
        this.params = params;

        initializeSettings(APIID);

        Log.d("APIManager", "Initialized APIManager");
    }

    @Override
    protected String doInBackground(Void...voids) {
        Log.d("APIManager", "Began execution");

        String rJson = "";

        String queryStr = formatParams(params);

        try {
            URL url = new URL("https://www3.septa.org/hackathon/locations/get_locations.php" + queryStr);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            rJson = readStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rJson;
    }

    protected void onPostExecute(String result) {
        Log.d("QUERY COMPLETE", "RESULT: " + result);


    }

    private String formatParams(String[][] params) {
        String queryStr = "?";

        for (int i = 0; i < params.length; i ++) {
            queryStr += params[i][0] + "=" + params[i][1];

            if (i < params.length - 1) {
                queryStr += "&";
            }
        }

        return queryStr;
    }

    private String readStream(InputStream in) {
        String result = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return result;
    }

    private void initializeSettings(int APIID) {
        if (APIID == GOOGLE_MAPS) {
            baseURL = "google";
            APIKey = "AIzaSyCerGZoWR42ZQNYXT3Cq08MrEMr4Kj4NH0";
        } else if (APIID == SEPTA) {
            baseURL = "https://www3.septa.org/hackathon/locations/get_locations.php";
        }
    }
}
