package de.gidf.woliegtwas;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by Franz on 16.01.2018.
 */

public class Stadt {
    String name;
    double latitude;
    double longitude;

    Stadt(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
