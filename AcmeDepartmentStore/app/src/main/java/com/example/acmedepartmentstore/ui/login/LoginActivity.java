package com.example.acmedepartmentstore.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acmedepartmentstore.CreateAccountActivity;
import com.example.acmedepartmentstore.Home;
import com.example.acmedepartmentstore.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView createAccount = findViewById(R.id.createAccount);

        // Get FirebaseAuth instance
         final FirebaseAuth mAuth;
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                String logUserName = usernameEditText.getText().toString();

                Log.i("Try Login", "User:" + logUserName);

                try{
                        //Initiate login with user details
                        mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                        Log.i("Login Success",usernameEditText.getText().toString());
                        startActivity(new Intent(LoginActivity.this, Home.class));

                    } catch (Exception e) {

                        // Inform User that login was invalid
                        Toast.makeText(LoginActivity.this, "Invalid UserName or Password", Toast.LENGTH_LONG).show();
                    }


            }
        });

        createAccount.setOnClickListener(new View.OnClickListener(){

// Creating logic to move to create account screen
            @Override
            public void onClick(View view) {

                String logUserName = usernameEditText.getText().toString();

                Log.i("Create Account", "Start Activity");

                try{
                    //Initiate login with user details

                    startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));

                } catch (Exception e) {

                    // Inform User that login was invalid
                    Toast.makeText(LoginActivity.this, "An error has occurred", Toast.LENGTH_LONG).show();
                    Log.i("Login Activity", "Failed to initialize");

                }


            }
        });



        };






    }


