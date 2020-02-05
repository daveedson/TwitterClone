package com.example.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("T8y3wT2pP323O64alIIsmJeVWBItWoQ8maUYXxx8")
                .clientKey("b8O8tQEjROQ2rs4ZUwIbWVzFQ6bSkhzvMGF19APF")
                .server("https://parseapi.back4app.com/")

    .build()
);

    }
}
