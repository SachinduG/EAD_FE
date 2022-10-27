/**
 * @author S.M. Jayasekara
 * @IT_number IT19161648
 */

package com.example.ead_fuel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button user_register, shed_register, user_sign_in, shed_sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        user_register = findViewById(R.id.welcome_user_register);
        shed_register = findViewById(R.id.welcome_shed_register);
        user_sign_in = findViewById(R.id.welcome_user_sign_in);
        shed_sign_in = findViewById(R.id.shed_sign_in);

        user_register.setOnClickListener(this);
        shed_register.setOnClickListener(this);
        user_sign_in.setOnClickListener(this);
        shed_sign_in.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.welcome_user_register:
                startActivity(new Intent(getApplicationContext(), UserRegisterActivity.class));
                break;
            case R.id.welcome_shed_register:
                startActivity(new Intent(getApplicationContext(), ShedRegisterActivity.class));
                break;
            case R.id.welcome_user_sign_in:
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
                break;
            case R.id.shed_sign_in:
                startActivity(new Intent(getApplicationContext(), ShedLoginActivity.class));
                break;
        }
    }
}