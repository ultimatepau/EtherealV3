package Form;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DAO.Tugas;
import Model.TugasModel;
import alarm.MyAlarm;
import id.sacredgeeks.ethereal.ListTugas;
import id.sacredgeeks.ethereal.R;

public class FormTugas extends AppCompatActivity {
    private long date =0;
    EditText tugas,keterangan,tanggal,jam;
    Button btntambah,btnhapus;
    Calendar myCalendar;
    MyAlarm alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tugas);

        tugas = (EditText)findViewById(R.id.TxtTugas);
        keterangan = (EditText)findViewById(R.id.TxtKeterangan);
        tanggal = (EditText)findViewById(R.id.TxtTanggal);
        jam = (EditText)findViewById(R.id.TxtJam);

        alarm = new MyAlarm();

        btnhapus = (Button)findViewById(R.id.btnHapus);

        setJam();
        setTanggal();

        btntambah = (Button)findViewById(R.id.btnTambah);

        cekListener();
        cekPK();
        setListenerHapus();
    }

    public void setListenerHapus() {
        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                String PK = i.getStringExtra("PK");
                TugasModel model = new TugasModel(FormTugas.this);
                model.Delete(Integer.parseInt(PK));
                alarm.cancelAlarm(FormTugas.this, Integer.parseInt(PK));
                Toast.makeText(FormTugas.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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
                    TugasModel model = new TugasModel(FormTugas.this);
                    Tugas tgs = new Tugas();

                    tgs.id = null;
                    tgs.tugas = tugas.getText().toString();
                    tgs.keterangan = keterangan.getText().toString();
                    tgs.tanggal = tanggal.getText().toString();
                    tgs.jam = jam.getText().toString();
                    model.Insert(tgs);

                    Toast.makeText(FormTugas.this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

                    try {
                        Date mydate = df.parse(tgs.tanggal+" "+tgs.jam);
                        date = mydate.getTime();
                        Log.wtf("onClick: ", String.valueOf(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    TugasModel tm = new TugasModel(FormTugas.this);
                    int lastId = tm.getLastId();
                    alarm.setAlarm(FormTugas.this, date, tm.getLastId());


                    onBackPressed();
                }
            });
        } else {
            btntambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TugasModel model = new TugasModel(FormTugas.this);
                    Tugas tgs = new Tugas();

                    tgs.id = PK;
                    tgs.tugas = tugas.getText().toString();
                    tgs.keterangan = keterangan.getText().toString();
                    tgs.tanggal = tanggal.getText().toString();
                    tgs.jam = jam.getText().toString();
                    model.Update(tgs);

                    Toast.makeText(FormTugas.this, "Ubah Data Berhasil", Toast.LENGTH_SHORT).show();
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
            btntambah.setText("Ubah Tugas");
            btnhapus.setVisibility(View.VISIBLE);
            TugasModel model = new TugasModel(this);
            Tugas data = new Tugas();
            data = model.getTugasById(Integer.parseInt(PK));

            tugas.setText(data.tugas);
            keterangan.setText(data.keterangan);
            tanggal.setText(data.tanggal);
            jam.setText(data.jam);
        }
    }

    public void setTanggal() {
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                tanggal.setText(sdf.format(myCalendar.getTime()));

            }

        };


        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FormTugas.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    public void setJam() {
        jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FormTugas.this, new TimePickerDialog.OnTimeSetListener() {
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
        Intent i = new Intent(FormTugas.this, ListTugas.class);
        startActivity(i);
    }
}
