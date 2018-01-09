package com.example.juliensautereau.carremagique;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button playNow, scoreTab, exitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        playNow = findViewById(R.id.playButton);
        scoreTab = findViewById(R.id.scoreButton);
        exitButton = findViewById(R.id.quitGame);

    }

    public void goToLevel(View v) {

        Intent intent = new Intent(this, LevelChoice.class);
        startActivity(intent);

    }

    public void afficheScore(View v) {

        Intent intent = new Intent(this, ScoreTab.class);
        startActivity(intent);
    }

    public void exitGame(View v) {

        this.finish();
    }


}
