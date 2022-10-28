/**
 * @author S.M. Jayasekara
 * @IT_number IT19161648
 */

package com.example.ead_fuel_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ead_fuel_app.models.Shed;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.ShedService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShedOwnerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView shedName, shedCity, shedNo, petrol, diesel;
    private ProgressBar progressBar;
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
        shedName = findViewById(R.id.station_name);
        shedCity = findViewById(R.id.station_address);
        shedNo = findViewById(R.id.station_no);
        diesel = findViewById(R.id.tvDiesel);
        petrol = findViewById(R.id.tvPetrol);
        progressBar = findViewById(R.id.pB);

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
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                if (response.isSuccessful()) {
                    Shed shed = response.body();

                    assert shed != null;
                    String no = shed.getRegNo();
                    boolean havePetrol = shed.isPetrolAvailable();
                    boolean haveDiesel = shed.isDieselAvailable();

                    if (haveDiesel){
                        diesel.setText("Diesel Available");
                    } else {
                        diesel.setText("Diesel Not Available");
                    }

                    if (havePetrol){
                        petrol.setText("Petrol Available");
                    } else {
                        petrol.setText("Petrol Not Available");
                    }

                    shedNo.setText(no);
                    shedName.setText(shedNme);
                    shedCity.setText(shedAddress);

                    Toast.makeText(ShedOwnerActivity.this, "Retrieved Shed Data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                Toast.makeText(ShedOwnerActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.petrol_arrive:
                progressBar.setVisibility(View.VISIBLE);

                ShedService shedService = ServiceBuilder.buildService(ShedService.class);
                // update petrol arrival time
                Call<Shed> call = shedService.petrolArrived(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Petrol Arrival Updated!", Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                break;

            case R.id.petrol_finish:
                progressBar.setVisibility(View.VISIBLE);

                shedService = ServiceBuilder.buildService(ShedService.class);
                //update petrol finish time
                call = shedService.petrolFinished(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Petrol Finish Updated!", Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                break;

            case R.id.diesel_arrive:
                progressBar.setVisibility(View.VISIBLE);

                shedService = ServiceBuilder.buildService(ShedService.class);
                // update diesel arrival time
                call = shedService.dieselArrived(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Diesel Arrival Updated!", Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                break;
                
            case R.id.diesel_finish:
                progressBar.setVisibility(View.VISIBLE);

                shedService = ServiceBuilder.buildService(ShedService.class);
                //update diesel finish time
                call = shedService.dieselFinished(regNo);

                    call.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            Toast.makeText(ShedOwnerActivity.this, "Diesel Finish Updated!", Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedOwnerActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                            recreate();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                break;
        }
    }
}