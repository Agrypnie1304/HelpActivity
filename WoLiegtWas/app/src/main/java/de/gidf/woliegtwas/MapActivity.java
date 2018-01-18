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
    double distance;
    int punktausgabe;
    int punkte;


    Stadt Berlin = new Stadt("Berlin", new LatLng(52.520007,13.404954));
    Stadt Bremen = new Stadt("Bremen", new LatLng(53.0792962, 8.8016937));
    Stadt Dresden = new Stadt("Dresden", new LatLng(51.0504088, 13.7372621));
    Stadt Duesseldorf = new Stadt("Düsseldorf", new LatLng(51.2277411, 6.7734556));
    Stadt Erfurt = new Stadt("Erfurt", new LatLng(50.9847679,11.0298799));
    Stadt Hamburg = new Stadt("Hamburg", new LatLng(53.551085,9.993682));
    Stadt Hannover = new Stadt("Hannover", new LatLng(52.3758916,9.7320104));
    Stadt Kiel = new Stadt("Kiel", new LatLng(54.3232927,10.1227652));
    Stadt Magdeburg = new Stadt("Magdeburg", new LatLng(52.1205333,11.6276237));
    Stadt Mainz = new Stadt("Mainz", new LatLng(49.9928617,8.2472526));
    Stadt Muenchen = new Stadt("München", new LatLng(48.135125, 11.581981));
    Stadt Potsdam = new Stadt("Potsdam", new LatLng(52.390569,13.064473));
    Stadt Saarbruecken = new Stadt("Saarbrücken", new LatLng(49.2401572,6.9969327));
    Stadt Schwerin = new Stadt("Schwerin", new LatLng(53.6355022,11.4012499));
    Stadt Stuttgard = new Stadt("Stuttgard", new LatLng(48.775846,9.182932));
    Stadt Wiesbaden = new Stadt("Wiesbaden", new LatLng(50.0782184, 8.2397608));

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


            punktausgabe = berechnePunkte(stadtListRandom[l].koordinaten);
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

    private int berechnePunkte(LatLng koordinaten){


        double wert = 10000;
        double lat1 = koordinaten.getLatitude();
        double long1= koordinaten.getLongitude();
        //double lat2 = tipp.getLatitude();

        distance = wert/1000; //Distanz in km

        if (distance>200){
            punkte = punkte;
        } else if (distance >150){
            punkte = punkte + 10;
        }else if(distance>100){
            punkte = punkte + 25;
        }else if(distance > 50){
            punkte = punkte + 50;
        } else if(distance >25){
            punkte = punkte + 75;
        }else{
            punkte = punkte + 100;
        }
        return punkte;
    };

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

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
