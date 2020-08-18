package com.example.acmedepartmentstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;



public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    // Verify Email
    static boolean isValid (String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    // Define method to create an account
    public void createAccount(String email, String password ){
        mAuth = FirebaseAuth.getInstance();
        if (isValid(email)==true){
        try{
        mAuth.createUserWithEmailAndPassword(email,password);
            Log.i("Account Create","Succeeded");
            startActivity(new Intent(CreateAccountActivity.this, Home.class));

        }
        catch (Exception e){

            Log.i("Account Create","Failed");

        }} else {
            // Alert user to use valid email
            Toast.makeText(CreateAccountActivity.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final Button createAccount = findViewById(R.id.CreateAccountBtn);
        final EditText usernameEditText = findViewById(R.id.CreateAccountUsername);
        final EditText passwordEditText = findViewById(R.id.CreateAccountPassword);


        // Create on click listener for create account button

        createAccount.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                String email = usernameEditText.getText().toString();
                String pw = passwordEditText.getText().toString();


                Log.i("Try Login", "User:" + email);

                try{
                    //Initiate create account with input details
                    createAccount(email,pw);

                } catch (Exception e) {

                    // Inform User that login was invalid
                    Toast.makeText(CreateAccountActivity.this, "Invalid UserName or Password", Toast.LENGTH_LONG).show();
                }


            }
        });





    }





}