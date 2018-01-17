/**
 * class PopUpHighscore
 * new intent to handle and display the score
 */

package de.gidf.woliegtwas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PopUpHighscore extends Activity {

    // ui elements
    private TextView popupViewScore, popupViewHead, popupViewError;
    private EditText popupEditName;
    private Button popupBtnHighscore, popupBtnPlayAgain;

    private int score;

    //shared preferences
    private SharedPreferences gamePrefs;
    public static final String HIGH_PREFS = "HighscoreFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        // get ui elements
        popupViewScore = (TextView) findViewById(R.id.textPopupScore);
        popupViewHead = (TextView) findViewById(R.id.textPopupHead);
        popupViewError = (TextView) findViewById(R.id.textPopupError);
        popupEditName = (EditText) findViewById(R.id.EditPopupName);
        popupBtnHighscore = (Button) findViewById(R.id.BtnPopupHigh);
        popupBtnPlayAgain = (Button) findViewById(R.id.BtnPopupPlayAgain);

        // adjust the size of the intent
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        //initiate shared prefs
        gamePrefs = getSharedPreferences(HIGH_PREFS, 0);

        // check if score is given
        if (getIntent().hasExtra("Score") == true) {
            // get score
            score = getIntent().getExtras().getInt("Score");
            // display score
            popupViewScore.setText(String.valueOf(score));
            //check for new highscore and display necessary ui elements
            if (checkForNewHighscore(score)) {
                popupViewHead.setText("Glückwunsch, Neuer Highscore! :)");
            } else {
                popupViewHead.setText("Leider kein neuer Highscore. :(");
                popupBtnHighscore.setVisibility(View.INVISIBLE);
                popupEditName.setVisibility(View.INVISIBLE);
                popupBtnPlayAgain.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * ###############
     * # new methods #
     * ###############
     */

    /**
     * ###################
     * # onClick-methods #
     * ###################
     */

    /**
     * play again
     */
    public void onClickPlayAgain(View view) {
        startActivity(
                new Intent(this, MapActivity.class));
    }

    /**
     * go back to main-page
     */
    public void onClickBackToMain(View view) {
        startActivity(
                new Intent(this, MainActivity.class));
    }

    /**
     * add highscore to top ten and go to highscore-page
     */
    public void onClickAddHighscore(View view) {
        // check if name is given and set new highscore to top ten
        if (!"".equals(popupEditName.getText().toString())) {
            //add highscore
            setHighscore();
            // go to highscore
            startActivity(
                    new Intent(this, Highscore_Activity.class));
        } else {
            popupViewError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * #####################
     * # highscore-methods #
     * #####################
     */

    /**
     * check for new highscore
     *
     * @param exScore (int) score to check
     * @return true for a new highscore
     */
    private boolean checkForNewHighscore(int exScore) {
        if (exScore > 0) {
            //get existing scores
            String StringScores = gamePrefs.getString("highscore", "");
            //check for scores
            if (StringScores.length() > 0) {
                //we have existing scores
                List<Integer> scoreStringsList = new ArrayList<Integer>();
                //split scores
                String[] exScoresStringArray = StringScores.split("\\|");
                //add score for each
                for (String eSc : exScoresStringArray) {
                    String[] parts = eSc.split(" - ");
                    scoreStringsList.add(Integer.parseInt(parts[1]));
                }
                // compare with other scores from existing highscore
                for (int i = 0; i < scoreStringsList.size(); i++) {
                    if (exScore > scoreStringsList.get(i)) {
                        // new highscore
                        return true;
                    }
                }
                // no new highscore
                return false;
            } else {
                // no exisiting scores
                return true;
            }
        }
        // in principle return false for no new highscore
        return false;
    }

    /**
     * set new highscore to top ten
     */
    private void setHighscore() {
        //get score
        int exScore = getIntent().getExtras().getInt("Score");
        //get name
        String exName = popupEditName.getText().toString();
        //set Highscore
        // we have a valid score
        SharedPreferences.Editor scoreEditor = gamePrefs.edit();
        //get existing scores
        String StringScores = gamePrefs.getString("highscore", "");

        //check for scores
        if (StringScores.length() > 0) {
            //we have existing scores
            List<Score> scoreStringsList = new ArrayList<Score>();
            //split scores
            String[] exScoresStringArray = StringScores.split("\\|");
            //add score object for each
            for (String eSc : exScoresStringArray) {
                String[] parts = eSc.split(" - ");
                scoreStringsList.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }

            //new score
            Score newScore = new Score(exName, exScore);
            scoreStringsList.add(newScore);
            //sort
            Collections.sort(scoreStringsList);
            //get top ten
            StringBuilder scoreBuild = new StringBuilder("");
            for (int s = 0; s < scoreStringsList.size(); s++) {
                if (s >= 10) {
                    break;
                }
                if (s > 0) {
                    scoreBuild.append("|");
                }
                scoreBuild.append(scoreStringsList.get(s).getScoreText());
            }
            //write to prefs
            scoreEditor.putString("highscore", scoreBuild.toString());
            scoreEditor.commit();

        } else {
            // no exisiting scores
            scoreEditor.putString("highscore", "" + exName + " - " + exScore);
            scoreEditor.commit();
        }
    }

    protected void onDestroy() {
        setHighscore();
        super.onDestroy();
    }

    //save instance state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //save state
        int exScore = getIntent().getExtras().getInt("Score");
        savedInstanceState.putInt("exScore", exScore);
        //superclass method
        super.onSaveInstanceState(savedInstanceState);
    }
}