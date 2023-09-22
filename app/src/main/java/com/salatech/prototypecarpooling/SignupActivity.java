package com.salatech.prototypecarpooling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.salatech.prototypecarpooling.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    DatabaseCarPool databaseCarpool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseCarpool = new DatabaseCarPool(this);

        binding.btnPassengerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextSignupName.getText().toString();
                String number = binding.editTextSignupNumber.getText().toString();
                String email = binding.editTextSignupEmail.getText().toString();
                String password = binding.editTextSignupPassword.getText().toString();
                String confirmPassword = binding.editTextSignupConfirmPassword.getText().toString();

                if (name.isEmpty() || number.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else {
                    // Remove non-numeric characters from the phone number
                    number = number.replaceAll("[^0-9]", "");

                    if (password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseCarpool.checkEmail(email);
                        if (checkUserEmail == false){
                            Boolean insert = databaseCarpool.insertData(name,number,email,password);
                            if (insert == true){
                                Toast.makeText(SignupActivity.this,"Signup Successfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignupActivity.this,"Signup Failed",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(SignupActivity.this,"User already exists Please login",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(SignupActivity.this,"Invalid Password",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}
