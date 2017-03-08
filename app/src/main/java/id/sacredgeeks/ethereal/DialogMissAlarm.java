package id.sacredgeeks.ethereal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static id.sacredgeeks.ethereal.R.id.jam;

public class DialogMissAlarm extends AppCompatActivity {
    TextView keter,jams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_miss_alarm);

        keter = (TextView)findViewById(R.id.keterangan);
        jams = (TextView)findViewById(jam);

        Intent i = getIntent();
        keter.setText(i.getStringExtra("ket"));
        jams.setText(i.getStringExtra("jam"));

    }

    public void dismiss(View view) {
//        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
