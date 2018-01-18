package de.gidf.woliegtwas;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.Random;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;

    int i =1;
    int j =2;
    int k=1, l=0, m=0;
    double distanceKM;
    double distanceMeter;
    int punktausgabe;
    int punkte;
    LatLng markerPos;

    Stadt Berlin = new Stadt("Berlin",52.520007,13.404954);
    Stadt Bremen = new Stadt("Bremen",53.0792962, 8.8016937);
    Stadt Dresden = new Stadt("Dresden",51.0504088, 13.7372621);
    Stadt Duesseldorf = new Stadt("Düsseldorf", 51.2277411, 6.7734556);
    Stadt Erfurt = new Stadt("Erfurt",50.9847679,11.0298799);
    Stadt Hamburg = new Stadt("Hamburg", 53.551085,9.993682);
    Stadt Hannover = new Stadt("Hannover", 52.3758916,9.7320104);
    Stadt Kiel = new Stadt("Kiel", 54.3232927,10.1227652);
    Stadt Magdeburg = new Stadt("Magdeburg", 52.1205333,11.6276237);
    Stadt Mainz = new Stadt("Mainz",49.9928617,8.2472526);
    Stadt Muenchen = new Stadt("München",  48.135125, 11.581981);
    Stadt Potsdam = new Stadt("Potsdam", 52.390569,13.064473);
    Stadt Saarbruecken = new Stadt("Saarbrücken", 49.2401572,6.9969327);
    Stadt Schwerin = new Stadt("Schwerin", 53.6355022,11.4012499);
    Stadt Stuttgard = new Stadt("Stuttgard", 48.775846,9.182932);
    Stadt Wiesbaden = new Stadt("Wiesbaden", 50.0782184, 8.2397608);

    Stadt[] stadtListRandom = new Stadt[]{
                    Berlin,
                    Bremen,
                    Dresden,
                    Duesseldorf,
                    Erfurt,
                    Hamburg,
                    Hannover,
                    Kiel,
                    Magdeburg,
                    Mainz,
                    Muenchen,
                    Potsdam,
                    Saarbruecken,
                    Schwerin,
                    Stuttgard,
                    Wiesbaden};


    public void confirm (View view){
        j=j+1; //erster klick (Start): j wird 3, k ist 1--> geht damit in abfrage 1
        Button button = findViewById(R.id.confirm);

        if (k == 1 && j%2!=0) { //wenn Start gedrückt wird, soll erste Stadt kommen

            shuffleArray(stadtListRandom);

            ((TextView)findViewById(R.id.stadt)).setText(""+ stadtListRandom[l].name);
            k=k+1;
            button.setText("bestätige");

        }else if (j%2==0 && k!=1) { //beim zweiten klick wird j gerade (4), beim dritten ungerade --> 2. klick: wechsel von start zu weiter; 3. klick: wechsel zu bestätige

            button.setText("weiter");

            punktausgabe = berechnePunkte(stadtListRandom[l].latitude, stadtListRandom[l].longitude);
            ((TextView) findViewById(R.id.score)).setText("Score: " + punktausgabe);
            l=l+1;

        }else {

            button.setText("bestätige");

            ((TextView)findViewById(R.id.stadt)).setText("" + stadtListRandom[l].name);

            //wenn 10 mal weiter gedrückt wurde, soll in die Highscore gegangen werden:
            if (i == 10) {
                Intent popupHighscore = new Intent(this, PopUpHighscore.class);
                popupHighscore.putExtra("Score", punkte);
                startActivity(popupHighscore);
            }
            i = i+1;
        }

    }

    public void end (View view){

        //wenn 10 mal confirm gedrückt wurde, soll das passieren:

        if (l == 10) {

            Intent goToHighscore = new Intent(this, Highscore_Activity.class);
            goToHighscore.putExtra(getPackageName(), punkte); //hier werden punkte an highscore übergeben
            startActivity(goToHighscore);

        }else{
            Intent goToMainEnd = new Intent (this, MainActivity.class);
            startActivity(goToMainEnd);
        }
    }


    private int berechnePunkte(double lati, double longi){

        double lat1 = lati;
        double long1= longi;
        double lat2 = markerPos.getLatitude();
        double long2= markerPos.getLongitude();

        distanceMeter = distance(lat1, lat2, long1, long2);
        distanceKM = distanceMeter/1000; //Distanz in km

        if (distanceKM>100){
            punkte = punkte;
        } else if (distanceKM >80){ // 80 bis 100 km 10 pkt
            punkte = punkte + 10;
        }else if(distanceKM>60){    // 60 bis 80 km 25 pkt
            punkte = punkte + 25;
        }else if(distanceKM > 40){  // 40 bis 60 km 50 pkt
            punkte = punkte + 50;
        } else if(distanceKM >25){  // 25 bis 40 km 75 pkt
            punkte = punkte + 75;
        }else{                      //weniger als 25 100 pkt
            punkte = punkte + 100;
        }
        return punkte;
    };

    public static double distance(double lat1, double lat2, double long1,
                                  double long2) {

        final int R = 6371; // Erdradius

        double latDistance = Math.toRadians(lat2 - lat1);
        double longDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // in Meter

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    private void shuffleArray (Stadt [] ar){ //Fisher-Yates shuffle
        Random rand = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rand.nextInt(i + 1);
            Stadt a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


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
                        markerPos=marker.getPosition();
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
