package id.sacredgeeks.ethereal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DialogMissAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_miss_alarm);
    }

    public void dismiss(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
