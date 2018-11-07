package com.aidanssite.traincatch;

import java.util.ArrayList;

public class TransitRoute {
    private String routeName;
    private ArrayList<TransitStation> stations;

    public TransitRoute (String routeName) {
        this.routeName = routeName;
        stations = new ArrayList<TransitStation>();
    }

    public String getRouteName () {
        return routeName;
    }

    public ArrayList<TransitStation> getStations () {
        return stations;
    }

    public void addStation (TransitStation newStation) {
        for (TransitStation station : stations) {
            if (station.equals(newStation)) {
                return;
            }
        }

        stations.add(newStation);
    }

    public static TransitRoute getRouteByName (String routeName, ArrayList<TransitRoute> routeList, boolean createIfEmpty) {
        for (TransitRoute route : routeList) {
            if (route.getRouteName().equals(routeName)) {
                return route;
            }
        }

        if (createIfEmpty) {
            TransitRoute newRoute = new TransitRoute(routeName);
            routeList.add(newRoute);
            return newRoute;
        }

        return null;
    }
}
