package com.example.acmedepartmentstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser sessionUser;
    private String sessionFirstName;
    private String sessionLastName;
    private String sessionUserID;
    private FirebaseFirestore _fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i("Activity Status","Start Home Activity Succeeded");


        // Initialize Firebase Instance
        mAuth = FirebaseAuth.getInstance();
        _fireStore = FirebaseFirestore.getInstance();

        // Test if user is logged in Else return to MAINACTIVITY
        if(mAuth.getCurrentUser()!=null){
            Log.i("Status","Persisted session w/"+ " " + mAuth.getCurrentUser().getUid()); }

        // Gets user document from Firestore as reference to dynamically load user data
        try {
                       sessionUser = mAuth.getCurrentUser();
            sessionUserID = mAuth.getCurrentUser().getUid();

            _fireStore.collection("users").document(sessionUserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot!=null) {
                    // Edit for no DB calls to create user first name last name in navigation view - set user name to null for null pointer
                        if(sessionFirstName != null && sessionLastName !=null ){
                            sessionFirstName = documentSnapshot.getString("firstName");
                            sessionLastName = documentSnapshot.getString("lastName");
                        }else{
                            sessionFirstName = "Welcome!";
                            sessionLastName = "New User";
                        }

                        Log.i("Status","Session user is: "+ sessionFirstName +" "+ sessionLastName);

                        TextView navHeaderTextView = findViewById(R.id.nav_header_username);
                        navHeaderTextView.setText(sessionFirstName);

                    } else {
                        Log.d("Status", "Failed to get user info");
                        // Toast.makeText(this, "Document Does Not exists", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        } catch (Exception e){

            Log.i("Status","Error retrieving user info");
        }





        toolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolBar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Switch Statement to determine menu item selected
        switch(menuItem.getItemId()) {
            case R.id.nav_inventory:
                Log.i("Inventory", "nav inventory selected");
                Log.i("Inventory", sessionUserID);
                Intent inventoryIntent = new Intent(Home.this, InventoryActivity.class);
                inventoryIntent.putExtra("LoggedInUser.firstName", sessionFirstName);
                inventoryIntent.putExtra("LoggedInUser.lastName", sessionFirstName);
                inventoryIntent.putExtra("LoggedInUser.uID", sessionUserID);
                startActivity(inventoryIntent);
                break;
            case R.id.nav_search:
                Log.i("Search", "nav search selected");
                break;
            case R.id.nav_starred:
                Log.i("Starred", "nav starred selected");
                break;
            case R.id.nav_recent:
                Log.i("Recent", "nav recent selected");
                break;
            case R.id.nav_backup:
                Log.i("Backup", "nav backup selected");
                break;
            case R.id.nav_settings:
                Log.i("Settings", "nav settings selected");
                break;
            case R.id.nav_logout:
                // Sign user out and return to MainActivity
                Log.i("Logout", "nav logout selected");
                try{
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Toast toast = Toast.makeText(this, "Logout Processed", Toast.LENGTH_SHORT);
                    toast.show();
                }catch(Exception e){Log.i("Status","No user to sign out");}
                Intent signOutIntent = new Intent(this, MainActivity.class);
                startActivity(signOutIntent);
                Log.i("Status","Logout Selected");
                break;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}