package Form;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import DAO.Jadwal;
import Model.JadwalModel;
import alarm.MyAlarm;
import id.sacredgeeks.ethereal.ListJadwal;
import id.sacredgeeks.ethereal.R;

public class FormJadwal extends AppCompatActivity {
    private long date = 0;
    EditText jadwal,keterangan,jam;
    Spinner Hari;
    Button btntambah,btnHapus;
    Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_jadwal);

        jadwal = (EditText)findViewById(R.id.TxtJadwal);
        keterangan = (EditText)findViewById(R.id.TxtKeterangan);
        Hari = (Spinner)findViewById(R.id.Hari);
        jam = (EditText)findViewById(R.id.TxtJam);

        btnHapus = (Button)findViewById(R.id.btnHapus);

        String hari[] = {"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu"};
        ArrayAdapter<String> adapterHari = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hari);
        adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hari.setAdapter(adapterHari);

        setJam();

        btntambah = (Button)findViewById(R.id.btnTambah);

        cekListener();
        cekPK();
        setListenerHapus();
    }

    public void setListenerHapus() {
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                String PK = i.getStringExtra("PK");
                JadwalModel model = new JadwalModel(FormJadwal.this);
                model.Delete(Integer.parseInt(PK));
                Toast.makeText(FormJadwal.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public void cekListener() {
        Intent i = getIntent();
        final String PK = i.getStringExtra("PK");
        if(PK.equals("")) {
            btntambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JadwalModel model = new JadwalModel(FormJadwal.this);
                    Jadwal jadwall = new Jadwal();

                    jadwall.id = null;
                    jadwall.jadwal= jadwal.getText().toString();
                    jadwall.keterangan = keterangan.getText().toString();
                    jadwall.hari= Hari.getSelectedItem().toString();
                    jadwall.jam = jam.getText().toString();
                    model.Insert(jadwall);

                    SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");

                    try {
                        Date mydate = df.parse(jadwall.jam);
                        date = mydate.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    MyAlarm alarm = new MyAlarm();
                    alarm.setAlarm(FormJadwal.this, date, 0);

                    Toast.makeText(FormJadwal.this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        } else {
            btntambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JadwalModel model = new JadwalModel (FormJadwal.this);
                    Jadwal jadwall= new Jadwal();

                    jadwall.id = PK;
                    jadwall.jadwal= jadwal.getText().toString();
                    jadwall.keterangan = keterangan.getText().toString();
                    jadwall.hari= Hari.getSelectedItem().toString();
                    jadwall.jam = jam.getText().toString();

                    model.Update(jadwall);

                    Toast.makeText(FormJadwal.this, "Ubah Data Berhasil", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
    }

    public void cekPK() {
        Intent intent = getIntent();
        String PK = intent.getStringExtra("PK");

        if(PK.equals("")) {
//            Toast.makeText(this, "Ga Ada PK nya", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Ada PK nya", Toast.LENGTH_SHORT).show();
            btntambah.setText("Ubah Jadwal");
            btnHapus.setVisibility(View.VISIBLE);
            JadwalModel model = new JadwalModel(this);
            Jadwal data = new Jadwal();
            data = model.getJadwalById(Integer.parseInt(PK));

            jadwal.setText(data.jadwal);
            keterangan.setText(data.keterangan);
            selectValue(Hari,data.hari);
            jam.setText(data.jam);
        }
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void setJam() {
        jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FormJadwal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jam.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    public void onBackPressed() {
        Intent i = new Intent(FormJadwal.this, ListJadwal.class);
        startActivity(i);
    }
}
