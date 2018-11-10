package com.aidanssite.traincatch;

import android.util.Log;

import java.util.ArrayList;

public class TransitDirectory {
    private ArrayList<TransitRoute> routeList;
    private ArrayList<TransitStation> stationList;

    public TransitDirectory () {
        routeList = new ArrayList<>();
        stationList = new ArrayList<>();
    }

    public TransitDirectory (ArrayList<TransitRoute> routeList,
                             ArrayList<TransitStation> stationList) {
        this.routeList = routeList;
        this.stationList = stationList;
    }

    public ArrayList<TransitRoute> getRouteList () {
        return routeList;
    }

    public ArrayList<TransitStation> getStationList () {
        return stationList;
    }

    public void debug() {
        for (TransitRoute route : routeList) {
            Log.d("Directory Debug", "Route: " + route);
        }

        for (TransitStation station : stationList) {
            Log.d("Directory Debug", "Station: " + station);
        }
    }
}
