package com.aidanssite.traincatch;

import java.util.ArrayList;

public class TransitStation {
    private double latitude, longitude;
    private ArrayList<TransitRoute> routes;

    private String company, locationID, locationName;
    private int APIID;

    public TransitStation (String company, int APIID, String locationID, String locationName, double latitude, double longitude) {
        this.company = company;
        this.APIID = APIID;
        this.locationID = locationID;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude () {
        return latitude;
    }

    public double getLongitude () {
        return longitude;
    }

    public String getCompany () {
        return company;
    }

    public int getAPIID () {
        return APIID;
    }

    public String getLocationName () {
        return locationID;
    }

    public String getLocationID () {
        return locationName;
    }

    public void addRoute (String routeName, ArrayList<TransitRoute> routeList) {
        TransitRoute newRoute = TransitRoute.getRouteByName(routeName, routes, false);

        if (newRoute == null) {
            newRoute = TransitRoute.getRouteByName(routeName, routeList, true);
        }

        newRoute.addStation(this);
        routes.add(newRoute);
    }

    public ArrayList<TransitRoute> getRoutes () {
        return routes;
    }
}
