package com.fish.cashflow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

        createObj(); //print kat listView
    }

    private void createObj() // transfer semua dalam db wishlist jadi object
    {
        //get the data and append to a list
        Cursor data = myDB.getDataFromWishlist();
        ArrayList<WishlishObj> listData = new ArrayList<>();
        double saving = BudgetPieChart.getSavings();
        Log.d(TAG, "SAVING ---->" +saving);
        double percent;

        while(data.moveToNext())
        {
            percent = 100 - (  ( Double.parseDouble(data.getString(2)) - saving) / Double.parseDouble(data.getString(2))  * 100); // calculation
            Log.d(TAG, "PERCENT IN WISHLIST CLASS ---->" +percent);
            listData.add(new WishlishObj( data.getString(1), data.getString(2), percent ) );
        }

        WishlistAdapter adapter = new WishlistAdapter(this, R.layout.adapter_view_layout, listData);
        listView.setAdapter(adapter);
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
                    myDB.insertDataWishlist(etDescription.getText().toString(), etRM.getText().toString());
                    Toast.makeText(Wishlist.this, "Add success" , Toast.LENGTH_SHORT).show();
                    dialogAddWishlist.cancel(); //untuk tutup pop up
                    createObj();
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
