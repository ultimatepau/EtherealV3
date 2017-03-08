package id.sacredgeeks.ethereal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.JadwalAdapter;
import DAO.Jadwal;
import form.FormJadwal;
import Model.JadwalModel;

public class ListJadwal extends AppCompatActivity {
    ArrayList<HashMap<String, String>> data;
    FloatingActionButton btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jadwal);

        btnTambah = (FloatingActionButton) findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListJadwal.this, FormJadwal.class);
                i.putExtra("PK","");
                startActivity(i);
            }
        });

        readData();
    }

    public void readData() {
        JadwalModel model = new JadwalModel(this);

        ArrayList<HashMap<String, String>> jadwalList = model.getJadwalList();

        if (jadwalList.size() != 0) {
            data = jadwalList;
        } else {
            data = new ArrayList<HashMap<String, String>>();
            Toast.makeText(this, "Data Kosong!", Toast.LENGTH_SHORT).show();
        }

        JadwalAdapter jadwalAdapter = new JadwalAdapter(this, data, R.layout.item_jadwal);
        ListView list = (ListView) findViewById(R.id.listjadwal);
        list.setAdapter(jadwalAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ID = data.get(i).get(Jadwal.KEY_ID);
                Intent intent = new Intent(ListJadwal.this,FormJadwal.class);
                intent.putExtra("PK",""+ID);
                startActivity(intent);

            }
        });
    }

    public void onBackPressed() {
        Intent i = new Intent(ListJadwal.this, MainActivity.class);
        startActivity(i);
    }
}
