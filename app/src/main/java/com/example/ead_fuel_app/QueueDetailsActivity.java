/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ead_fuel_app.models.Queue;
import com.example.ead_fuel_app.service.QueueService;
import com.example.ead_fuel_app.service.ServiceBuilder;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueueDetailsActivity extends AppCompatActivity {

    TextView shed_name,shed_reg_no,shed_address_display,shed_fuel_availability,
            shed_fuel_arrival,shed_fuel_finish,shed_fuel_queue;
    Button queue_join;
    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_queue_details);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        shed_name = findViewById(R.id.shed_name);
        shed_reg_no = findViewById(R.id.shed_reg_no);
        shed_address_display = findViewById(R.id.shed_address_display);
        shed_fuel_availability = findViewById(R.id.shed_fuel_availability);
        shed_fuel_arrival = findViewById(R.id.shed_fuel_arrival);
        shed_fuel_finish = findViewById(R.id.shed_fuel_finish);
        shed_fuel_queue = findViewById(R.id.shed_fuel_queue);

        queue_join = findViewById(R.id.queue_join);

        String reg = sharedPreferences.getString("Session", "No");


        Intent intent = getIntent();
        String shedname = intent.getStringExtra("shedname");
        String shedregno = intent.getStringExtra("shedregno");
        String shedaddress = intent.getStringExtra("shedaddress");
        boolean dieselavailability = intent.getBooleanExtra("dieselavailability", true);
        boolean petrolavailability = intent.getBooleanExtra("petrolavailability", true);
        String dieselarrival = intent.getStringExtra("dieselarrival");
        String petrolarrival = intent.getStringExtra("petrolarrival");
        String dieselfinish = intent.getStringExtra("dieselfinish");
        String petrolfinish = intent.getStringExtra("petrolfinish");
        int dieselqueue = intent.getIntExtra("dieselqueue",0);
        int petrolqueue = intent.getIntExtra("petrolqueue",0);
        String fType = intent.getStringExtra("fType");

        String diesellen = String.valueOf(dieselqueue);
        String petrollen = String.valueOf(petrolqueue);

        queue_join.setOnClickListener(view -> {

            Queue queue = new Queue("12345", shedregno, reg, fType, null, null, false);

            QueueService queueService = ServiceBuilder.buildService(QueueService.class);
            //user insert to the queue
            Call<Queue> request = queueService.enterQueue(queue);

            request.enqueue(new Callback<Queue>() {
                @Override
                public void onResponse(@NonNull Call<Queue> call, @NonNull Response<Queue> response) {
                    if(response.isSuccessful()){

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        assert response.body() != null;
                        editor.putString("QueueID", response.body().getId());
                        editor.apply();

                        Toast.makeText(QueueDetailsActivity.this, "You Joined The Queue!", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(QueueDetailsActivity.this, QueueInActivity.class);
                        intent1.putExtra("fType",fType);
                        intent1.putExtra("shedRegNo", shedregno);
                        intent1.putExtra("shedNam", shedname);
                        intent1.putExtra("shedAdd", shedaddress);
                        startActivity(intent1);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Queue> call, @NonNull Throwable t) {
                    Log.d("FI", t.getMessage());
                }
            });
        });

        shed_name.setText(shedname);
        shed_reg_no.setText(shedregno);
        shed_address_display.setText(shedaddress);

        if (dieselavailability) {
            shed_fuel_availability.setText("Available");
        } else {
            shed_fuel_availability.setText("Not Available");
        }
        shed_fuel_arrival.setText(dieselarrival);
        shed_fuel_finish.setText(dieselfinish);
        shed_fuel_queue.setText(diesellen);
    }
}