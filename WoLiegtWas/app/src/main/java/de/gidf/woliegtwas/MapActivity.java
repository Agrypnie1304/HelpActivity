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
    int k=1, l,m;
    String testString;
    double distance;
    int punktausgabe;
    int punkte;
    int stadtAnzahl =16;

    Random rand = new Random();
    int n;
    Stadt[] stadtList = new Stadt[stadtAnzahl -1];
    Stadt[] stadtListRandom = new Stadt[stadtAnzahl-1];

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



    public void confirm (View view){
        j=j+1; //erster klick (Start): j wird 3, k ist 1--> geht damit in abfrage 1
        Button button = findViewById(R.id.confirm);

        if (k == 1 && j%2!=0) { //wenn Start gedrückt wird, soll erste Stadt kommen
            ((TextView)findViewById(R.id.stadt)).setText(""+ stadtList[j-3].name);
            k=k+1;
            button.setText("bestätige");

        }else if (j%2==0 && k!=1) { //beim zweiten klick wird j gerade (4), beim dritten ungerade --> 2. klick: wechsel von start zu weiter; 3. klick: wechsel zu bestätige

            button.setText("weiter");

            punktausgabe = berechnePunkte(stadtList[j-3].koordinaten);
            ((TextView) findViewById(R.id.score)).setText("Score: " + punktausgabe);


        }else {

            button.setText("bestätige");

            ((TextView)findViewById(R.id.stadt)).setText("" + stadtList[j-3].name);

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

        if (j == 10) {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//FEHLER!!!!!!
        stadtList[0] = Berlin;
        stadtList[1] = Bremen;
        stadtList[2] = Dresden;
        stadtList[3] = Duesseldorf;
        stadtList[4] = Erfurt;
        stadtList[5] = Hamburg;
        stadtList[6] = Hannover;
        stadtList[7] = Kiel;
        stadtList[8] = Magdeburg;
        stadtList[9] = Mainz;
        stadtList[10] = Muenchen;
        stadtList[11] = Potsdam;
        stadtList[12] = Saarbruecken;
        stadtList[13] = Schwerin;
        stadtList[14] = Stuttgard;
        stadtList[15] = Wiesbaden;

        for (l=0; l<= stadtAnzahl-1; l++){ //erstelle eine random gemixte neue Liste, welche nacheinander abgefragt werden kann
            this.n = l+rand.nextInt(stadtAnzahl-1-l);
            stadtListRandom[l]=stadtList[this.n];
        }

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
