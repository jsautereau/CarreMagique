package com.example.juliensautereau.carremagique;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    List<Integer> myList = new ArrayList<Integer>();

    TextView resultat[] = new TextView[6]; // Résultats

    EditText input[] = new EditText[9]; // Inputs

    // Ma matrice
    Integer[][] matrice = new Integer[3][9];

    // Nb de 1 à 9
    Integer nb[] = new Integer[]{1,2,3,4,5,6,7,8,9};

    ArrayList<Integer> positionBlocked = new ArrayList<Integer>();

    static int blockedInput = 6;

    int level = 1;
    String levelString = "FACILE";

    // Resultat content
    ArrayList<Integer> resultatContent = new ArrayList<Integer>();

    ArrayList<String> textFieldContent = new ArrayList<String>();

    long timer;

    Button btnContinue, btnSubmit, btnNewGame, btnExitGame;

    Chronometer chrono;

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

        chrono = findViewById(R.id.chronometre);

        Intent i = getIntent();

        String val = i.getStringExtra("niveau_choisi");

        switch(val) {
            case "FACILE": level = 1; levelString = val; blockedInput = 6; break;
            case "MOYEN": level = 2; levelString = val; blockedInput = 3; break;
            case "DIFFICILE": level = 3; levelString = val; blockedInput = 0; break;
            default: level = 1; levelString = "FACILE"; blockedInput = 6; break;
        }

    }

    // Méthode onResume()
    protected void onResume() {
        super.onResume();

        // on met à jour nos résultats tant que l'utilisateur n'as pas cliqué sur "Nouveau Jeu"
        if (resultatContent.size() > 0) {
            for (int i = 0; i < resultatContent.size(); i++) {
                resultat[i].setText("" + resultatContent.get(i));
            }
            if(textFieldContent.size() > 0) {
                for (int i = 0; i < textFieldContent.size(); i++) {
                    input[i].setText("" + textFieldContent.get(i));
                }
            }

            for(int i = 0; i < input.length; i++) {

                if (positionBlocked.contains(i)) {

                    input[i].setEnabled(false);
                }
            }

            chrono.stop();
            chrono.start();
        }
        else
        {
            genererResultat(level);
            chrono.start();
        }

    }

    // Méthode permettant de sauvegarder l'instance des différents états
    protected void onSaveInstanceState(Bundle donnees) {

        donnees.putIntegerArrayList("appels", resultatContent);
        donnees.putStringArrayList("saisie_text", textFieldContent);
        donnees.putIntegerArrayList("positionBlocked", positionBlocked);
        donnees.putLong("chrono_value", chrono.getBase());
        super.onSaveInstanceState(donnees);
    }

    // Méthode permettant de restaurer l'instance des différents états
    protected void onRestoreInstanceState(Bundle donnees) {

        super.onRestoreInstanceState(donnees);
        resultatContent = donnees.getIntegerArrayList("appels");
        textFieldContent = donnees.getStringArrayList("saisie_text");
        positionBlocked = donnees.getIntegerArrayList("positionBlocked");
        chrono.setBase(donnees.getLong("chrono_value"));

    }

    // Méthode de vérifier que le résultat est correct à l'horizontal comme à la vertical
    public boolean isCorrect() {

        boolean valideH = false;
        boolean valideV = false;

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
                    if (calculVertical == Integer.parseInt(resultat[3+j].getText().toString())) {
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

    // Méthode permettant d'afficher un message à l'utilisateur
    protected void afficherToast(String mesg) {
        Toast msg = Toast.makeText(this, mesg, Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.CENTER, 0, 0);
        msg.show();
    }

    // Fonction permettant d'ajouter des élements dans ma liste d'entier
    public void addElement(Integer x) {
        myList.add(x);
    }

    // Fonction permettant de générer des cases aléatoires
    public void getRandomInput(Integer[] array) {

        int checkBlocked = 0;

        boolean exitWhile = true;

        if(blockedInput != 0) {

            while (exitWhile) {

                int rnd = new Random().nextInt(array.length);

                if (!myList.contains(rnd) && rnd != 0) {

                    addElement(rnd);
                    System.out.println(myList);
                    checkBlocked += 1;

                }

                if (checkBlocked == blockedInput) {
                    exitWhile = false;
                }

            }

        }

        for(int i = 0; i < input.length; i++) {

            if(myList.contains(array[i])) {
                positionBlocked.add(i);
                input[i].setText(""+array[i]);
                input[i].setEnabled(false);
            }
        }
    }

    // Fonction permettant de générer des résultats aléatoires parmi les valeurs du carré magique
    public void genererResultat(int level) {

        // on vite notre liste dès le début
        resultatContent.removeAll(resultatContent);
        textFieldContent.removeAll(textFieldContent);
        myList.removeAll(myList);


        long seed = System.nanoTime();
        Collections.shuffle(Arrays.asList(nb), new Random(seed));

        int ligneSuivante = 0;
        int colonneSuivante = 0;
        int calcul = 0;
        for(int i = 0; i < 6; i++) {

            if(i < 3) {

                calcul = nb[0+ligneSuivante]+nb[1+ligneSuivante]+nb[2+ligneSuivante];

                resultat[i].setText(""+calcul);
                resultatContent.add(calcul);
          //      resultatContent[i] = calcul;
            }
            else {

                calcul = nb[0+colonneSuivante]+nb[3+colonneSuivante]+nb[6+colonneSuivante];
                colonneSuivante += 1;
                resultat[i].setText(""+calcul);
                resultatContent.add(calcul);
            //    resultatContent[i] = calcul;

            }

            ligneSuivante += 3;
        }

        getRandomInput(nb);
    }

    // Nouveau Jeu
    public void newGame(View v) {

        positionBlocked.removeAll(positionBlocked);
        enableMode();
        for(int i = 0; i < input.length; i++) {
            input[i].setText("");
        }

        genererResultat(level);

        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();

    }

    // Méthode permettant de mettre à jour la matrice selon les données reçues
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

    // Méthode permettant de vérifier la matrice
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

    // Méthode permettant de vérifier que la saisie comporte que des chiffres uniques de 1 à 9
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

    // Méthode permettant d'empêcher l'utilisateur de faire un précédent avec le bouton
    public void onBackPressed() {
        // ne rien faire
    }

    // Mode resume quand l'utilisateur clique sur "Submit"
    public void modeResume() {

        timer = SystemClock.elapsedRealtime();
        chrono.stop();
        btnContinue.setEnabled(true);

        for(int i = 0; i < input.length; i++) {
            input[i].setEnabled(false);
        }

        btnSubmit.setEnabled(false);
    }

    // Mode enable permettant de réactiver les boutons et les cases
    public void enableMode() {

        for(int i = 0; i < input.length; i++) {
            if (positionBlocked.contains(i)) {

                input[i].setEnabled(false);
            }
            else
            {
                input[i].setEnabled(true);
            }
        }

        btnSubmit.setEnabled(true);
        btnContinue.setEnabled(false);

        chrono.setBase(chrono.getBase() + SystemClock.elapsedRealtime() - timer);
        chrono.start();
    }

    // Méthode permettant de continuer la partie
    public void doContinue(View v) {

        enableMode();
    }

    // Méthode permettant de quitter le jeu et revenir au menu principal
    public void exitGame(View v) {

        this.finish();
    }

    // Méthode permettant de stocker les différents inputs dans une liste
    public void storeTextField() {

        for(int i = 0; i < input.length; i++) {

            textFieldContent.add(input[i].getText().toString());
        }

    }

    // Méthode globale permettant de tester le calcul
    public void testCalcul( View v) {

        ajoutMatrice();

        storeTextField();

        if(!verifMatrice()){
            afficherToast("Merci de renseigner toutes les cases SVP !");
        }
        else if(!isUnique()) {
            afficherToast("Tous les chiffres renseignés doivent être unique !");
        }
        else {
            if(isCorrect()) {
                chrono.stop();  // On arrête le chronomètre
                afficherToast("It's correct");
                this.finish();
                Intent endGame = new Intent(this, ExitGame.class);
                endGame.putExtra("niveau_choisi", levelString);
                endGame.putExtra("time_resolv", chrono.getText().toString());
                startActivityForResult(endGame, RESULT_OK);
            }
            else
            {
                afficherToast("It's incorrect");
                modeResume();
            }

        }
    }

    // Méthode permettant de recevoir une validition de la requête reçu concernant un Intent père
    protected void onActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK){
            if (resultCode == RESULT_OK){

                levelString = data.getExtras().getString("niveau_choisi");
                chrono.setText(data.getExtras().getString("time_resolv"));
            }
        }
    }
}
