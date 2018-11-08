package com.aidanssite.traincatch;

import android.app.Application;

public class TrainCatch extends Application {
    private static TransitDirectory transitDirectory;

    public static TransitDirectory getTransitDirectory () {
        return transitDirectory;
    }

    public TransitDirectory setTransitDirectory (TransitDirectory newTDirectory) {
        transitDirectory = newTDirectory;
        return transitDirectory;
    }
}
