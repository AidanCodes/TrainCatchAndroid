package com.aidanssite.traincatch;

import android.app.Application;

public class TrainCatch extends Application {
    private TransitDirectory transitDirectory;

    public TransitDirectory getTransitDirectory () {
        return transitDirectory;
    }

    public void setTransitDirectory (TransitDirectory newTDirectory) {
        this.transitDirectory = newTDirectory;
    }
}
