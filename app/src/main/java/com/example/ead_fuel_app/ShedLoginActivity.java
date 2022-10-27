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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead_fuel_app.models.Shed;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.ShedService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShedLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText rNo, psd;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_shed_login);
        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);


        rNo = findViewById(R.id.regNo);
        psd = findViewById(R.id.psd);
        Button login = findViewById(R.id.signin1);
        TextView signup = findViewById(R.id.already1);
        progressBar = findViewById(R.id.progressBar1);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.already1:
                startActivity(new Intent(ShedLoginActivity.this, ShedRegisterActivity.class));
                break;
            case R.id.signin1:
                progressBar.setVisibility(View.VISIBLE);

                String regNo = rNo.getText().toString();
                String password = psd.getText().toString();

                ShedService shedService = ServiceBuilder.buildService(ShedService.class);
                //login shed using register number and password
                Call<Shed> call = shedService.loginShed("password",
                        regNo,
                        password);

                call.enqueue(new Callback<Shed>() {
                    @Override
                    public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (regNo.isEmpty()) {
                            rNo.setError("Register number field cannot be empty!");
                        }else if (password.isEmpty()) {
                            psd.setError("Password field cannot be empty!");
                        }else if(!response.isSuccessful()) {
                            Toast.makeText(ShedLoginActivity.this, "Register number or Password Incorrect!", Toast.LENGTH_SHORT).show();
                        }else {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                assert response.body() != null;
                                editor.putString("ShedSessionName", String.valueOf(response.body().getName()));
                            editor.putString("ShedSessionAddress", String.valueOf(response.body().getAddress()));
                            editor.apply();

                            Toast.makeText(ShedLoginActivity.this, "Shed LoggedIn Successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ShedLoginActivity.this, ShedOwnerActivity.class);
                            intent.putExtra("regNo", regNo);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                        Toast.makeText(ShedLoginActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        }
    }
}