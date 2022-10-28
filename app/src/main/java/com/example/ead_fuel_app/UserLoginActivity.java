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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead_fuel_app.models.User;
import com.example.ead_fuel_app.service.ServiceBuilder;
import com.example.ead_fuel_app.service.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nicNo, psd;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_login);

        sharedPreferences = getSharedPreferences("Fuels", Context.MODE_PRIVATE);

        nicNo = findViewById(R.id.nic);
        psd = findViewById(R.id.password);

        Button signin = findViewById(R.id.signin);
        signin.setOnClickListener(this);

        TextView already = findViewById(R.id.already);
        already.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.already:
                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
                break;
            case R.id.signin:
                progressBar.setVisibility(View.VISIBLE);

                String Nic = nicNo.getText().toString();
                String Password = psd.getText().toString();

                UserService userService = ServiceBuilder.buildService(UserService.class);
                //login user with nic number and password
                Call<User> request = userService.loginUser("password",
                        Nic,
                        Password);

                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (Nic.isEmpty()) {
                            nicNo.setError("NIC number field cannot be empty!");
                        }else if (Password.isEmpty()) {
                            psd.setError("Password field cannot be empty!");
                        }else if(!response.isSuccessful()){
                            Toast.makeText(UserLoginActivity.this, "NIC number or Password Incorrect!", Toast.LENGTH_SHORT).show();
                        } else {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            assert response.body() != null;
                            editor.putString("Session", String.valueOf(response.body().getNic()));
                            editor.apply();

                            Toast.makeText(UserLoginActivity.this, "User LoggedIn Successfully!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(UserLoginActivity.this, QueueListActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(UserLoginActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        }
    }
}