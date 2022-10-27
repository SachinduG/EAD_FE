/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead_fuel_app.models.Shed;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.ShedService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueueListActivity extends AppCompatActivity implements ShedSelectListener{

    SharedPreferences sharedPreferences;
    TextInputLayout textInputLayout, textInputLayout2;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;
    TextView city, fuel, select_fuel;
    ArrayList<Shed> shedList;
    RecyclerView recyclerView;
    ShedRecyclerAdapter shedRecyclerAdapter;
    LinearLayout linearFuel;
    String shedAdd;
    String fuelT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_queue_list);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        textInputLayout = findViewById(R.id.textInput);
        textInputLayout2 = findViewById(R.id.textInputType);
        autoCompleteTextView = findViewById(R.id.autoComplete);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteType);
        city = findViewById(R.id.selectedCity);
        fuel = findViewById(R.id.selectedFuel);
        select_fuel = findViewById(R.id.select_fuel);
        linearFuel = findViewById(R.id.linearFuel);
        recyclerView = findViewById(R.id.selectedSheds);

        String [] cities = {"Colombo", "Ratnapura", "Galle"};
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(QueueListActivity.this,R.layout.dropdown_menu,cities);
        autoCompleteTextView.setAdapter(cityAdapter);

        String [] fuelType = {"Petrol","Diesel"};
        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<>(QueueListActivity.this,R.layout.dropdown_menu,fuelType);
        autoCompleteTextView2.setAdapter(fuelAdapter);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            shedAdd = (String) adapterView.getItemAtPosition(i);
            city.setText(shedAdd);
            textInputLayout2.setVisibility(View.VISIBLE);
            linearFuel.setVisibility(View.VISIBLE);
            select_fuel.setVisibility(View.VISIBLE);

            ShedService shedService = ServiceBuilder.buildService(ShedService.class);
            //get sheds by city
            Call<ArrayList<Shed>> request = shedService.ShedsByAddress(shedAdd);

            request.enqueue(new Callback<ArrayList<Shed>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Shed>> call, @NonNull Response<ArrayList<Shed>> response) {
                    if(response.isSuccessful()){
                        shedList = response.body();
                        shedRecyclerAdapter = new ShedRecyclerAdapter(getApplicationContext(), shedList, QueueListActivity.this);
                        recyclerView.setAdapter(shedRecyclerAdapter);

                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Shed>> call, @NonNull Throwable t) {
                    Toast.makeText(QueueListActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        });
        autoCompleteTextView2.setOnItemClickListener((adapterView, view, i, l) -> {
            fuelT = (String)adapterView.getItemAtPosition(i);
            fuel.setText(fuelT);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("FuelType", fuelT);
            editor.apply();

            ShedService shedService = ServiceBuilder.buildService(ShedService.class);
            //get the shed with shortest queue by city and fuel type
            Call<Shed> request2 = shedService.ShortestQueueByAddressAndType(shedAdd,fuelT);

            request2.enqueue(new Callback<Shed>() {
                @Override
                public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                    if(response.isSuccessful()){
                        Shed ed = response.body();
                        ArrayList<Shed> shdList = new ArrayList<Shed>(){{
                            add(ed);
                        }};
                        shedRecyclerAdapter = new ShedRecyclerAdapter(getApplicationContext(), shdList, QueueListActivity.this);
                        recyclerView.setAdapter(shedRecyclerAdapter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                    Toast.makeText(QueueListActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onItemClicked(Shed shed) {
        Intent intent = new Intent(QueueListActivity.this, QueueDetailsActivity.class);
        intent.putExtra("shedname", shed.getName());
        intent.putExtra("shedregno", shed.getRegNo());
        intent.putExtra("shedaddress", shed.getAddress());
        intent.putExtra("dieselavailability", shed.isDieselAvailable());
        intent.putExtra("petrolavailability", shed.isPetrolAvailable());
        intent.putExtra("dieselarrival", shed.getDieselArrivalTime());
        intent.putExtra("petrolarrival", shed.getPetrolArrivalTime());
        intent.putExtra("dieselfinish", shed.getDieselFinishTime());
        intent.putExtra("petrolfinish", shed.getPetrolFinishTime());
        intent.putExtra("dieselqueue", shed.getDieselQueueLength());
        intent.putExtra("petrolqueue", shed.getPetrolQueueLength());
        intent.putExtra("fType",fuelT);
        startActivity(intent);
    }
}