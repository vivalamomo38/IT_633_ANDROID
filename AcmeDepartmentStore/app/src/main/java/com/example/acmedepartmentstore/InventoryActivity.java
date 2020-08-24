package com.example.acmedepartmentstore;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmedepartmentstore.data.model.Item;
import com.example.acmedepartmentstore.data.model.LoggedInUser;
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

        Log.i("Status","Start Inventory Activity Succeeded");

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
        String firstName;
        String lastName;
        String uID;
        LoggedInUser sessionUser ;

        // Implement try catch to solve extra failures
        try {
            if (extras != null) {
                firstName = extras.getString("LoggedInUser.firstName");
                lastName = extras.getString("LoggedInUser.firstName");
                uID = extras.getString("LoggedInUser.uID");

               sessionUser = new LoggedInUser(uID,firstName,lastName,null);

                Log.i("Extras", "Extras were passed");

                // Had to provide workaround here as, I couldn't setText to the original drawer layout
                // See resolution code here --https://stackoverflow.com/questions/33199764/android-api-23-change-navigation-view-headerlayout-textview

                View header = LayoutInflater.from(this).inflate(R.layout.nav_header_layout, null);
                navigationView.addHeaderView(header);
                TextView navUserName = (TextView) header.findViewById(R.id.nav_header_username);

                navUserName.setText(firstName);
                // Set NavBar UserName w/ Extra passed from Intent


            } else {
                Log.i("Extras", "No Extras were passed");
            }
        } catch (Exception e){ Log.i("Extras", "Extras failed to pass correctly");}
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
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.i("Status","Item Clicked" + position);

                        //Implement show dialog for each item and load item data into textfields
                        viewItemDialog(view,position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // prepare item cards
        InsertDataIntoCards();

    }

    //Establish mock data and add to the itemList for testing
    private void InsertDataIntoCards() {
        // Add Card Data and display

        int[] itemCovers = new int[]{
                R.drawable.comingsoon,
                R.drawable.screw,
                R.drawable.finish_nail,
                R.drawable.behr_interior_semi_gloss,
                R.drawable.paint_brush,
                R.drawable.gg_glue,
                R.drawable.phillips_head_screwdriver,
        };

        Item a = new Item("Screws", 100, 0.99, "3/4 inch screw",itemCovers[1]);
        itemList.add(a);

         a = new Item("Nails", 1500, 0.63, "3/4 inch nails",itemCovers[2]);
        itemList.add(a);

        a = new Item("Behr Interior Semi-Gloss (White)", 1, 17.99, "1 Gl White Paint",itemCovers[3]);
        itemList.add(a);

        a = new Item("Krause & Becker PRO - Paint Brush", 3, 7.99, "Premium 3 inch width paint brush",itemCovers[4]);
        itemList.add(a);

        a = new Item("Gorilla Glue", 3, 3.99, "1.75 fl. oz. Tube Gorilla Glue",itemCovers[5]);
        itemList.add(a);

        a = new Item("Phillips Screw Driver", 100, 22.99, "Stanley Phillips Screw Driver",itemCovers[6]);
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
                    Toast toast = Toast.makeText(this, "Logout Processed", Toast.LENGTH_SHORT);
                    toast.show();
                }catch(Exception e){Log.i("Status","No user to sign out");}
                Intent signOutIntent = new Intent(this, MainActivity.class);
                startActivity(signOutIntent);
                break;
        }
        return false; }


    // class to handle the grid spacing
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

    //Method to show the add item dialog
    public void addItemDialog(View view){


        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Get the layout inflater to inflate the custom view

        LayoutInflater inflater = getLayoutInflater();

        // 3. Inflate the layout and assign to a view for explicit reference
        final View alertDialogView = inflater.inflate(R.layout.add_item_dialog, null);

        builder.setView(alertDialogView)
                // Add action buttons
                .setPositiveButton(R.string.add_item_dialog_item_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Log Test OnClick
                       try{

                           int[] newItemCover = new int[]{R.drawable.comingsoon};

                           EditText itemNameEditText = (EditText) alertDialogView.findViewById(R.id.itemName);
                           EditText itemDescEditText = (EditText) alertDialogView.findViewById(R.id.itemDescription);
                           EditText itemQtyEditText = (EditText) alertDialogView.findViewById(R.id.itemQty);
                           EditText itemPPUEditText = (EditText) alertDialogView.findViewById(R.id.itemPPU);

                           String newItemName = itemNameEditText.getText().toString();
                           String newItemDesc = itemDescEditText.getText().toString();
                           int newItemQty = Integer.parseInt(itemQtyEditText.getText().toString());
                           double newItemPPU = Double.parseDouble(itemPPUEditText.getText().toString());

                           Item newItem = new Item(newItemName, newItemQty,newItemPPU,newItemDesc,newItemCover[0]);

                           ImageView imageView = recyclerView.findViewById(R.id.thumbnail);
                           imageView.setImageDrawable (null);

                           itemList.add(newItem);
                           itemAdapter.notifyDataSetChanged();


                           Log.i("Status","Add Item succeeded for " + newItemName );

                       } catch (Exception e){
                           Log.i("Status","Add Item Failed");
                           Context context = getApplicationContext();
                           CharSequence text = "One of your entries was invalid!";
                           int duration = Toast.LENGTH_SHORT;

                           Toast toast = Toast.makeText(context, text, duration);
                           toast.show();
                       }
                           Log.i("Status","Positive Button Selected");
                    }
                })
                .setNegativeButton(R.string.add_item_dialog_item_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //actions for negative
                    }
                });



// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();



    }

    // Method to delete inventory item from the itemlist
    public void deleteItem(int itemPosition){
        itemList.remove(itemPosition);
        itemAdapter.notifyDataSetChanged();

    }

    // Method to save edits to an inventory item - needs improvement and DB Calls
    public void saveUpdatedItem(int itemPosition, Item item){
        itemList.remove(itemPosition);
        itemList.add(item);
        itemAdapter.notifyDataSetChanged();

    }

    // Method to confirm the item delete
    public void deleteItemDialog (final int itemPosition){
        new AlertDialog.Builder(this)
                .setTitle("Delete Inventory Item")
                .setMessage("Do you really want to remove this Item?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        try{
                            deleteItem(itemPosition);
                        Toast.makeText(getApplicationContext(), "Yay! Inventory Cleaned!", Toast.LENGTH_SHORT).show();}
                        catch (Exception e){
                            Log.i("Status","Item Delete Failed");
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    // Method to show the item details on click
    public void viewItemDialog(View view, final int position){
        // Get item to load into the inflated dialog
        Item viewItem = itemList.get(position);

        Log.i("Status",viewItem.getItemName() + " has been selected");


        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Get the layout inflater to inflate the custom view

        LayoutInflater inflater = getLayoutInflater();


        // 3. Inflate the layout and assign to a view for explicit reference
        final View alertDialogView = inflater.inflate(R.layout.edit_item_dialog, null);

        final EditText itemNameEditText = (EditText) alertDialogView.findViewById(R.id.itemName);
        final EditText itemDescEditText = (EditText) alertDialogView.findViewById(R.id.itemDescription);
        final EditText itemQtyEditText = (EditText) alertDialogView.findViewById(R.id.itemQty);
        final EditText itemPPUEditText = (EditText) alertDialogView.findViewById(R.id.itemPPU);
        // Load data from selected item into Dialog Views

        itemNameEditText.setText(viewItem.getItemName());
        itemDescEditText.setText(viewItem.getItemDescription());
        itemQtyEditText.setText(String.valueOf(viewItem.getNumOfItems()));
        itemPPUEditText.setText(String.valueOf(viewItem.getUnitPrice()));

        // Set TextViews to not editable until edit button clicked
        itemNameEditText.setEnabled(false);
        itemDescEditText.setEnabled(false);
        itemQtyEditText.setEnabled(false);
        itemPPUEditText.setEnabled(false);


        //


        builder.setView(alertDialogView)
                // Add action buttons
                .setPositiveButton(R.string.edit_item_dialog_item_edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editItemDialog(position);
                    }
                }).setNegativeButton(R.string.edit_item_dialog_item_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //actions for negative
                        dialog.dismiss();
                        deleteItemDialog(position);

                    }
                });



// 3. Show the Alert Dialog and set custom onclicklistener to allow user to edit the data

        AlertDialog dialog = builder.create();

        dialog.show();



    }

    // Create method to show edit view of item
    public void editItemDialog(final int position){
        // Get item to load into the inflated dialog
        final Item viewItem = itemList.get(position);
        final Item[] editItem = new Item[1];

        Log.i("Status",viewItem.getItemName() + " has been selected for editing");


        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Get the layout inflater to inflate the custom view

        LayoutInflater inflater = getLayoutInflater();


        // 3. Inflate the layout and assign to a view for explicit reference
        final View alertDialogView = inflater.inflate(R.layout.edit_item_dialog, null);

        final EditText itemNameEditText = (EditText) alertDialogView.findViewById(R.id.itemName);
        final EditText itemDescEditText = (EditText) alertDialogView.findViewById(R.id.itemDescription);
        final EditText itemQtyEditText = (EditText) alertDialogView.findViewById(R.id.itemQty);
        final EditText itemPPUEditText = (EditText) alertDialogView.findViewById(R.id.itemPPU);
        final int itemThumbnail = viewItem.getThumbnail();
        // Load data from selected item into Dialog Views

        itemNameEditText.setText(viewItem.getItemName());
        itemDescEditText.setText(viewItem.getItemDescription());
        itemQtyEditText.setText(String.valueOf(viewItem.getNumOfItems()));
        itemPPUEditText.setText(String.valueOf(viewItem.getUnitPrice()));

        builder.setView(alertDialogView)
                // Add action buttons
                .setPositiveButton(R.string.edit_item_dialog_item_save,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String newItemName = itemNameEditText.getText().toString();
                                String newItemDesc = itemDescEditText.getText().toString();
                                int newItemQty = Integer.parseInt(itemQtyEditText.getText().toString());
                                double newItemPPU = Double.parseDouble(itemPPUEditText.getText().toString());
                                int newItemThumbnail = itemList.get(position).getThumbnail();

                                editItem[0] = new Item(newItemName,newItemQty,newItemPPU,newItemDesc,newItemThumbnail);


                                saveUpdatedItem(position, editItem[0]);
                            }
                        }).setNegativeButton(R.string.edit_item_dialog_item_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //actions for negative
                dialog.dismiss(); }
        });



// 3. Show the Alert Dialog and set custom onclicklistener to allow user to edit the data

        AlertDialog dialog = builder.create();

        dialog.show();



    }

    }
