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

    TextView shedName, shedRegNo, shedCity, fuelAvailability,
            arrivalTime, finishTime, queueLength;
    Button queue_join;
    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_queue_details);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        shedName = findViewById(R.id.shed_name);
        shedRegNo = findViewById(R.id.shed_reg_no);
        shedCity = findViewById(R.id.shed_address_display);
        fuelAvailability = findViewById(R.id.shed_fuel_availability);
        arrivalTime = findViewById(R.id.shed_fuel_arrival);
        finishTime = findViewById(R.id.shed_fuel_finish);
        queueLength = findViewById(R.id.shed_fuel_queue);

        queue_join = findViewById(R.id.queue_join);

        String reg = sharedPreferences.getString("Session", "No");


        Intent intent = getIntent();
        String shedName = intent.getStringExtra("shedname");
        String shedRegNo = intent.getStringExtra("shedregno");
        String shedCity = intent.getStringExtra("shedaddress");
        boolean isDieselAvailable = intent.getBooleanExtra("dieselavailability", true);
        boolean isPetrolAvailable = intent.getBooleanExtra("petrolavailability", true);
        String dieselArrivalTime = intent.getStringExtra("dieselarrival");
        String petrolArrivalTime = intent.getStringExtra("petrolarrival");
        String dieselFinishTime = intent.getStringExtra("dieselfinish");
        String petrolFinishTime = intent.getStringExtra("petrolfinish");
        int dieselQueueLength = intent.getIntExtra("dieselqueue",0);
        int petrolQueueLength = intent.getIntExtra("petrolqueue",0);
        String fuelType = intent.getStringExtra("fType");

        String diesellen = String.valueOf(dieselQueueLength);
        String petrollen = String.valueOf(petrolQueueLength);

        queue_join.setOnClickListener(view -> {

            Queue queue = new Queue("12345", shedRegNo, reg, fuelType, null, null, false);

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

                        Toast.makeText(QueueDetailsActivity.this, "You have Joined The Queue!", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(QueueDetailsActivity.this, QueueInActivity.class);
                        intent1.putExtra("fType",fuelType);
                        intent1.putExtra("shedRegNo", shedRegNo);
                        intent1.putExtra("shedNam", shedName);
                        intent1.putExtra("shedAdd", shedCity);
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

        this.shedName.setText(shedName);
        this.shedRegNo.setText(shedRegNo);
        this.shedCity.setText(shedCity);

        if (isDieselAvailable) {
            fuelAvailability.setText("Available");
        } else {
            fuelAvailability.setText("Not Available");
        }
        arrivalTime.setText(dieselArrivalTime);
        finishTime.setText(dieselFinishTime);
        queueLength.setText(diesellen);
    }
}