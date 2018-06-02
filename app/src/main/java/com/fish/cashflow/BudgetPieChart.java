package com.fish.cashflow;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BudgetPieChart extends AppCompatActivity implements View.OnClickListener{

    //log
    private static String TAG = "BudgetPieChart";

    //data for pie chart
    private float[] Data = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f, 52.6f};

    //interface
    Button catEntertainment, catEducation, catHealth, catTransport, catShopping, catPersonalCare, catBills, catFood;
    ActionBarDrawerToggle mToggle;
    TextView MonthLabel, MonthlyIncomeLabel;
    DrawerLayout mDrawerLayout;
    ProgressBar progressBar;
    ImageButton changeIncome;
    PieChart pieChart;

    //usable variable
    String category;
    String currentDate;

    //database
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_pie_chart);

        Log.d(TAG, "onCreate: BudgetPieChart");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        //Drawer
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Database
        myDB = new DatabaseHelper(this);

        //Calendar to get current date
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        //to get month
        String month = currentDate.split("\\s")[0];//splits the string based on whitespace
        MonthLabel.setText(month);
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
        colors.add(0xFFf44165); // catEntertainment
        colors.add(0xFF8fc6ab); // catEducation
        colors.add(0xFF85e4f7); // catHealth
        colors.add(0xFFe1ea9d); // catTransport
        colors.add(0xFF9a60af); // catShopping
        colors.add(0xFFcc90c3); // catPersonalCare
        colors.add(0xFF739b8a); //  catBills
        colors.add(0xFFb24c08); // catFood
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
        MonthlyIncomeLabel = findViewById(R.id.MonthlyIncomeLabel);
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
                Log.d(TAG, "Cat Entertainment");
                category = "Entertainment";
                initPopUpExpense();
                break;
            case R.id.catEducation:
                Log.d(TAG, "Cat Education");
                category = "Education";
                initPopUpExpense();
                break;
            case R.id.catHealth:
                Log.d(TAG, "Cat Health");
                category = "Health";
                initPopUpExpense();
                break;
            case R.id.catTransport:
                Log.d(TAG, "Cat Transport");
                category = "Transport";
                initPopUpExpense();
                break;
            case R.id.catShopping:
                Log.d(TAG, "Cat Shopping");
                category = "Shopping";
                initPopUpExpense();
                break;
            case R.id.catPersonalCare:
                Log.d(TAG, "Cat Personal Care");
                category = "Personal Care";
                initPopUpExpense();
                break;
            case R.id.catBills:
                Log.d(TAG, "Cat Bills");
                category = "Bills";
                initPopUpExpense();
                break;
            case R.id.catFood:
                Log.d(TAG, "Cat Food");
                category = "Food";
                initPopUpExpense();
                break;
            case R.id.changeIncome:
                Log.d(TAG, "Change Income");
                initPopUpChangeMonthlyIncome();
                break;
        }
    }
    private void initPopUpExpense()
    {
        Log.d(TAG, "initPopUpExpense");

        AlertDialog.Builder mBuilderExpense = new AlertDialog.Builder(BudgetPieChart.this);

        View mViewExpense = getLayoutInflater().inflate(R.layout.activity_expense, null);
        TextView ExpenseLabel = mViewExpense.findViewById(R.id.ExpenseLabel);

        TextView CategoryLabel = mViewExpense.findViewById(R.id.CategoryLabel);
        CategoryLabel.setText(category);

        final EditText etRM = mViewExpense.findViewById(R.id.etRM);
        final EditText etDescription = mViewExpense.findViewById(R.id.etDescription);
        Button AddButton = mViewExpense.findViewById(R.id.AddButton);

        mBuilderExpense.setView(mViewExpense);
        final AlertDialog dialogExpense = mBuilderExpense.create();


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etRM.getText().toString().isEmpty() && !etDescription.getText().toString().isEmpty())
                {
                    myDB.insertData(etRM.getText().toString(), etDescription.getText().toString(), currentDate, category);
                    Toast.makeText(BudgetPieChart.this, "Add success", Toast.LENGTH_SHORT).show();
                    dialogExpense.cancel();
                }
                else
                {
                    Toast.makeText(BudgetPieChart.this, "Must fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogExpense.show();
    }

    private void initPopUpChangeMonthlyIncome()
    {
        Log.d(TAG, "initPopUpChangeMonthlyIncome");

        AlertDialog.Builder mBuilderChangeMonthlyIncome = new AlertDialog.Builder(BudgetPieChart.this);

        View mViewChangeMonthlyIncome = getLayoutInflater().inflate(R.layout.activity_change_income, null);
        TextView MonthlyIncome = mViewChangeMonthlyIncome.findViewById(R.id.MonthlyIncome);

        final EditText etMonthlyIncome = mViewChangeMonthlyIncome.findViewById(R.id.etMonthlyIncome);

        Button DoneButton = mViewChangeMonthlyIncome.findViewById(R.id.DoneButton);

        mBuilderChangeMonthlyIncome.setView(mViewChangeMonthlyIncome);
        final AlertDialog dialogChangeMonthlyIncome = mBuilderChangeMonthlyIncome.create();

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMonthlyIncome.getText().toString().isEmpty())
                {
                    String varMonthlyIncome = etMonthlyIncome.getText().toString();
                    Toast.makeText(BudgetPieChart.this, "Add success "+varMonthlyIncome, Toast.LENGTH_SHORT).show();
                    MonthlyIncomeLabel.setText("Monthly income: "+varMonthlyIncome);
                    dialogChangeMonthlyIncome.cancel(); //untuk tutup pop up
                }
                else
                {
                    Toast.makeText(BudgetPieChart.this, "Must fill the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogChangeMonthlyIncome.show();
    }
}
