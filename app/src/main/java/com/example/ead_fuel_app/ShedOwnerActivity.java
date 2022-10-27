/**
 * @author S.M. Jayasekara
 * @IT_number IT19161648
 */

package com.example.ead_fuel_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead_fuel_app.models.Shed;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.ShedService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShedOwnerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView sName, sAddress, sNo;
    String regNo;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_shed_owner);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        Button pArrived = findViewById(R.id.petrol_arrive);
        Button pFinished = findViewById(R.id.petrol_finish);
        Button dArrived = findViewById(R.id.diesel_arrive);
        Button dFinished = findViewById(R.id.diesel_finish);
        sName = findViewById(R.id.station_name);
        sAddress = findViewById(R.id.station_address);
        sNo = findViewById(R.id.station_no);

        String shedNme = sharedPreferences.getString("ShedSessionName", "No");
        String shedAddress = sharedPreferences.getString("ShedSessionAddress", "No");

        Intent intent = getIntent();
        regNo = intent.getStringExtra("regNo");

        getResults(shedNme,shedAddress);
        pArrived.setOnClickListener(this);
        pFinished.setOnClickListener(this);
        dArrived.setOnClickListener(this);
        dFinished.setOnClickListener(this);
    }

    private void getResults(String shedNme,String shedAddress) {

        ShedService shedService = ServiceBuilder.buildService(ShedService.class);
        //get shed details by register number
        Call<Shed> call = shedService.ShedById(regNo);

        call.enqueue(new Callback<Shed>() {
            @Override
            public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                if (response.isSuccessful()) {
                    Shed shed = response.body();

                    assert shed != null;
                    String no = shed.getRegNo();

                    sNo.setText(no);
                    sName.setText(shedNme);
                    sAddress.setText(shedAddress);

                    Log.d("Retrofit", response.toString());
                    Toast.makeText(ShedOwnerActivity.this, "Retrieved Shed Data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                Toast.makeText(ShedOwnerActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Retrofit", t.getMessage());
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.petrol_arrive:

                // update petrol arrival time
                ShedService shedService = ServiceBuilder.buildService(ShedService.class);
                Call<Shed> call = shedService.petrolArrived(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Petrol Arrival Updated!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                break;
            case R.id.petrol_finish:

                //update petrol finish time
                shedService = ServiceBuilder.buildService(ShedService.class);
                call = shedService.petrolFinished(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Petrol Finish Updated!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                break;
            case R.id.diesel_arrive:

                // update diesel arrival time
                shedService = ServiceBuilder.buildService(ShedService.class);
                call = shedService.dieselArrived(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Diesel Arrival Updated!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                break;
            case R.id.diesel_finish:
                //update diesel finish time
                shedService = ServiceBuilder.buildService(ShedService.class);
                call = shedService.dieselFinished(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Diesel Finish Updated!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                break;
        }
    }
}