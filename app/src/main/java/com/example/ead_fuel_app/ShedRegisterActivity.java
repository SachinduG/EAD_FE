/**
 * @author S.M. Jayasekara
 * @IT_number IT19161648
 */

package com.example.ead_fuel_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead_fuel_app.models.Shed;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.ShedService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShedRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText shedName, regNo, shedCity, psd, psd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_shed_register);

        shedName = findViewById(R.id.shed_username);
        regNo = findViewById(R.id.reg_no);
        shedCity = findViewById(R.id.shed_add);
        psd = findViewById(R.id.password);
        psd1 = findViewById(R.id.confirm_password);

        TextView already = findViewById(R.id.already);
        already.setOnClickListener(this);

        Button btn_register = findViewById(R.id.signup);
        btn_register.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.already:
                startActivity(new Intent(getApplicationContext(), ShedLoginActivity.class));
                break;
            case R.id.signup:
                String shedName = this.shedName.getText().toString().trim();
                String regNo = this.regNo.getText().toString().trim();
                String add = shedCity.getText().toString().trim();
                String pass = psd.getText().toString().trim();
                String repass = psd1.getText().toString().trim();

                if (regNo.isEmpty()) {
                    this.regNo.setError("Registration No cannot be empty!");
                }
                else if (shedName.isEmpty()) {
                    this.shedName.setError("Name field cannot be empty!");
                }
                else if (add.isEmpty()) {
                    shedCity.setError("City field cannot be empty!");
                }
                else if (pass.isEmpty()) {
                    psd.setError("Password field cannot be empty!");
                }
                else if (!pass.equals(repass)) {
                    psd.setError("Passwords are not matched!");
                    psd1.setError("Passwords are not matched!");
                }
                else if (pass.length() < 8 || pass.length() > 20) {
                    psd.setError("Password length should be between 8 and 20!");
                }
                else {
                    Shed shed = new Shed(regNo,shedName,add,pass,null,null,false,0,null,null,false,0);

                    ShedService shedService = ServiceBuilder.buildService(ShedService.class);
                    //register the shed
                    Call<Shed> request = shedService.registerShed(shed);

                    request.enqueue(new Callback<Shed>() {
                        @Override
                        public void onResponse(@NonNull Call<Shed> call, @NonNull Response<Shed> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ShedRegisterActivity.this, "Shed Registered!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ShedRegisterActivity.this, ShedLoginActivity.class));
                            } else {
                                Toast.makeText(ShedRegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Shed> call, @NonNull Throwable t) {
                            Toast.makeText(ShedRegisterActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        }
    }
}