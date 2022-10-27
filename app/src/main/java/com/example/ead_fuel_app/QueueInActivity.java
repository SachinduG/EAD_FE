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
    TextView shed_waiting_time,shed_name,shed_address_display,shed_fuel_type;
    Button queue_exit_after_fuel,queue_exit_no_fuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_queue_in);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        shed_waiting_time = findViewById(R.id.shed_waiting_time);
        shed_name = findViewById(R.id.shed_name);
        shed_address_display = findViewById(R.id.shed_address_display);
        shed_fuel_type = findViewById(R.id.shed_fuel_type);

        queue_exit_after_fuel = findViewById(R.id.queue_exit_after_fuel);
        queue_exit_no_fuel = findViewById(R.id.queue_exit_no_fuel);

        queue_exit_after_fuel.setOnClickListener(this);
        queue_exit_no_fuel.setOnClickListener(this);

        Intent intent = getIntent();
        String shedregno = intent.getStringExtra("shedRegNo");
        String fType = intent.getStringExtra("fType");
        String shedName = intent.getStringExtra("shedNam");
        String shedAdd = intent.getStringExtra("shedAdd");

        shed_name.setText(shedName);
        shed_address_display.setText(shedAdd);
        shed_fuel_type.setText(fType);

        getAvergeWaitingTime(shedregno, fType);

    }

    // get average time for each selected queue
    private void getAvergeWaitingTime(String shedregno, String fType) {
        QueueService queueService = ServiceBuilder.buildService(QueueService.class);

        Call<Long> request = queueService.AvgWaitingTimeByIdType(shedregno, fType);

        request.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if(response.isSuccessful()){
                    shed_waiting_time.setText(String.valueOf(response.body()));
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
                String queueId = sharedPreferences.getString("QueueID", "No");

                QueueService queueService = ServiceBuilder.buildService(QueueService.class);
                //user leave from the queue
                Call<Queue> request = queueService.leaveQueue(queueId);

                request.enqueue(new Callback<Queue>() {
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
                startActivity(new Intent(getApplicationContext(), QueueOutActivity.class));
                break;
        }
    }
}