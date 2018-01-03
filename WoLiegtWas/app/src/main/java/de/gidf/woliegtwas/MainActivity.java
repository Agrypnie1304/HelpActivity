package de.gidf.woliegtwas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeApp();
    }
    private void InitializeApp() {
        btn1 = (Button) findViewById(R.id.btnPlay);
        btn2 = (Button) findViewById(R.id.btnHelp);
        btn3 = (Button) findViewById(R.id.btnHighScore);


        //
        // EVENT LISTENERS
        //

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMapActivity();
            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHelpActivity();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHighScoreActivity();
            }
        });

    }
    private void startMapActivity() {
        startActivity(
                new Intent(this, MapActivity.class));
    }


    private void startHelpActivity() {
        startActivity(
                new Intent(this, HelpActivity.class));
    }


    private void startHighScoreActivity() {
        startActivity(
                new Intent(this, Highscore_Activity.class));
    }
}
