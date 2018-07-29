package com.fish.cashflow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Saving extends AppCompatActivity implements View.OnClickListener {

    //Log
    private static String TAG = "Savings";

    //Database
    DatabaseHelper myDB;

    //Interface
    TextView tvSavings;
    ImageButton backButton;
    ImageView moneyIcon;

    //Variable to use
    private String[] monthInWords = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private String[] monthInNumber = {"201801", "201802", "201803", "201804", "201805", "201806", "201807", "201808", "201809", "201810", "201811", "201812"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        Log.d(TAG, "onCreate: Saving");

        initComponent(); //Initialize components.
        initOnClickListener(); //Initialize onClickListener.

        //Database
        myDB = new DatabaseHelper(this);

        showTotalSavings();
    }

    /**
     * Define the UI.
     */
    private void initComponent()
    {
        Log.d(TAG, "initComponent");
        //Creating all object components
        backButton = findViewById(R.id.backButton);
        tvSavings = findViewById(R.id.tvSavings);
        moneyIcon = findViewById(R.id.moneyIcon);
    }

    /**
     * Implementing OnClickListener for each button.
     */
    private void initOnClickListener()
    {
        Log.d(TAG, "initOnClickListener");
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
        switch(v.getId())
        {
            case R.id.backButton:
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
        }
    }

    private void showTotalSavings()
    {
        //Get the data from Wishlist table and append to a list.
        double totalSaving = 0, monthIncome = 0, monthExpense = 0;

        for (int i = 0; i < monthInWords.length; i++)
        {
            // Get all monthly income based on month.
            Cursor res1 = myDB.getMonthlyIncome(monthInWords[i]);
            if(res1 != null && res1.moveToFirst()) // If the query result is not empty.
            {
                monthIncome = Double.parseDouble(res1.getString(1));
            }
            else
                Toast.makeText(Saving.this, "ERROR HAS OCCUR. PLEASE REPORT THIS BUG." , Toast.LENGTH_SHORT).show();

            // Get all total expenses based on month.
            Cursor res2 = myDB.calculatingTotalExpenseForAllCategory(monthInNumber[i]);
            if(res2 != null && res2.moveToFirst()) // If the query result is not empty.
            {
                if(res2.getString(0) == null)
                    monthExpense = 0;
                else
                    monthExpense = Double.parseDouble(res2.getString(0));
            }
            else
                Toast.makeText(Saving.this, "ERROR HAS OCCUR. PLEASE REPORT THIS BUG." , Toast.LENGTH_SHORT).show();

            //Calculation.
            totalSaving += ( monthIncome - monthExpense );
            Log.d(TAG, "TOTAL SAVINGS ---->" +totalSaving);
        }

        DecimalFormat df = new DecimalFormat("0.00");
        tvSavings.setText("Total savings: RM"+df.format(totalSaving));

    }

}
