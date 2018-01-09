package com.example.juliensautereau.carremagique;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ScoreTab extends AppCompatActivity {

    ListView listFacile, listMoyen, listDifficile;

    String[] modeFacileTri = new String[]{"coucou"};
    ArrayList<String> modeMoyenTri = new ArrayList<String>();
    ArrayList<String> modeDifficileTri = new ArrayList<String>();

    String[] cars = new String[]{"Coucou", "Sa marche pas"};

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tab);

        listFacile = findViewById(R.id.listViewFacile);
        listMoyen = findViewById(R.id.listViewMoyen);
        listDifficile = findViewById(R.id.listViewDifficile);

        sp = getSharedPreferences("ExitGame", MODE_PRIVATE); editor = sp.edit();

        //String data = new String();
        for(String key: sp.getAll().keySet()) {
            //data = data+key+":"+sp.getString(key, "nickname")+"\n";
            for(int i = 0; i < 2; i++) {
                modeFacileTri = append(modeFacileTri, sp.getString(key, "nickname"+i));
            }
            //System.out.println(key);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeFacileTri);

        listFacile.setAdapter(adapter);

    }
}
