package com.salatech.prototypecarpooling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.salatech.prototypecarpooling.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseCarPool databaseCarPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Set the root view of the binding layout

        databaseCarPool = new DatabaseCarPool(this);

        binding.btnPassengerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextPassengerLoginEmail.getText().toString();
                String password = binding.editTextLoginPassword.getText().toString();
                if (email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this,"All fields are mandatory",Toast.LENGTH_LONG).show();
                else {
                    Boolean checkCredentials = databaseCarPool.checkEmailPassword(email,password);

                    if (checkCredentials == true){
                        Toast.makeText(LoginActivity.this,"Welcome back",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        binding.signupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
