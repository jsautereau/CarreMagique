package com.example.juliensautereau.carremagique;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExitGame extends AppCompatActivity {

    TextView time, mode, status, dataRead;
    EditText nickname;

    Button lecture;

    String texte;
    String chronoValue = "99:59";
    String val = "FACILE";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_game);

        time = findViewById(R.id.timeToSolv);
        mode = findViewById(R.id.modeGame);
        nickname = findViewById(R.id.nicknameZone);

        dataRead = findViewById(R.id.dataRead);

        status = findViewById(R.id.etat);

        Intent i = getIntent();

        /* Info from another intent : Game */
        val = i.getStringExtra("niveau_choisi");
        chronoValue = i.getStringExtra("time_resolv");

        sp = getSharedPreferences("ExitGame", MODE_PRIVATE); editor = sp.edit();

        time.setText("TIME " + chronoValue);
        mode.setText("MODE " + val);
    }

    // Sauvegarde du score
    public void save(View v) {

        texte = nickname.getText().toString();

        if(!texte.equals("")) {

            Integer id = 0;

            for(String key: sp.getAll().keySet()) {

                String copyKey = key;
                boolean checkKey = copyKey.startsWith("nickname");

                if(checkKey) {

                    id += 1;
                }
            }

            editor.putString("nickname"+id, texte);
            editor.putString("level"+id, val);
            editor.putString("score"+id, chronoValue);
            editor.commit();

            Toast.makeText(this, "Score ajouté avec succès !", Toast.LENGTH_LONG).show();

            // on reset la textefield
            nickname.setText("");

            // on active le bouton de lecture

            this.finish();

        }
        else
        {
            Toast.makeText(this, "Merci de renseigner votre nickname", Toast.LENGTH_LONG).show();
        }
    }

    // Retour au menu Home
    public void goToHome(View v) {

        this.finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
