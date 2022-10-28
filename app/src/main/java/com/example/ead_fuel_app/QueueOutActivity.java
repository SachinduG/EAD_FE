/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class QueueOutActivity extends AppCompatActivity implements View.OnClickListener {

    TextView shedName, shedCity;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_queue_out);

        Intent intent = getIntent();
        String shedName = intent.getStringExtra("sName");
        String shedAdd = intent.getStringExtra("sAdd");

        this.shedName = findViewById(R.id.shed_name);
        shedCity = findViewById(R.id.shed_address_display);
        btnHome = findViewById(R.id.home);

        this.shedName.setText(shedName);
        shedCity.setText(shedAdd);

        btnHome.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.home) {
            startActivity(new Intent(QueueOutActivity.this, MainActivity.class));
        }
    }
}