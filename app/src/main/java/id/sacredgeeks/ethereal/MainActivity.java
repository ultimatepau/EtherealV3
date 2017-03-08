package id.sacredgeeks.ethereal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Model.JadwalModel;
import Model.TugasModel;

public class MainActivity extends AppCompatActivity {
    Calendar calendar;
    TextView tugas,jadwal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String hari = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        JadwalModel lol = new JadwalModel(this);
        TugasModel lol2 = new TugasModel(this);

        tugas  = (TextView)findViewById(R.id.tvTugas);
        jadwal = (TextView)findViewById(R.id.tvJadwal);

        tugas.setText("Anda mempunyai "+lol2.getRow()+" tugas berjalan");
        jadwal.setText("Anda mempunyai "+lol.getRow()+" jadwal berjalan");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void Jadwal(View view) {
        Intent i = new Intent(MainActivity.this,ListJadwal.class);
        startActivity(i);
    }

    public void Tugas(View view) {
        Intent i = new Intent(MainActivity.this,ListTugas.class);
        startActivity(i);
    }

    public void Setting(View view) {
        Toast.makeText(this, "Kamu Klik Setting", Toast.LENGTH_SHORT).show();
    }

    public void Clan(View view) {
        Toast.makeText(this, "Kamu Klik Clan", Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        System.exit(0);
    }
}
