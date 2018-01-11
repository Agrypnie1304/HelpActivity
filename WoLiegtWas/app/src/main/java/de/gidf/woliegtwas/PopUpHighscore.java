package de.gidf.woliegtwas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fabi on 08.01.2018.
 */

class PopUpHighscore extends Activity{

    private TextView popupViewScore;

    private int score;

    //shared preferences
    private SharedPreferences highPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        popupViewScore = (TextView) findViewById(R.id.textPopupScore);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));


        /*
        To-Do: überprüfen, ob neuer Highscore vorliegt und entsprechend TextView und TextEdit anzeigen
         */
        if(getIntent().hasExtra("Score") == true){
            score = getIntent().getExtras().getInt("Score");
            popupViewScore.setText(String.valueOf(score));

        }

    }

    /*
    go back to main-page
    */
    public void onClickBackToMain(View view){
        startActivity(
                new Intent(this, MainActivity.class));
    }

    /*
    add highscore to top ten and go to highscore-page
     */
    public void onClickAddHighscore(View view){
        // add highscore

        /*
        To-Do: überprüfen, ob Name eingegeben und neuen Highscore mit Name eintragen
         */


        // go to highscore
        startActivity(
                new Intent(this, Highscore_Activity.class));
    }

//        private void setHighscore(){
//        //set Highscore
//        if(score > 0){
//            //we have a valid score
//            SharedPreferences.Editor scoreEditor = highPrefs.edit();
//            String name = null;
//            //get existing scores
//            String scores = highPrefs.getString("highscore", "");
//
//            //check for scores
//            if(scores.length() > 0){
//                //we have existing scores
//                List<Score> scoreStrings = new ArrayList<Score>();
//                //split scores
//                String[] exScores = scores.split("\\|");
//                //add score object for each
//                for(String eSc : exScores){
//                    String[] parts = eSc.split(" - ");
//                    scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
//                }
//
//                //new score
//                Score newScore = new Score(name, score);
//                scoreStrings.add(newScore);
//                //sort
//                Collections.sort(scoreStrings);
//                //get top ten
//                StringBuilder scoreBuild = new StringBuilder("");
//                for(int s=0; s < scoreStrings.size(); s++){
//                    if(s >= 10) {
//                        break;
//                    }
//                    if(s > 0 ){
//                        scoreBuild.append("|");
//                    }
//                    scoreBuild.append(scoreStrings.get(s).getScoreText());
//                }
//                //write to prefs
//                scoreEditor.putString("highscore", scoreBuild.toString());
//                scoreEditor.commit();
//
//            } else {
//                // no exisiting scores
//                scoreEditor.putString("highscore", "" + name + " - " + score);
//                scoreEditor.commit();
//            }
//        }
//    }
//
//    protected void onDestroy(){
//        setHighscore();
//        super.onDestroy();
//    }
//
//
//    //save instance state
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState){
//        //save state
//        savedInstanceState.putInt("score", score);
//        //superclass method
//        super.onSaveInstanceState(savedInstanceState);
//    }
}
