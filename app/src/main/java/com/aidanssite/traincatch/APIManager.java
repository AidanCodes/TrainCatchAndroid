package com.aidanssite.traincatch;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIManager extends AsyncTask<Void, Void, JSONArray> {

    public static final int GOOGLE_MAPS = 0,
            DISTANCE_MATRIX = 0,
            SEPTA = 1,
            SYSTEM_LOCATIONS = 0,
            RAIL_AD = 1;

    public int APIID, routeID = -1;
    private String[][] queryParams, routeParams;
    private String baseURL, routeString, APIKey = "";

    public APIManager (int APIID, int routeID, String[][] queryParams, String[][] routeParams) {
        this.queryParams = queryParams;
        this.routeParams = routeParams;


        initializeSettings(APIID, routeID);

        Log.d("APIManager", "Initialized APIManager");
    }

    @Override
    protected JSONArray doInBackground (Void...voids) {
        Log.d("APIManager", "Began execution");

        String rJsonString = "";
        JSONArray rJson = null;

        String queryStr = formatQueryParams(queryParams, APIKey);

        try {
            URL url = new URL(baseURL + routeString + queryStr);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            rJsonString = readStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            rJson = new JSONArray(rJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rJson;
    }

    protected void onPostExecute (JSONArray result) {
        switch (APIID) {
            case GOOGLE_MAPS: {
                switch (routeID) {
                    case DISTANCE_MATRIX: {
                        Log.d("APIManager Result", "Google Maps Distance Matrix: " + result);
                    }
                }
            }

            case SEPTA: {
                switch (routeID) {
                    case SYSTEM_LOCATIONS: {
                        parseSeptaLocations(result);
                    }

                    case RAIL_AD: {
                        Log.d("APIManager Result", "SEPTA Rail A/D: " + result);
                    }
                }
            }
        }
    }

    private String formatQueryParams (String[][] queryParams, String APIKey) {
        if (queryParams.length != 0 && APIKey.length() != 0) {
            String queryStr = "?";

            for (int i = 0; i < queryParams.length; i++) {
                queryStr += queryParams[i][0] + "=" + queryParams[i][1];

                if (i < queryParams.length - 1) {
                    queryStr += "&";
                }
            }

            if (APIKey.length() != 0) {
                queryStr += "&key=" + APIKey;
            }

            return queryStr;
        }

        return "";
    }

    private String readStream (InputStream in) {
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

    private void parseSeptaLocations (JSONArray locJson) {
        //  TODO
    }

    private void initializeSettings (int APIID, int routeID) {
        this.APIID = APIID;
        this.routeID = routeID;

        switch (APIID) {
            case GOOGLE_MAPS: {
                baseURL = "https://maps.googleapis.com/maps/api/";
                APIKey = "AIzaSyCerGZoWR42ZQNYXT3Cq08MrEMr4Kj4NH0";

                switch (routeID) {
                    case DISTANCE_MATRIX: {
                        routeString = "distancematrix/json";
                    }
                }
            }

            case SEPTA: {
                baseURL = "https://www3.septa.org/hackathon/";

                switch (routeID) {
                    case SYSTEM_LOCATIONS: {
                        routeString = "locations/get_locations.php";
                    }

                    case RAIL_AD: {
                        routeString = "Arrivals/";
                    }
                }
            }
        }
    }
}
