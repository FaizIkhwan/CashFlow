package com.fish.cashflow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Wishlist extends AppCompatActivity implements View.OnClickListener{

    //log
    private static String TAG = "Wishlist";

    //interface
    TextView wishlistTV;
    Button addButton, backButton;
    ListView listView;

    //database
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Log.d(TAG, "onCreate: Wishlist");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        //database
        myDB = new DatabaseHelper(this);

        populateListView(); //print kat listView

    }

    private void initComponent() //interface
    {
        Log.d(TAG, "InitComponent");
        wishlistTV = findViewById(R.id.wishlistTV);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
    }

    private void initOnClickListener() //interface
    {
        Log.d(TAG, "initOnClickListener");
        addButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) //click listener
    {
        Log.d(TAG, "onClick");
        switch (v.getId())
        {
            case R.id.addButton:
                initPopUpAddWishlist();
                break;
            case R.id.backButton:
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
        }
    }

    private void populateListView()
    {

        /*
            NANTI KENA TUKAR JADI TABLE WISHLIST
         */
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = myDB.getAllData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
            listData.add(data.getString(2));
            listData.add(data.getString(3));
            listData.add(data.getString(4));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    private void initPopUpAddWishlist()
    {
        Log.d(TAG, "initPopUpAddWishlist");

        AlertDialog.Builder mBuilderAddWishlist = new AlertDialog.Builder(this);

        View mViewAddWishlist = getLayoutInflater().inflate(R.layout.activity_add_wishlist, null);
        TextView addWishlistLabel = mViewAddWishlist.findViewById(R.id.addWishlistLabel);

        final EditText etRM = mViewAddWishlist.findViewById(R.id.etRM);
        final EditText etDescription = mViewAddWishlist.findViewById(R.id.etDescription);

        Button AddButton = mViewAddWishlist.findViewById(R.id.AddButton);

        mBuilderAddWishlist.setView(mViewAddWishlist);
        final AlertDialog dialogAddWishlist = mBuilderAddWishlist.create();

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etRM.getText().toString().isEmpty() && !etDescription.getText().toString().isEmpty())
                {
                    // NANTI KENA LETAK DALAM DATABASE
                    Toast.makeText(Wishlist.this, "Add success" , Toast.LENGTH_SHORT).show();
                    dialogAddWishlist.cancel(); //untuk tutup pop up
                }
                else
                {
                    Toast.makeText(Wishlist.this, "Must fill the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogAddWishlist.show();
    }
}
