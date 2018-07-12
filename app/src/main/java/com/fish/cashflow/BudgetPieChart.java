package com.fish.cashflow;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BudgetPieChart extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener
{
    //Log
    private static String TAG = "BudgetPieChart";

    //Interface
    private TextView MonthLabel, MonthlyIncomeLabel, berapaPercentTV;
    private ProgressBar progressBar;
    private ImageButton changeIncome, catEntertainment, catEducation, catHealth, catTransport, catShopping, catPersonalCare, catBills, catFood;
    private PieChart pieChart;

    //Navigation drawer
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;

    //Variable to use
    private String category;
    private String currentDate;
    private String month;
    private String monthToDisplay;
    private String dateForProgressBar; //YYYYMM
    private String[] cat = {"ENTERTAINMENT", "EDUCATION", "HEALTH", "TRANSPORT", "SHOPPING", "PERSONAL CARE", "BILLS", "FOOD"};
    private String[] monthInWords = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private static double savings;

    //Variable for spinner
    private String changeMonthlyIncomeSpinnerRes;
    private String expenseSpinnerDay;
    private String expenseSpinnerMonth;
    private String expenseSpinnerYear;

    //Database
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_pie_chart);

        Log.d(TAG, "onCreate: BudgetPieChart");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        //Database
        myDB = new DatabaseHelper(this);

        whichToDisplayCategory(); // Need to put this first. Important. Else, bug.
        pieChartSetup();
        settingMonthlyIncome();

        //Drawer
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        monthToDisplay = getDateAndMonth(); // Will get value of months ABC. Example "JANUARY", "JULY".
        MonthLabel.setText(monthToDisplay);

        String monthlyIncomeToDisplay = "Monthly income: "+whatToDisplayMonthlyIncome();
        MonthlyIncomeLabel.setText(monthlyIncomeToDisplay);

        settingProgressBar();
    }

    /**
     * To check table Income is empty or not.
     * If it is empty, will add Income = 0 for each month.
     * else, ignore.
     */
    private void settingMonthlyIncome()
    {
        for(int i=0; i<monthInWords.length; i++)
        {
            Cursor res = myDB.getMonthlyIncome(monthInWords[i]);
            if(res != null && res.moveToFirst()) // If the query result is not empty.
                return;
            else
                myDB.insertDataIncome("0", monthInWords[i]);
        }
    }

    /**
     * To show percentage of the progress bar.
     */
    private void settingProgressBar()
    {
        dateForProgressBar = currentDate.split("-")[0]+currentDate.split("-")[1]; //YYYYMM

        double totalExpenseForThisMonth = 0, incomeForThisMonth = 0, percentCalculation = 0;

        Cursor res = myDB.calculatingTotalExpenseForAllCategory(dateForProgressBar);
        if(res != null && res.moveToFirst()) // If the query result is not empty.
        {
            Log.d(TAG, "TOTAL EXPENSE FOR THIS MONTH ---> "+res.getString(0));
            if(res.getString(0) == null)
                totalExpenseForThisMonth = 0;
            else
                totalExpenseForThisMonth = Double.parseDouble(res.getString(0));
        }

        Cursor res2 = myDB.getMonthlyIncome(monthToDisplay);
        if(res2 != null && res2.moveToFirst()) // If the query result is not empty.
        {
            Log.d(TAG, "MONTHLY INCOME FOR THIS MONTH ---> "+res2.getString(1));
            if(res2.getString(1) == null)
                incomeForThisMonth = 0;
            else
                incomeForThisMonth = Double.parseDouble(res2.getString(1));
        }

        //Calculating SAVINGS
        savings = incomeForThisMonth - totalExpenseForThisMonth;
        Log.d(TAG, "SAVINGSSSS ---> "+savings);

        //Change it into percentage
        percentCalculation =  (savings/incomeForThisMonth) * 100;
        int percentToDisplay = (int) percentCalculation;
        Log.d(TAG, "PERCENT CALCULATION ---> "+percentCalculation);
        Log.d(TAG, "PERCENT TO DISPLAY ---> "+percentToDisplay);

        if(savings == 0 && incomeForThisMonth == 0)
            return;

        progressBar.setProgress(percentToDisplay);

        DecimalFormat df = new DecimalFormat("#0.00");
        berapaPercentTV.setText(df.format(percentToDisplay)+"%");
    }

    /**
     * To check in current month have income or not.
     * If it have income, it will return the amount of income.
     * Else, return 0.
     * @return
     */
    private String whatToDisplayMonthlyIncome()
    {
        Log.d(TAG, "whatToDisplayMonthlyIncome");
        Cursor res = myDB.getMonthlyIncome(monthToDisplay);
        if(res != null && res.moveToFirst()) // If the query result is not empty.
            return res.getString(1);
        else
            return "0";
    }

    /**
     * To return month in words.
     * @return res
     */
    private String getDateAndMonth()
    {
        Log.d(TAG, "getDateAndMonth");
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        month = currentDate.split("-")[1];
        String res = "";
        switch (month)
        {
            case "01":
                res = "JANUARY";
                break;
            case "02":
                res = "FEBRUARY";
                break;
            case "03":
                res = "MARCH";
                break;
            case "04":
                res = "APRIL";
                break;
            case "05":
                res = "MAY";
                break;
            case "06":
                res = "JUNE";
                break;
            case "07":
                res = "JULY";
                break;
            case "08":
                res = "AUGUST";
                break;
            case "09":
                res = "SEPTEMBER";
                break;
            case "10":
                res = "OCTOBER";
                break;
            case "11":
                res = "NOVEMBER";
                break;
            case "12":
                res =  "DECEMBER";
                break;
        }
        return res;
    }

    /**
     * Process to check if the category STATE is TRUE or FALSE.
     * If it is TRUE, button will appear.
     * Else, it will not.
     */
    private void whichToDisplayCategory()
    {
        for(int i=0; i<cat.length; i++)
        {
            Cursor res = myDB.getStateForCategory(cat[i].toUpperCase());
            if(res != null && res.moveToFirst()) // If the query result is not empty.
            {
                do
                {
                    switch ( res.getString(0) )
                    {
                        case "ENTERTAINMENT":
                            if( res.getString(1).equals("TRUE") )
                                catEntertainment.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catEntertainment.setVisibility(View.INVISIBLE);
                            break;
                        case "EDUCATION":
                            if( res.getString(1).equals("TRUE") )
                                catEducation.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catEducation.setVisibility(View.INVISIBLE);
                            break;
                        case "HEALTH":
                            if( res.getString(1).equals("TRUE") )
                                catHealth.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catHealth.setVisibility(View.INVISIBLE);
                            break;
                        case "TRANSPORT":
                            if( res.getString(1).equals("TRUE") )
                                catTransport.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catTransport.setVisibility(View.INVISIBLE);
                            break;
                        case "SHOPPING":
                            if( res.getString(1).equals("TRUE") )
                                catShopping.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catShopping.setVisibility(View.INVISIBLE);
                            break;
                        case "PERSONAL CARE":
                            if( res.getString(1).equals("TRUE") )
                                catPersonalCare.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catPersonalCare.setVisibility(View.INVISIBLE);
                            break;
                        case "BILLS":
                            if( res.getString(1).equals("TRUE") )
                                catBills.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catBills.setVisibility(View.INVISIBLE);
                            break;
                        case "FOOD":
                            if( res.getString(1).equals("TRUE") )
                                catFood.setVisibility(View.VISIBLE);
                            else if( res.getString(1).equals("FALSE") )
                                catFood.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
                while(res.moveToNext());
            }
            else // If the query result is empty.
                myDB.insertDataCategory(cat[i].toUpperCase(), "0", "TRUE".toUpperCase()); // letak semua category TRUE
        }
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

    /**
     * Process to add data set for piechart.
     */
    private void addDataSet()
    {
        Log.d(TAG, "addDataSet started");

        //Data for pie chart
        float[] Data = {0, 0, 0, 0, 0, 0, 0, 0};

        //Add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        for(int i=0; i<cat.length; i++)
        {
            Cursor res = myDB.getStateForCategory(cat[i]);
            if(res != null && res.moveToFirst()) // If the query result is not empty.
            {
                do {
                    switch ( res.getString(0) )
                    {
                        case "ENTERTAINMENT":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFFf44165); // catEntertainment
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFFf44165))
                                    colors.remove(0xFFf44165); // catEntertainment
                                Data[i] = 0;
                            }
                            break;

                        case "EDUCATION":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFF8fc6ab); // catEducation
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFF8fc6ab))
                                    colors.remove(0xFF8fc6ab); // catEducation
                                Data[i] = 0;
                            }
                            break;

                        case "HEALTH":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFF85e4f7); // catHealth
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFF85e4f7))
                                    colors.remove(0xFF85e4f7); // catHealth
                                Data[i] = 0;
                            }
                            break;

                        case "TRANSPORT":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFFe1ea9d); // catTransport
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFFe1ea9d))
                                    colors.remove(0xFFe1ea9d); // catTransport
                                Data[i] = 0;
                            }
                            break;
                        case "SHOPPING":

                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFF9a60af); // catShopping
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFF9a60af))
                                    colors.remove(0xFF9a60af); // catShopping
                                Data[i] = 0;
                            }
                            break;

                        case "PERSONAL CARE":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFFcc90c3); // catPersonalCare
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFFcc90c3))
                                    colors.remove(0xFFcc90c3); // catPersonalCare
                                Data[i] = 0;
                            }
                            break;

                        case "BILLS":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFF739b8a); // catBills
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFF739b8a))
                                    colors.remove(0xFF739b8a); // catBills
                                Data[i] = 0;
                            }
                            break;

                        case "FOOD":
                            if( res.getString(1).equals("TRUE") )
                            {
                                colors.add(0xFFb24c08); // catFood
                                Data[i] = 1f;
                            }
                            else
                            {
                                if(colors.contains(0xFFb24c08))
                                    colors.remove(0xFFb24c08); // catFood
                                Data[i] = 0;
                            }
                            break;
                    }
                }while(res.moveToNext());
            }
        }

        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for(int i = 0; i < Data.length; i++)
            yEntrys.add(new PieEntry(Data[i] , i));

        //Create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"");
        pieDataSet.setDrawValues(false);
        pieDataSet.setColors(colors);

        //Create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    /**
     * Define the UI.
     */
    private void initComponent()
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
        berapaPercentTV = findViewById(R.id.berapaPercentTV);
    }

    /**
     * Setting up the pie chart.
     */
    private void pieChartSetup()
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

        // Implementing listener for each section in pie chart.
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "e: " + e.toString());
                Log.d(TAG, "h: " + h.toString());
                String temp = h.toString().substring(14,15);
                initPopUpRemainingBudget(temp);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * Implementing OnClickListener for each button.
     */
    private void initOnClickListener()
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

    /**
     * OnClick method for each button.
     * @param v
     */
    public void onClick(View v)
    {
        Log.d(TAG, "onClick");
        switch (v.getId())
        {
            case R.id.catEntertainment:
                Log.d(TAG, "Cat Entertainment");
                category = "ENTERTAINMENT";
                initPopUpExpense();
                break;
            case R.id.catEducation:
                Log.d(TAG, "Cat Education");
                category = "EDUCATION";
                initPopUpExpense();
                break;
            case R.id.catHealthButton:
                Log.d(TAG, "Cat Health");
                category = "HEALTH";
                initPopUpExpense();
                break;
            case R.id.catTransport:
                Log.d(TAG, "Cat Transport");
                category = "TRANSPORT";
                initPopUpExpense();
                break;
            case R.id.catShopping:
                Log.d(TAG, "Cat Shopping");
                category = "SHOPPING";
                initPopUpExpense();
                break;
            case R.id.catPersonalCare:
                Log.d(TAG, "Cat Personal Care");
                category = "PERSONAL CARE";
                initPopUpExpense();
                break;
            case R.id.catBills:
                Log.d(TAG, "Cat Bills");
                category = "BILLS";
                initPopUpExpense();
                break;
            case R.id.catFood:
                Log.d(TAG, "Cat Food");
                category = "FOOD";
                initPopUpExpense();
                break;
            case R.id.changeIncome:
                Log.d(TAG, "Change Income");
                initPopUpChangeMonthlyIncome();
                break;
        }
    }

    /**
     * Creating pop up when the user click on pie chart.
     * @param temp
     */
    private void initPopUpRemainingBudget(String temp)
    {
        Log.d(TAG, "initPopUpRemainingBudget");

        String cat="";
        switch (temp)
        {
            case "0":
                cat = "ENTERTAINMENT";
                break;
            case "1":
                cat = "EDUCATION";
                break;
            case "2":
                cat = "HEALTH";
                break;
            case "3":
                cat = "TRANSPORT";
                break;
            case "4":
                cat = "SHOPPING";
                break;
            case "5":
                cat = "PERSONAL CARE";
                break;
            case "6":
                cat = "BILLS";
                break;
            case "7":
                cat = "FOOD";
                break;
        }

        AlertDialog.Builder mBuilderRemainingBudget = new AlertDialog.Builder(BudgetPieChart.this);

        View mViewRemainingBudget = getLayoutInflater().inflate(R.layout.activity_remaining_budget, null);
        TextView remainingBudget = mViewRemainingBudget.findViewById(R.id.remainingBudget);
        TextView showRemainingBudget = mViewRemainingBudget.findViewById(R.id.showRemainingBudget);
        Button backButton = mViewRemainingBudget.findViewById(R.id.backButton);

        double budget = 0;
        Cursor res = myDB.getStateForCategory(cat);
        if(res != null && res.moveToFirst()) // If the query result is not empty.
        {
            remainingBudget.setText("Remaining Budget "+res.getString(1));
            budget = Double.parseDouble(res.getString(2));
            Log.d(TAG, "BUDGET --->" +budget);
        }
        else
            remainingBudget.setText("Remaining Budget ");

        double totalExpense = 0;
        String dateToPass = currentDate.split("-")[0]+currentDate.split("-")[1];
        Log.d(TAG, "DATE TO PASS --->" +dateToPass);
        Log.d(TAG, "CAT --->" +cat);
        Cursor res2 = myDB.calculatingTotalExpense(dateToPass, cat);
        if(res2 != null && res2.moveToFirst()) // If the query result is not empty.
        {
            if(res2.getString(0) == null)
            {
                totalExpense = 0;
                Log.d(TAG, "TOTAL EXPENSE = 0");
            }
            else
            {
                Log.d(TAG, "TOTAL EXPENSE --->" +res2.getString(0));
                totalExpense = Double.parseDouble(res2.getString(0));
            }

        }

        Log.d(TAG, "TOTAL EXPENSE --->" +totalExpense);

        double remainingBudgetToDisplay = budget - totalExpense;
        showRemainingBudget.setText("RM "+remainingBudgetToDisplay);

        mBuilderRemainingBudget.setView(mViewRemainingBudget);
        final AlertDialog dialogRemainingBudget = mBuilderRemainingBudget.create();

        /*
            When the user click "Back" button.
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRemainingBudget.cancel();
            }
        });

        dialogRemainingBudget.show();
        settingProgressBar();
    }

    /**
     * Creating pop up when the user click on category button.
     */
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
        Spinner spinnerExpenseDateDay = mViewExpense.findViewById(R.id.spinnerExpenseDateDay);
        Spinner spinnerExpenseDateMonth = mViewExpense.findViewById(R.id.spinnerExpenseDateMonth);
        Spinner spinnerExpenseDateYear = mViewExpense.findViewById(R.id.spinnerExpenseDateYear);

        //Spinner DAY
        ArrayAdapter<CharSequence> adapterExpenseDay = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapterExpenseDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpenseDateDay.setAdapter(adapterExpenseDay);
        spinnerExpenseDateDay.setOnItemSelectedListener(this);

        //Spinner MONTH
        ArrayAdapter<CharSequence> adapterExpenseMonth = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapterExpenseMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpenseDateMonth.setAdapter(adapterExpenseMonth);
        spinnerExpenseDateMonth.setOnItemSelectedListener(this);

        //Spinner YEAR
        ArrayAdapter<CharSequence> adapterExpenseYear = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        adapterExpenseYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpenseDateYear.setAdapter(adapterExpenseYear);
        spinnerExpenseDateYear.setOnItemSelectedListener(this);

        mBuilderExpense.setView(mViewExpense);
        final AlertDialog dialogExpense = mBuilderExpense.create();

        /*
            When user click "Add" button.
         */
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etRM.getText().toString().isEmpty() && !etDescription.getText().toString().isEmpty() && !expenseSpinnerYear.equalsIgnoreCase("Year") && !expenseSpinnerMonth.equalsIgnoreCase("Month") && !expenseSpinnerDay.equalsIgnoreCase("Day"))
                {
                    String expense, date, description;

                    expense = etRM.getText().toString().toUpperCase();
                    description = etDescription.getText().toString().toUpperCase();
                    date = expenseSpinnerYear+expenseSpinnerMonth+expenseSpinnerDay;
                    Log.d(TAG, "DATE YANG DI INPUT ---->"+date);

                    myDB.insertDataExpense(expense, description, date, category.toUpperCase());
                    Toast.makeText(BudgetPieChart.this, "Add success" , Toast.LENGTH_SHORT).show();
                    dialogExpense.cancel();
                    settingProgressBar();
                }
                else
                {
                    Toast.makeText(BudgetPieChart.this, "Must fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogExpense.show();
    }

    /**
     * Creating pop up when the user click on add monthly income button.
     */
    private void initPopUpChangeMonthlyIncome()
    {
        Log.d(TAG, "initPopUpChangeMonthlyIncome");

        AlertDialog.Builder mBuilderChangeMonthlyIncome = new AlertDialog.Builder(BudgetPieChart.this);

        View mViewChangeMonthlyIncome = getLayoutInflater().inflate(R.layout.activity_change_income, null);
        TextView MonthlyIncome = mViewChangeMonthlyIncome.findViewById(R.id.MonthlyIncome);
        final EditText etMonthlyIncome = mViewChangeMonthlyIncome.findViewById(R.id.etMonthlyIncome);
        Spinner spinnerChangeMonthlyIncome = mViewChangeMonthlyIncome.findViewById(R.id.spinnerChangeMonthlyIncome);
        Button DoneButton = mViewChangeMonthlyIncome.findViewById(R.id.DoneButton);

        //Spinner
        ArrayAdapter<CharSequence> adapterMonthlyIncome = ArrayAdapter.createFromResource(this, R.array.monthSpelling, android.R.layout.simple_spinner_item);
        adapterMonthlyIncome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChangeMonthlyIncome.setAdapter(adapterMonthlyIncome);
        spinnerChangeMonthlyIncome.setOnItemSelectedListener(this);

        mBuilderChangeMonthlyIncome.setView(mViewChangeMonthlyIncome);
        final AlertDialog dialogChangeMonthlyIncome = mBuilderChangeMonthlyIncome.create();

        /*
            When user click on "Done" button.
         */
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMonthlyIncome.getText().toString().isEmpty() && !changeMonthlyIncomeSpinnerRes.equalsIgnoreCase("Month")) // If the user fill all the details.
                {
                    String varMonthlyIncome = etMonthlyIncome.getText().toString(); // Get what user input.

                    Cursor res = myDB.getMonthlyIncome(changeMonthlyIncomeSpinnerRes); // Query to get income based on month user pick.
                    if(res != null && res.moveToFirst()) // If the query result is not empty.
                    {
                        myDB.updateMonthlyIncome(res.getString(0), varMonthlyIncome, changeMonthlyIncomeSpinnerRes); // Update row ID, Amount monthly income, Spinner result.
                        Toast.makeText(BudgetPieChart.this, "Add success", Toast.LENGTH_SHORT).show();
                        dialogChangeMonthlyIncome.cancel(); // To close the pop up.

                        if(changeMonthlyIncomeSpinnerRes.equalsIgnoreCase(monthToDisplay)) // If user choose the month same with system month. Example user choose = "JUNE", system = "JUNE".
                        {
                            MonthlyIncomeLabel.setText("Monthly income: "+varMonthlyIncome);
                            settingProgressBar();
                        }
                    }
                    else
                    {
                        Log.d(TAG, "initPopUpChangeMonthlyIncome");
                        Toast.makeText(BudgetPieChart.this, "AN ERROR HAS OCCUR!", Toast.LENGTH_SHORT).show();
                    }
                }
                else // User does not fill all the detail.
                {
                    Toast.makeText(BudgetPieChart.this, "Must fill the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogChangeMonthlyIncome.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) //When user click on navigation, there is an event.
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.home:
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
            case R.id.category:
                startActivity(new Intent(this, Category.class));
                finish();
                break;
            case R.id.history:
                startActivity(new Intent(this, History.class));
                finish();
                break;
            case R.id.savings:
                startActivity(new Intent(this, Saving.class));
                finish();
                break;
            case R.id.wishlist:
                startActivity(new Intent(this, Wishlist.class));
                finish();
                break;
            case R.id.statistic:
                startActivity(new Intent(this, Statistic.class));
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) // Spinner
    {
        switch (parent.getId())
        {
            case R.id.spinnerChangeMonthlyIncome:
                changeMonthlyIncomeSpinnerRes = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerExpenseDateDay:
                expenseSpinnerDay = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerExpenseDateMonth:
                expenseSpinnerMonth = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerExpenseDateYear:
                expenseSpinnerYear = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) // Spinner
    {

    }
}
