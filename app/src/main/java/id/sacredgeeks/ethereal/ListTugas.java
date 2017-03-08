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

import Adapter.TugasAdapter;
import DAO.Tugas;
import form.FormTugas;
import Model.TugasModel;

public class ListTugas extends AppCompatActivity{
    ArrayList<HashMap<String, String>> data;
    FloatingActionButton btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tugas);

        btnTambah = (FloatingActionButton) findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListTugas.this, FormTugas.class);
                i.putExtra("PK","");
                startActivity(i);
            }
        });

        readData();
    }

    public void readData() {
        TugasModel model = new TugasModel(this);

        ArrayList<HashMap<String, String>> tugasList = model.getTugasList();

        if(tugasList.size() != 0) {
            data = tugasList;
        } else {
            data = new ArrayList<HashMap<String, String>>();
            Toast.makeText(this, "Data Kosong!", Toast.LENGTH_SHORT).show();
        }

        TugasAdapter tugasAdapter= new TugasAdapter(this,data,R.layout.item_tugas);
        ListView list = (ListView) findViewById(R.id.listTugas);
        list.setAdapter(tugasAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ID = data.get(i).get(Tugas.KEY_ID);
                Intent intent = new Intent(ListTugas.this, FormTugas.class);
                intent.putExtra("PK",""+ID);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        Intent i = new Intent(ListTugas.this, MainActivity.class);
        startActivity(i);
    }
}
