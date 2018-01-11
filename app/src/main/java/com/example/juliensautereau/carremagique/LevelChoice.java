package com.example.juliensautereau.carremagique;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LevelChoice extends AppCompatActivity {

    // TextView : Title de l'Intent
    TextView tv;

    // Choix du spinner
    String[] level={"<Choisir le niveau>", "FACILE", "MOYEN", "DIFFICILE"};

    // Drop Down
    Spinner spinner;

    // Level Selected
    String levelSelected = "";

    // Bouton envoyer
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choice);

        tv = findViewById(R.id.title_level);
        spinner = findViewById(R.id.spinnerRegion);
        buttonSend = findViewById(R.id.validateLevel);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,level);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapterR);


    }

    // Méthode permettant de choisir le niveau du joueur
    public void sendLevel(View v) {

        levelSelected = spinner.getSelectedItem().toString();

        if(levelSelected != "<Choisir le niveau>") {
            this.finish();
            Intent game = new Intent(this, MainActivity.class);
            game.putExtra("niveau_choisi", levelSelected);
            startActivityForResult(game, RESULT_OK);
        }
        else {
            Toast.makeText(this, "Veuillez choisir un niveau SVP", Toast.LENGTH_SHORT).show();
        }

    }

    // Méthode permettant de faire un retour à la page Home
    public void backPage(View v) {

        this.finish();
    }

    // Méthode permettant de recevoir une validition de la requête reçu concernant un Intent père
    protected void onActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK){
            if (resultCode == RESULT_OK){

                levelSelected = data.getExtras().getString("niveau_choisi");
            }
        }
    }
}
