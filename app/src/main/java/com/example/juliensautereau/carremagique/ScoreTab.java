package com.example.juliensautereau.carremagique;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ScoreTab extends AppCompatActivity {

    // Nos ListView
    ListView listFacile, listMoyen, listDifficile;

    // Variables pour l'adapter des ListView
    String[] modeFacileTri = new String[]{};
    String[] modeMoyenTri = new String[]{};
    String[] modeDifficileTri = new String[]{};

    // Fichiers partagés
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    // Ajout des élements à notre liste
    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    // Classe permettant de représenter les différents scores enregistrées.
    class Scoreboard
    {
        int score;
        String nickname, level, scoreString;

        // Constructor
        public Scoreboard(int score, String nickname,
                       String level, String scoreString)
        {
            this.score = score;
            this.nickname = nickname;
            this.level = level;
            this.scoreString = scoreString;
        }

        // Used to print student details in main()
        public String toString()
        {
            return "[ " + this.nickname + " ]" + " Temps : " + this.scoreString;
        }
    }

    // Classe permettant de trier les scores pour les différents modes
    class Sortbyroll implements Comparator<Scoreboard>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Scoreboard a, Scoreboard b)
        {
            return a.score - b.score;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tab);

        listFacile = findViewById(R.id.listViewFacile);
        listMoyen = findViewById(R.id.listViewMoyen);
        listDifficile = findViewById(R.id.listViewDifficile);

        sp = getSharedPreferences("ExitGame", MODE_PRIVATE); editor = sp.edit();

        triClassementScore();

        // Store les nickname, mode, score dans un tableau pour chacun (utilisation d'une classe nécessaire pour utilisation de Array.sorts)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeFacileTri);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeMoyenTri);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeDifficileTri);

        // On set les scoreboards dans les bonnes ListView
        listFacile.setAdapter(adapter);
        listMoyen.setAdapter(adapter1);
        listDifficile.setAdapter(adapter2);

    }

    // Méthode globale permettant d'afficher que les 3 premiers meilleures scores pour les modes "FACILE", "MOYEN" et "DIFFICILE"
    public void triClassementScore() {

        Scoreboard[] arrFacile = {};
        Scoreboard[] arrMoyen = new Scoreboard[]{};
        Scoreboard[] arrDifficile = new Scoreboard[]{};

        int i = 0;

        for(String key: sp.getAll().keySet()) {

            String copyKey = key;
            boolean checkKey = copyKey.startsWith("nickname");

            String levelShow = sp.getString("level"+i, "level"+i);
            String scoreShow = sp.getString("score"+i, "score"+i).replaceAll(":", "");

            if(checkKey) {

                int timeLow = Integer.parseInt(scoreShow);

                switch(levelShow) {

                    case "FACILE":
                        arrFacile = append(arrFacile, new Scoreboard(timeLow, sp.getString("nickname"+i, "nickname"+i), sp.getString("level"+i, "level"+i), sp.getString("score"+i, "score"+i)));
                        break;
                    case "MOYEN":
                        arrMoyen = append(arrMoyen, new Scoreboard(timeLow, sp.getString("nickname"+i, "nickname"+i), sp.getString("level"+i, "level"+i), sp.getString("score"+i, "score"+i)));
                        break;
                    case "DIFFICILE":
                        arrDifficile = append(arrDifficile, new Scoreboard(timeLow, sp.getString("nickname"+i, "nickname"+i), sp.getString("level"+i, "level"+i), sp.getString("score"+i, "score"+i)));
                        break;
                }

                i++;
            }

        }

        Arrays.sort(arrFacile, new Sortbyroll());

        int identifiant = 0;

        /* Affiche les 3 premiers (meilleurs scores) (Mode Difficile) */
        for (int j=0; j<arrFacile.length; j++) {

            if(identifiant <= 2) {
                modeFacileTri = append(modeFacileTri, arrFacile[j].toString());
            }
            identifiant += 1;
        }

        if(modeFacileTri.length == 0) {
            modeFacileTri = append(modeFacileTri, "Pas de score pour le mode Facile");
        }

        identifiant = 0;

        /* Affiche les 3 premiers (meilleurs scores) (Mode Moyen) */
        for (int j=0; j<arrMoyen.length; j++) {
            if(identifiant <= 2) {
                String donnees = arrMoyen[j].toString();
                modeMoyenTri = append(modeMoyenTri, donnees);
            }
            identifiant += 1;
        }

        if(modeMoyenTri.length == 0) {
            modeMoyenTri = append(modeMoyenTri, "Pas de score pour le mode Moyen");
        }

        identifiant = 0;

        /* Affiche les 3 premiers (meilleurs scores) (Mode Difficile) */
        for (int j=0; j<arrDifficile.length; j++) {
            if(identifiant <= 2) {
                modeDifficileTri = append(modeDifficileTri, arrDifficile[j].toString());
            }
            identifiant += 1;
        }

        if(modeDifficileTri.length == 0) {
            modeDifficileTri = append(modeDifficileTri, "Pas de score pour le mode Difficile");
        }
    }
}
