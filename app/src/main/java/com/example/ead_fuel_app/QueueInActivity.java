/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead_fuel_app.models.Queue;
import com.example.ead_fuel_app.service.QueueService;
import com.example.ead_fuel_app.service.ServiceBuilder;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueueInActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    TextView waitingTime, shedName, shedCity, fuelType;
    Button exitAfter, exitBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_queue_in);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        waitingTime = findViewById(R.id.shed_waiting_time);
        shedName = findViewById(R.id.shed_name);
        shedCity = findViewById(R.id.shed_address_display);
        fuelType = findViewById(R.id.shed_fuel_type);

        exitAfter = findViewById(R.id.queue_exit_after_fuel);
        exitBefore = findViewById(R.id.queue_exit_no_fuel);

        exitAfter.setOnClickListener(this);
        exitBefore.setOnClickListener(this);

        Intent intent = getIntent();
        String shedregno = intent.getStringExtra("shedRegNo");
        String fType = intent.getStringExtra("fType");
        String shedName = intent.getStringExtra("shedNam");
        String shedAdd = intent.getStringExtra("shedAdd");

        this.shedName.setText(shedName);
        shedCity.setText(shedAdd);
        fuelType.setText(fType);

        getAvergeWaitingTime(shedregno, fType);

    }

    private void getAvergeWaitingTime(String shedregno, String fType) {
        QueueService queueService = ServiceBuilder.buildService(QueueService.class);
        // get average waiting time of shed using shed Id and fuel type
        Call<Long> request = queueService.AvgWaitingTimeByIdType(shedregno, fType);

        request.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if(response.isSuccessful()){
                    waitingTime.setText(String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                Toast.makeText(QueueInActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.queue_exit_after_fuel:
                String queueId1 = sharedPreferences.getString("QueueID", "No");

                QueueService queueService1 = ServiceBuilder.buildService(QueueService.class);
                //user leave from the queue after pumping fuel
                Call<Queue> call1 = queueService1.leaveQueue(queueId1);

                call1.enqueue(new Callback<Queue>() {
                    @Override
                    public void onResponse(@NonNull Call<Queue> call, @NonNull Response<Queue> response) {
                        Toast.makeText(QueueInActivity.this, "You have left the queue!", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        String shedName = intent.getStringExtra("shedNam");
                        String shedAdd = intent.getStringExtra("shedAdd");
                        Intent intent2 = new Intent(getApplicationContext(), QueueOutActivity.class);
                        intent2.putExtra("sName", shedName);
                        intent2.putExtra("sAdd", shedAdd);
                        startActivity(intent2);
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Queue> call, @NonNull Throwable t) {
                        Toast.makeText(QueueInActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.queue_exit_no_fuel:
                String queueId2 = sharedPreferences.getString("QueueID", "No");

                QueueService queueService2 = ServiceBuilder.buildService(QueueService.class);
                //user leave from the queue before pumping fuel
                Call<Queue> call2 = queueService2.leaveEarlyQueue(queueId2);

                call2.enqueue(new Callback<Queue>() {
                    @Override
                    public void onResponse(@NonNull Call<Queue> call, @NonNull Response<Queue> response) {
                        Toast.makeText(QueueInActivity.this, "You have left the queue before pump fuel!", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        String shedName = intent.getStringExtra("shedNam");
                        String shedAdd = intent.getStringExtra("shedAdd");
                        Intent intent2 = new Intent(getApplicationContext(), QueueOutActivity.class);
                        intent2.putExtra("sName", shedName);
                        intent2.putExtra("sAdd", shedAdd);
                        startActivity(intent2);
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Queue> call, @NonNull Throwable t) {
                        Toast.makeText(QueueInActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}