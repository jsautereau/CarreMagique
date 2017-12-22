package com.example.jsautereau.carremagique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    //TextView resultat1, resultat2, resultat3, resultat4, resultat5, resultat6;
    //EditText input1, input2, input3, input4, input5, input6, input7, input8, input9;

    TextView resultat[] = new TextView[6]; // Résultats

    EditText input[] = new EditText[9]; // Inputs

    // Ma matrice
    Integer[][] matrice = new Integer[3][9];

    // Nb de 1 à 9
    Integer nb[] = new Integer[]{1,2,3,4,5,6,7,8,9};

    // Resultat content
<<<<<<< HEAD
    ArrayList<Integer> resultatContent = new ArrayList<Integer>();
=======
    int resultatContent[] = new int[]{1};
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb

    Button btnContinue, btnSubmit, btnNewGame, btnExitGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input[0] = findViewById(R.id.input1);
        input[1] = findViewById(R.id.input2);
        input[2] = findViewById(R.id.input3);
        input[3] = findViewById(R.id.input4);
        input[4] = findViewById(R.id.input5);
        input[5] = findViewById(R.id.input6);
        input[6] = findViewById(R.id.input7);
        input[7] = findViewById(R.id.input8);
        input[8] = findViewById(R.id.input9);

        resultat[0] = findViewById(R.id.resultat1);
        resultat[1] = findViewById(R.id.resultat2);
        resultat[2] = findViewById(R.id.resultat3);
        resultat[3] = findViewById(R.id.resultat4);
        resultat[4] = findViewById(R.id.resultat5);
        resultat[5] = findViewById(R.id.resultat6);

        // Mes boutons
        btnSubmit = findViewById(R.id.button1);
        btnContinue = findViewById(R.id.button2);
        btnNewGame = findViewById(R.id.button3);
        btnExitGame = findViewById(R.id.button4);
<<<<<<< HEAD
    }

    protected void onResume() {
        super.onResume();

        // on met à jour nos résultats tant que l'utilisateur n'as pas cliqué sur "Nouveau Jeu"
        if(resultatContent.size() > 0) {
            for(int i = 0; i < resultatContent.size(); i++) {
                resultat[i].setText(""+resultatContent.get(i));
=======

        // Au début on genere des nouveaux résultats
        if(resultatContent.length > 0) {
            for(int i = 0; i < resultat.length; i++) {
                resultat[i].setText(""+resultatContent[i]);
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
            }
        }
        else {
            genererResultat(1);
        }
    }

    protected void onSaveInstanceState(Bundle donnees) {

<<<<<<< HEAD
        donnees.putIntegerArrayList("appels", resultatContent);
=======
        donnees.putIntArray("resultatContent", resultatContent);
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
        super.onSaveInstanceState(donnees);
    }

    protected void onRestoreInstanceState(Bundle donnees) {

        super.onRestoreInstanceState(donnees);
<<<<<<< HEAD
        resultatContent = donnees.getIntegerArrayList("appels");
=======
        resultatContent = donnees.getIntArray("resultatContent");
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
    }

    public boolean isCorrect() {

        boolean valideH = false;
        boolean valideV = false;

        System.out.println(valideH);

        for(int i = 0; i < 3; i++) {

            int calculHorizontal = 0;

            for(int j = 0; j < 3; j++) {

                calculHorizontal += matrice[i][j];

                if (j == 2) {
                    if (calculHorizontal == Integer.parseInt(resultat[i].getText().toString())) {
                        valideH = true;
                    } else {
                        valideH = false;
                        break;
                    }
                }
            }

            if(!valideH) {
                break;
            }
        }

        for(int j = 0; j < 3; j++) {

            int calculVertical = 0;

            for(int i = 0; i < 3; i++) {

                calculVertical += matrice[i][j];

                if (i == 2) {
                    if (calculVertical == Integer.parseInt(resultat[3+i].getText().toString())) {
                        valideV = true;
                    } else {
                        valideV = false;
                        break;
                    }
                }
            }

            if(!valideV) {
                break;
            }
        }

        if(valideH && valideV) {
            return valideH; // return true toujours
        }
        else
        {
            return false;
        }

    }

    protected void afficherToast(String mesg) {
        Toast msg = Toast.makeText(this, mesg, Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.CENTER, 0, 0);
        msg.show();
    }

    // Fonction permettant de générer des résultats aléatoires parmi les valeurs du carré magique
    public void genererResultat(int level) {

<<<<<<< HEAD
        // on vite notre liste dès le début
        resultatContent.removeAll(resultatContent);

=======
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
        long seed = System.nanoTime();
        Collections.shuffle(Arrays.asList(nb), new Random(seed));

        int ligneSuivante = 0;
        int colonneSuivante = 0;
        int calcul = 0;
        for(int i = 0; i < 6; i++) {

            if(i < 3) {

                calcul = nb[0+ligneSuivante]+nb[1+ligneSuivante]+nb[2+ligneSuivante];

                resultat[i].setText(""+calcul);
<<<<<<< HEAD
                resultatContent.add(calcul);
=======
                resultatContent[i] = calcul;
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
            }
            else {

                calcul = nb[0+colonneSuivante]+nb[3+colonneSuivante]+nb[6+colonneSuivante];
                colonneSuivante += 1;
                resultat[i].setText(""+calcul);
<<<<<<< HEAD
                resultatContent.add(calcul);
=======
                resultatContent[i] = calcul;
>>>>>>> cbf8d7bdf4c11d0bd8b7431ae8b3f34fb0be8dbb
            }

            ligneSuivante += 3;
        }
    }

    // Nouveau Jeu
    public void newGame(View v) {

        enableMode();
        for(int i = 0; i < input.length; i++) {
            input[i].setText("");
        }
        genererResultat(1);

    }

    public void ajoutMatrice() {

        int incr = 0;

        int etat = 0;

        // on rempli notre matrice
        for(int i = 0; i < 3; i++) {

            for(int j = 0; j < 3; j++) {

                System.out.println(input[j+incr].getText().toString());

                if(input[j+incr].getText().toString().equals("")) {
                    System.out.println(input[j+incr].getText().toString());
                    matrice[i][j] = null;
                    break;
                }
                else
                {
                    matrice[i][j] = Integer.parseInt(input[j+incr].getText().toString());
                    System.out.println(matrice[i][j]);
                }
            }

            incr += 3;
        }
    }

    public boolean verifMatrice() {

        boolean valide = true;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(matrice[i][j] == null) {
                    valide = false;
                    break;
                }
            }
        }

        return valide;
    }

    public boolean isUnique() {

        boolean valide = true;

        Integer inputValidation[] = new Integer[9];

        for(int i = 0; i < input.length; i++) {
            inputValidation[i] = Integer.parseInt(input[i].getText().toString());
        }

        Set<Integer> setUniqueNumbers = new LinkedHashSet<Integer>();
        for(Integer i : inputValidation) {
            setUniqueNumbers.add(i);
        }

        if(setUniqueNumbers.size() != inputValidation.length) {
            valide = false;
        }

        return valide;
    }

    public void modeResume() {

        btnContinue.setEnabled(true);

        for(int i = 0; i < input.length; i++) {
            input[i].setEnabled(false);
        }

        btnSubmit.setEnabled(false);
    }

    public void enableMode() {

        for(int i = 0; i < input.length; i++) {
            input[i].setEnabled(true);
        }

        btnSubmit.setEnabled(true);
        btnContinue.setEnabled(false);
    }

    public void doContinue(View v) {

        enableMode();
    }

    public void exitGame(View v) {

        this.finish();
    }

    public void testCalcul( View v) {

        ajoutMatrice();

        if(!verifMatrice()){
            afficherToast("Merci de renseigner toutes les cases SVP !");
        }
        else if(!isUnique()) {
            afficherToast("Tous les chiffres renseignés doivent être unique !");
        }
        else {
            if(isCorrect()) {
                afficherToast("It's correct");
                modeResume();
            }
            else
            {
                afficherToast("It's incorrect");
                modeResume();
            }

        }
    }
}
