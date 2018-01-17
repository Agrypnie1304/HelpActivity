package de.gidf.woliegtwas;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by Franz on 16.01.2018.
 */

public class Stadt {
    String name;
    LatLng koordinaten;

    Stadt(String name, LatLng koordinaten){
        this.name = name;
        this.koordinaten = new LatLng();
    }

}
