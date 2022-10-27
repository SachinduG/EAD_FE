/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead_fuel_app.models.User;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username, nic, address, vehicle_number, password, confirm_password;
    private String fuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_register);

        username = findViewById(R.id.username);
        nic = findViewById(R.id.nic);
        address = findViewById(R.id.address);
        vehicle_number = findViewById(R.id.vehicle_number);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);

        TextView already = findViewById(R.id.already);
        already.setOnClickListener(this);

        Button btn_register = findViewById(R.id.signup);
        btn_register.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.Petrol:
                if (checked)
                    fuelType = "Petrol";
                break;
            case R.id.Diesel:
                if (checked)
                    fuelType = "Diesel";
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.already:
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
                break;
            case R.id.signup:
                String userName = username.getText().toString();
                String nic_no = nic.getText().toString();
                String add = address.getText().toString();
                String vNum = vehicle_number.getText().toString();
                String pass = password.getText().toString();
                String repass = confirm_password.getText().toString();

                if (nic_no.isEmpty()) {
                    nic.setError("NIC field cannot be empty!");
                }
                else if (userName.isEmpty()) {
                    username.setError("Name field cannot be empty!");
                }
                else if (add.isEmpty()) {
                    address.setError("City field cannot be empty!");
                }
                else if (vNum.isEmpty()) {
                    vehicle_number.setError("Vehicle Number field cannot be empty!");
                }
                else if (pass.isEmpty()) {
                    password.setError("Password field cannot be empty!");
                }
                else if (!pass.equals(repass)) {
                    password.setError("Passwords are not matched!");
                    confirm_password.setError("Passwords are not matched!");
                }
                else if (pass.length() < 8 || pass.length() > 20) {
                    password.setError("Password length should be between 8 and 20!");
                }
                else {
                    User user = new User(nic_no,userName,add,vNum,fuelType,pass);

                    UserService userService = ServiceBuilder.buildService(UserService.class);
                    // register the user
                    Call<User> request = userService.registerUser(user);

                    request.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(UserRegisterActivity.this, "User Registered!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserRegisterActivity.this, UserLoginActivity.class));
                            } else {
                                Toast.makeText(UserRegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Toast.makeText(UserRegisterActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

        }
    }
}