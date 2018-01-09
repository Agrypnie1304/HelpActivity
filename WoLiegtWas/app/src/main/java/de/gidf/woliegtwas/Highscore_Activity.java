package de.gidf.woliegtwas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Highscore_Activity extends AppCompatActivity {

    //shared preferences
    private SharedPreferences highPrefs;

    //ui elements
    private TextView scoreView;

    public static final String HIGH_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //get text view
        scoreView = (TextView) findViewById(R.id.textHighList);
        //initiate shared prefs
        highPrefs = getSharedPreferences(HIGH_PREFS, 0);
        //get scores
        String[] savedScores = highPrefs.getString("highscore", "no scores").split("\\|");
        //build string
        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score + "\n");
        }
        //display scores
        scoreView.setText(scoreBuild.toString());
    }

    /*
    go back to main-page
     */
    public void onClickBackToMain(View view){
        startActivity(
                new Intent(this, MainActivity.class));
    }

    /*
    share the scores
     */
    /*
    To-Do: Body und Sub durch Highscore ersetzen
     */
    public void onClickShareIt(View view) {
        //sharing implementation here
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //Headline
        String shareBody = "Your body here";
        //Highscoretext
        String shareSub = "Your Subject here";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Teilen via"));
    }


}
