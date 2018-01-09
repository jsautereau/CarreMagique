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
        lecture = findViewById(R.id.lectureFichier);

        dataRead = findViewById(R.id.dataRead);

        status = findViewById(R.id.etat);

        Intent i = getIntent();

        /* Info from another intent : Game */
        val = i.getStringExtra("niveau_choisi");
        chronoValue = i.getStringExtra("time_resolv");

        sp = getSharedPreferences("ExitGame", MODE_PRIVATE); editor = sp.edit();

        time.setText("TIME " + chronoValue);
        mode.setText("MODE " + val);

        lecture.setEnabled(false);

        /* Gestion d'enregistrement du score du joueur */


    }

    public void registerScore(View v) {

        // On récupère le score et on l'enregistre
        String saisie = nickname.getText().toString();
        saisie = saisie + "\n";
        String filename = "nickname.txt";

        // Ecriture
        try {
            FileOutputStream f = openFileOutput(filename, Context.MODE_APPEND);
            f.write(saisie.getBytes());
            f.close();
            Toast.makeText(this, "Success, your nickname IS OK ! :)", Toast.LENGTH_LONG).show();

            nickname.setText("");
        }
        catch(IOException ioe) {
            Toast.makeText(this, "Error d'écriture", Toast.LENGTH_LONG).show();
        }

        lecture.setEnabled(true);

    }

    public void save(View v) {

        // Nettoyage des High Scores
        /*for(String key: sp.getAll().keySet()) {

            editor.remove(key);
            editor.apply();
        }*/

        texte = nickname.getText().toString();

        if(!texte.equals("")) {

            Integer id = 0;
            boolean userFound = false;

            for(String key: sp.getAll().keySet()) {

                String copyKey = key;
                boolean checkKey = copyKey.startsWith("nickname");

                if(checkKey) {

                    Integer intDetected = Integer.parseInt(key.replaceAll("[^0-9]", ""));
                    /*if(sp.getString(key, "nickname") == texte) {
                        userFound = true;
                        id = intDetected;
                        break;
                    }
                    else {*/
                        id = intDetected + 1;
                    //}
                }
            }

            editor.putString("nickname"+id, texte);
            editor.putString("level"+id, val);
            editor.putString("score"+id, chronoValue);
            editor.commit();

            Toast.makeText(this, "Score ajouté avec succès !", Toast.LENGTH_LONG).show();

            lecture.setEnabled(true);

        }
        else
        {
            Toast.makeText(this, "Merci de renseigner votre nickname", Toast.LENGTH_LONG).show();
        }

        /*editor.putString("nickname", texte);
        /*editor.putString("score", val);
        editor.putString("timer", chronoValue);*/
        // editor.commit();
        //lecture.setEnabled(true);
    }

    public void read(View v) {
        String data = new String();
        status.setText("");
        for(String key: sp.getAll().keySet()) {
            data = data+key+":"+sp.getString(key, "nickname")+"\n";
        }
        dataRead.setText(data);
    }

    public void verificateur(View v) {

        String filename = "nickname.txt";
        ArrayList<String> list = new ArrayList<String>();
        String tmp = "";

        // Lecture
        try {
            FileInputStream fileI = openFileInput(filename);
            int c;
            while( (c = fileI.read()) != -1 ) {
                if( Character.toString((char)c).equals("\n")) {
                    list.add(tmp);
                    tmp = "";
                }
                else
                {
                    tmp = tmp + Character.toString((char) c);
                }
            }

            Toast.makeText(this, "Lecture réussi ! :)", Toast.LENGTH_LONG).show();
        }
        catch(IOException ioe) {
            Toast.makeText(this, "Error de lecture ! :(", Toast.LENGTH_LONG).show();
        }

        status.setText("nombre de lignes "  + list.size());

        String data = "Liste des joueurs enregistrées \n\n";

        for(int i = 0; i < list.size(); i++) {

            data += " - " + list.get(i).toString() + "\n";

        }

        dataRead.setText(data);

    }

    public void goToHome(View v) {

        this.finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
