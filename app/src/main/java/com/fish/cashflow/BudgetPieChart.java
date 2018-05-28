package com.fish.cashflow;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class BudgetPieChart extends AppCompatActivity implements View.OnClickListener{

    private static String TAG = "MainActivity";

    private float[] Data = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f, 52.6f};
    Button catEntertainment, catEducation, catHealth, catTransport, catShopping, catPersonalCare, catBills, catFood;
    ActionBarDrawerToggle mToggle;
    TextView MonthLabel, Balance;
    DrawerLayout mDrawerLayout;
    ProgressBar progressBar;
    ImageButton changeIncome;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_pie_chart);

        Log.d(TAG, "onCreate: BudgetPieChart");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) //Action bar menu
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDataSet() //Pie chart
    {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for(int i = 0; i < Data.length; i++)
            yEntrys.add(new PieEntry(Data[i] , i));

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"");
        pieDataSet.setDrawValues(false);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0xFFf44165);
        colors.add(0xFF8fc6ab);
        colors.add(0xFF85e4f7);
        colors.add(0xFFe1ea9d);
        colors.add(0xFF9a60af);
        colors.add(0xFFcc90c3);
        colors.add(0xFF739b8a);
        colors.add(0xFFb24c08);

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void initComponent()
    {
        Log.d(TAG, "initComponent");
        //Creating all object components
        catEntertainment = findViewById(R.id.catEntertainment);
        catEducation = findViewById(R.id.catEducation);
        catHealth = findViewById(R.id.catHealth);
        catTransport = findViewById(R.id.catTransport);
        catShopping = findViewById(R.id.catShopping);
        catPersonalCare = findViewById(R.id.catPersonalCare);
        catBills = findViewById(R.id.catBills);
        catFood = findViewById(R.id.catFood);
        changeIncome = findViewById(R.id.changeIncome);
        MonthLabel = findViewById(R.id.MonthLabel);
        Balance = findViewById(R.id.Balance);
        progressBar = findViewById(R.id.progressBar);
        pieChart = findViewById(R.id.idPieChart);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        pieChartSetup();
    }

    private void pieChartSetup()
    {
        Log.d(TAG, "pieChartSetup");
        pieChart.setRotationEnabled(false);
        pieChart.setBackgroundColor(Color.WHITE);
        pieChart.setHoleRadius(65f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("write something");
        pieChart.setCenterTextSize(10);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        addDataSet();
    }

    private void initOnClickListener() // Method for set on click listener for all button
    {
        Log.d(TAG, "initOnClickListener");
        catEntertainment.setOnClickListener(this);
        catEducation.setOnClickListener(this);
        catHealth.setOnClickListener(this);
        catTransport.setOnClickListener(this);
        catShopping.setOnClickListener(this);
        catPersonalCare.setOnClickListener(this);
        catBills.setOnClickListener(this);
        catFood.setOnClickListener(this);
        changeIncome.setOnClickListener(this);
    }

    public void onClick(View v)  //onClickListener
    {
        /*
            Syntax
            Intent <intentName> = new Intent(<context>, <otherActivity>.class);
            startActivity(<intentName>);
            or
            startActivity(new Intent(this, activityTwo.class));
            */
        Log.d(TAG, "onClick");
        switch (v.getId())
        {
            case R.id.catEntertainment:

            case R.id.catEducation:

            case R.id.catHealth:

            case R.id.catTransport:

            case R.id.catShopping:

            case R.id.catPersonalCare:

            case R.id.catBills:

            case R.id.catFood:

            case R.id.changeIncome:

        }
    }

}
