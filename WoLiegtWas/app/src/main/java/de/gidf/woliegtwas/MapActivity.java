package de.gidf.woliegtwas;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class MapActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ziel token
        Mapbox.getInstance(this, "pk.eyJ1Ijoic3lubyIsImEiOiJjamIyN2hjMDYxZ2NwMzNuMjVtMnM2cjg2In0.kEgKpYjF3FA5sdfTRDMw0A");
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                //Ziel URL
                mapboxMap.setStyleUrl("mapbox://styles/syno/cjb27u3h1s8ls2sqvi4ua7hs9");

                //Der Marker startet mitten in Deutschland
                final Marker marker = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(51.163375,10.447683))
                        //.title("Dein Tipp:") //ist eigentlich überflüssig
                        //.snippet("Zentrum")   //das ist ein kleines Infofeld darunter
                );


                mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {

                        // Weil es cool aussieht, animieren wir mal den Marker
                        ValueAnimator markerAnimator = ObjectAnimator.ofObject(marker, "position",
                                new LatLngEvaluator(), marker.getPosition(), point);
                        //Zeit in ms also 2000 = 2s
                        markerAnimator.setDuration(500);
                        markerAnimator.start();
                    }
                });

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



    private static class LatLngEvaluator implements TypeEvaluator<LatLng> {
        // Marker Animation interpolieren

        private LatLng latLng = new LatLng();


        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }


}
