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

    /*#
    go back to main-page
     */
    public void onClickBackToMain(View view){
        startActivity(
                new Intent(this, MainActivity.class));
    }

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

//    private void setHighscore(){
//        //set Highscore
//        int exScore = getScore();
//
//        if(exScore > 0){
//            //we have a valid score
//            SharedPreferences.Editor scoreEditor = highPrefs.edit();
//            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
//            String dateOutput = dateForm.format(new Date());
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
//                Score newScore = new Score(dateOutput, exScore);
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
//                scoreEditor.putString("highscore", "" + dateOutput + " - " + exScore);
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
//        int exScore = getScore();
//        savedInstanceState.putInt("score", exScore);
//        //superclass method
//        super.onSaveInstanceState(savedInstanceState);
//    }
}
