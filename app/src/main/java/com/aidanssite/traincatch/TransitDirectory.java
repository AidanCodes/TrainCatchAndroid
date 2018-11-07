package com.aidanssite.traincatch;

import java.util.ArrayList;

public class TransitDirectory {
    private ArrayList<TransitRoute> routeList;
    private ArrayList<TransitStation> stationList;

    public TransitDirectory () {
        routeList = new ArrayList<>();
        stationList = new ArrayList<>();
    }

    public TransitDirectory (ArrayList<TransitRoute> routeList, ArrayList<TransitStation> stationList) {
        this.routeList = routeList;
        this.stationList = stationList;
    }

    public ArrayList<TransitRoute> getRouteList () {
        return routeList;
    }

    public ArrayList<TransitStation> getStationList () {
        return stationList;
    }
}
