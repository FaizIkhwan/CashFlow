package com.fish.cashflow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.util.ArrayList;

public class Statistic extends AppCompatActivity implements View.OnClickListener{

    //Database
    DatabaseHelper myDB;

    //Log
    private static String TAG = "BudgetPieChart";

    //Interface
    private ImageButton backButton;
    private TextView statisticTV;
    private BarChart barChart;

    //Variable to use
    String[] monthInNumber = {"201801", "201802", "201803", "201804", "201805", "201806", "201807", "201808", "201809", "201810", "201811", "201812"};
    String[] month = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        Log.d(TAG, "onCreate: Statistic");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        //Database
        myDB = new DatabaseHelper(this);

        barChartSetting();
    }

    /**
     * Define the UI.
     */
    private void initComponent()
    {
        backButton = findViewById(R.id.backButton);
        statisticTV = findViewById(R.id.statisticTV);
        barChart = findViewById(R.id.barChart);
    }

    /**
     * Implementing OnClickListener for each button.
     */
    private void initOnClickListener()
    {
        backButton.setOnClickListener(this);
    }

    /**
     * Bar chart setting and styling.
     */
    private void barChartSetting()
    {
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(50);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getAxisLeft().setAxisMinimum(0f);

        float monthlyIncome, monthlyExpense;

        ArrayList<BarEntry> income = new ArrayList<>();
        ArrayList<BarEntry> sumExpense = new ArrayList<>();

        for(int i=0; i<month.length; i++)
        {
            Log.d(TAG, "MONTH---> "+month[i]);
            Cursor res1 = myDB.getMonthlyIncome(month[i]);
            if(res1 != null && res1.moveToFirst()) // If the query result is not empty.
            {
                if(res1.getString(1) == null)
                    monthlyIncome = 0;
                else
                    monthlyIncome = Float.parseFloat(res1.getString(1));

                Log.d(TAG, "MONTHLY INCOME ---> "+monthlyIncome);
                income.add(new BarEntry(i, monthlyIncome));
            }

            Cursor res2 = myDB.calculatingTotalExpenseForAllCategory(monthInNumber[i]);
            if(res2 != null && res2.moveToFirst()) // If the query result is not empty.
            {
                if(res2.getString(0) == null)
                    monthlyExpense = 0;
                else
                    monthlyExpense = Float.parseFloat(res2.getString(0));

                Log.d(TAG, "MONTHLY TOTAL EXPENSE ---> "+monthlyExpense);
                sumExpense.add(new BarEntry(i, monthlyExpense));
            }
        }

        BarDataSet barDataSet1 = new BarDataSet(income, "Monthly income");
        barDataSet1.setColor(0xFF85e4f7);

        BarDataSet barDataSet2 = new BarDataSet(sumExpense, "Sum monthly expenses");
        barDataSet2.setColor(0xFF9a60af);

        BarData data = new BarData(barDataSet1, barDataSet2);

        data.setDrawValues(false);

        float groupSpace = 0.1f;
        float barSpace = 0.02f;
        float barWidth = 0.43f;

        barChart.setData(data);

        data.setBarWidth(barWidth);
        barChart.groupBars(1, groupSpace, barSpace);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(month));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setAxisMinimum(1);

        YAxis yAxis = barChart.getAxisRight();
        yAxis.setEnabled(false);
    }

    /**
     * Bar chart X axis.
     */
    public class MyXAxisValueFormatter implements IAxisValueFormatter
    {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values)
        {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }

    /**
     * OnClick method for each button.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
        }
    }
}


