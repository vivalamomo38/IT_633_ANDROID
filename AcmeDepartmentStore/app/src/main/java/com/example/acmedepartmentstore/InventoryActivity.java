package com.example.acmedepartmentstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmedepartmentstore.data.model.Item;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare Nav bar variables
    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // Declare recycler view variables
    private List<Item> itemList;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;


    // On create methods for InventoryActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

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


        // Test that intent extras exist
        Bundle extras = getIntent().getExtras();
        String userName;

        // Implement try catch to solve reopen application bug
        // Issue #1 on gitHub Repo
        try {
            if (extras != null) {
                userName = extras.getString("LoggedInUser.firstName");
                Log.i("Extras", "Extras were passed");
                Log.i("Extras", userName);

                // Had to provide workaround here as, I couldn't setText to the original drawer layout
                // See resolution code here --https://stackoverflow.com/questions/33199764/android-api-23-change-navigation-view-headerlayout-textview

                View header = LayoutInflater.from(this).inflate(R.layout.nav_header_layout, null);
                navigationView.addHeaderView(header);
                TextView navUserName = (TextView) header.findViewById(R.id.nav_header_username);

                navUserName.setText(userName);
                // Set NavBar UserName w/ Extra passed from Intent


            } else {
                Log.i("Extras", "No Extras were passed");
            }
        } catch (Exception e){}
        // Create Recycler View widget
        recyclerView = findViewById(R.id.inventory_recycler_view);

        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, itemList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        //Item Card Decoration:

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);

        // prepare item cards
        InsertDataIntoCards();

    }

    //Establish mock data and add to the itemList for testing
    private void InsertDataIntoCards() {
        // Add Card Data and display

        int[] itemCovers = new int[]{
                R.drawable.screw,
                R.drawable.finish_nail,
                R.drawable.behr_interior_semi_gloss,
                R.drawable.paint_brush,
                R.drawable.gg_glue,
                R.drawable.phillips_head_screwdriver,
        };

        Item a = new Item("Screws", 100, 0.99, "3/4 inch screw",itemCovers[0]);
        itemList.add(a);

         a = new Item("Nails", 1500, 0.63, "3/4 inch nails",itemCovers[1]);
        itemList.add(a);

        a = new Item("Behr Interior Semi-Gloss (White)", 1, 17.99, "1 Gl White Paint",itemCovers[2]);
        itemList.add(a);

        a = new Item("Krause & Becker PRO - Paint Brush", 3, 7.99, "Premium 3 inch width paint brush",itemCovers[3]);
        itemList.add(a);

        a = new Item("Gorilla Glue", 3, 3.99, "1.75 fl. oz. Tube Gorilla Glue",itemCovers[4]);
        itemList.add(a);

        a = new Item("Phillips Screw Driver", 100, 22.99, "Stanley Phillips Screw Driver",itemCovers[5]);
        itemList.add(a);

        //Notify data set changed
        itemAdapter.notifyDataSetChanged();

    }

    // Navigation Bar Selection Logic
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Switch Statement to determine nav menu item selected
        switch(menuItem.getItemId()) {
            case R.id.nav_inventory:
                Log.i("Inventory", "nav inventory selected");
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
                Log.i("Logout", "nav logout selected");
                try{
                   FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                }catch(Exception e){Log.i("Status","No user to sign out");}
                Intent signOutIntent = new Intent(this, MainActivity.class);
                startActivity(signOutIntent);
                break;
        }
        return false; }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }



        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge){
                outRect.left = spacing -column /spanCount;
                outRect.right = (column+1) * spacing;

                if (position <spanCount){// Top Edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; //Item Bottom

            } else {

                outRect.left = column * spacing /spanCount;
                outRect.right = spacing - (column +1 ) * spacing / spanCount;

                if (position >= spanCount){
                    outRect.top = spacing;
                }


            }


        }
    }

    // Method to convert DP to PX
    private int dpToPx(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics()));
    }

    public void addItemDialog(View view){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Get the layout inflater to inflate the custom view

        LayoutInflater inflater = getLayoutInflater();

        // 3. Inflate the layout

        builder.setView(inflater.inflate(R.layout.add_item_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.prompt_email, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.prompt_password, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       //actions for negative
                    }
                });



// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();

    }

}