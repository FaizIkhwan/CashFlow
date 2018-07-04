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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Wishlist extends AppCompatActivity implements View.OnClickListener{

    //Log
    private static String TAG = "Wishlist";

    //Interface
    TextView wishlistTV;
    ImageButton addButton, backButton;
    ListView listView;

    //Database
    DatabaseHelper myDB;

    //Variable to use
    private String[] monthInWords = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    String[] monthInNumber = {"201801", "201802", "201803", "201804", "201805", "201806", "201807", "201808", "201809", "201810", "201811", "201812"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Log.d(TAG, "onCreate: Wishlist");

        initComponent(); //Initialize components.
        initOnClickListener(); //Initialize onClickListener.

        //Database
        myDB = new DatabaseHelper(this);

        createObj(); //Print on listView.
    }

    /**
     * Transfer data from table Wishlist on database into object.
     */
    private void createObj() {
        //Get the data from Wishlist table and append to a list.
        Cursor data = myDB.getDataFromWishlist();
        ArrayList<WishlishObj> listData = new ArrayList<>();

        double totalSaving = 0, monthIncome = 0, monthExpense = 0, percent;

        for (int i = 0; i < monthInWords.length; i++)
        {
            Cursor res1 = myDB.getMonthlyIncome(monthInWords[i]);
            if(res1 != null && res1.moveToFirst()) // If the query result is not empty.
            {
                monthIncome = Double.parseDouble(res1.getString(1));
            }
            else
                Toast.makeText(Wishlist.this, "ERROR HAS OCCUR. PLEASE REPORT THIS BUG." , Toast.LENGTH_SHORT).show();

            Cursor res2 = myDB.calculatingTotalExpenseForAllCategory(monthInNumber[i]);
            if(res2 != null && res2.moveToFirst()) // If the query result is not empty.
            {
                if(res2.getString(0) == null)
                    monthExpense = 0;
                else
                    monthExpense = Double.parseDouble(res2.getString(0));
            }
            else
                Toast.makeText(Wishlist.this, "ERROR HAS OCCUR. PLEASE REPORT THIS BUG." , Toast.LENGTH_SHORT).show();

            //Calculation.
            totalSaving += ( monthIncome - monthExpense );
            Log.d(TAG, "TOTAL SAVINGS ---->" +totalSaving);
        }

        while(data.moveToNext())
        {
            percent = (  totalSaving / Double.parseDouble(data.getString(2))  * 100); // Calculation.
            Log.d(TAG, "PERCENT IN WISHLIST CLASS ---->" +percent);
            listData.add(new WishlishObj( data.getString(1), percent ) );
        }

        WishlistAdapter adapter = new WishlistAdapter(this, R.layout.adapter_view_layout, listData);
        listView.setAdapter(adapter);
    }

    /**
     * Define the UI.
     */
    private void initComponent()
    {
        Log.d(TAG, "InitComponent");
        wishlistTV = findViewById(R.id.wishlistTV);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
    }

    /**
     * Implementing OnClickListener for each button.
     */
    private void initOnClickListener()
    {
        Log.d(TAG, "initOnClickListener");
        addButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    /**
     * OnClick method for each button.
     * @param v
     */
    @Override
    public void onClick(View v)
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

    /**
     * Creating pop up when the user click on add wishlist button.
     */
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
                    myDB.insertDataWishlist(etDescription.getText().toString().toUpperCase(), etRM.getText().toString());
                    Toast.makeText(Wishlist.this, "Add success" , Toast.LENGTH_SHORT).show();
                    dialogAddWishlist.cancel(); // To close the pop up.
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
