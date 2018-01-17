package de.gidf.woliegtwas;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3;
    MediaPlayer bkgrdmsc;
    private int lastbkgdchecked = MainActivity.bkgdchecked;

    public static int bkgdchecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (lastbkgdchecked ==0) {
            bkgrdmsc = MediaPlayer.create(MainActivity.this, R.raw.background_music);
            bkgrdmsc.setLooping(true);
            bkgrdmsc.start();
        }

        InitializeApp();
        bkgdMusicoff();
    }

    public void bkgdMusicoff(){
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    bkgdchecked = 1;
                    bkgrdmsc.pause();
                }else{
                    bkgdchecked = 0;
                    bkgrdmsc.start();
                }
            }
        });
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
    @Override
    public void onBackPressed() {
        if(lastbkgdchecked ==0) {
            bkgrdmsc.release();
        }
        MainActivity.bkgdchecked = 0;
        super.onBackPressed();
        finish();
    }
}
