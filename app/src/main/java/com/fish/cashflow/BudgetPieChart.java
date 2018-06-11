package com.fish.cashflow;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BudgetPieChart extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    //log
    private static String TAG = "BudgetPieChart";

    //data for pie chart
    private float[] Data = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};

    //interface
    Button catEntertainment, catEducation, catHealth, catTransport, catShopping, catPersonalCare, catBills, catFood;
    TextView MonthLabel, MonthlyIncomeLabel;
    ProgressBar progressBar;
    ImageButton changeIncome;
    PieChart pieChart;

    //Navigation drawer
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;

    //usable variable
    String category;
    String currentDate;

    //database
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Database
        myDB = new DatabaseHelper(this);

        //Calendar to get current date
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        //to get month
        String month = currentDate.split("\\s")[0];// \\s = splits the string based on whitespace
        MonthLabel.setText(month);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) //Action bar menu, drawer
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

    private void initComponent() // init inteterface
    {
        Log.d(TAG, "initComponent");
        //Creating all object components
        catEntertainment = findViewById(R.id.catEntertainment);
        catEducation = findViewById(R.id.catEducation);
        catHealth = findViewById(R.id.catHealthButton);
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

    private void pieChartSetup() // untuk setup pie chart
    {
        Log.d(TAG, "pieChartSetup");
        pieChart.setRotationEnabled(false);
        pieChart.setBackgroundColor(Color.WHITE);
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(10);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        addDataSet();

        // untuk bila click kt chart, implement listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "e: " + e.toString());
                Log.d(TAG, "h: " + h.toString());

                initPopUpRemainingBudget();
            }

            @Override
            public void onNothingSelected() {

            }
        });
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
            case R.id.catHealthButton:
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

    private void initPopUpRemainingBudget() // pop up bila tekan pie chart
    {
        Log.d(TAG, "initPopUpRemainingBudget");

        AlertDialog.Builder mBuilderRemainingBudget = new AlertDialog.Builder(BudgetPieChart.this);

        View mViewRemainingBudget = getLayoutInflater().inflate(R.layout.activity_remaining_budget, null);
        TextView remainingBudget = mViewRemainingBudget.findViewById(R.id.remainingBudget);
        TextView showRemainingBudget = mViewRemainingBudget.findViewById(R.id.showRemainingBudget);
        Button backButton = mViewRemainingBudget.findViewById(R.id.backButton);

        showRemainingBudget.setText("RM berapa");

        mBuilderRemainingBudget.setView(mViewRemainingBudget);
        final AlertDialog dialogRemainingBudget = mBuilderRemainingBudget.create();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRemainingBudget.cancel();
            }
        });

        dialogRemainingBudget.show();
    }

    private void initPopUpExpense() // setting popup expense
    {
        Log.d(TAG, "initPopUpExpense");

        AlertDialog.Builder mBuilderExpense = new AlertDialog.Builder(BudgetPieChart.this);

        View mViewExpense = getLayoutInflater().inflate(R.layout.activity_expense, null);
        TextView ExpenseLabel = mViewExpense.findViewById(R.id.ExpenseLabel);
        TextView CategoryLabel = mViewExpense.findViewById(R.id.CategoryLabel);
        CategoryLabel.setText(category);

        final EditText etRM = mViewExpense.findViewById(R.id.etRM);
        final EditText etDescription = mViewExpense.findViewById(R.id.etDescription);
        final EditText etDate = mViewExpense.findViewById(R.id.etDate);
        Button AddButton = mViewExpense.findViewById(R.id.AddButton);

        mBuilderExpense.setView(mViewExpense);
        final AlertDialog dialogExpense = mBuilderExpense.create();


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etRM.getText().toString().isEmpty() && !etDescription.getText().toString().isEmpty() && !etDate.getText().toString().isEmpty())
                {
                    myDB.insertData(etRM.getText().toString(), etDescription.getText().toString(), etDate.getText().toString(), category);
                    Toast.makeText(BudgetPieChart.this, "Add success" , Toast.LENGTH_SHORT).show();
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

    private void initPopUpChangeMonthlyIncome() // setting popup changeMonthlyIncomes
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) //bila click kat navigation tu, ada event
    {
        /*
            Syntax
            Intent <intentName> = new Intent(<context>, <otherActivity>.class);
            startActivity(<intentName>);
            or
            startActivity(new Intent(this, activityTwo.class));
        */
        int id = item.getItemId();

        switch (id)
        {
            case R.id.home:
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
            case R.id.category:
                startActivity(new Intent(this, Category.class));
                break;
            case R.id.history:
                startActivity(new Intent(this, History.class));
                break;
            case R.id.wishlist:
                Toast.makeText(this,"wishlist",Toast.LENGTH_SHORT).show();
                break;
            case R.id.statistic:
                Toast.makeText(this,"statistic",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
