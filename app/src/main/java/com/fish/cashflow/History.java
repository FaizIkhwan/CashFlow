package com.fish.cashflow;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity implements View.OnClickListener {

    //log
    private static String TAG = "History";

    //database
    DatabaseHelper myDB;

    //interface
    TextView historyTV;
    Button backButton, catEntertainmentButton, catEducationButton, catHealthButton, catTransportButton, catShoppingButton, catPersonalCareButton, catBillsButton, catFoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        //Database
        myDB = new DatabaseHelper(this);
    }

    private void initComponent() //interface
    {
        Log.d(TAG, "initComponent");
        //Creating all object components
        historyTV = findViewById(R.id.historyTV);
        backButton = findViewById(R.id.backButton);
        catEntertainmentButton = findViewById(R.id.catEntertainmentButton);
        catEducationButton = findViewById(R.id.catEducationButton);
        catHealthButton = findViewById(R.id.catHealthButton);
        catTransportButton = findViewById(R.id.catTransportButton);
        catShoppingButton = findViewById(R.id.catShoppingButton);
        catPersonalCareButton = findViewById(R.id.catPersonalCareButton);
        catBillsButton = findViewById(R.id.catBillsButton);
        catFoodButton = findViewById(R.id.catFoodButton);
    }

    private void initOnClickListener() // button listener
    {
        Log.d(TAG, "initOnClickListener");
        backButton.setOnClickListener(this);
        catEntertainmentButton.setOnClickListener(this);
        catEducationButton.setOnClickListener(this);
        catHealthButton.setOnClickListener(this);
        catTransportButton.setOnClickListener(this);
        catShoppingButton.setOnClickListener(this);
        catPersonalCareButton.setOnClickListener(this);
        catBillsButton.setOnClickListener(this);
        catFoodButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) // button listener
    {
        Log.d(TAG, "onClick");
        switch(v.getId())
        {
            case R.id.backButton:
                Log.d(TAG, "backButton");
                startActivity(new Intent(this, BudgetPieChart.class));
                break;
            case R.id.catEntertainmentButton:
                Log.d(TAG, "catEntertainmentButton");
                retrievingData("Entertainment");
                break;
            case R.id.catEducationButton:
                Log.d(TAG, "catEducationButton");
                retrievingData("Education");
                break;
            case R.id.catHealthButton:
                Log.d(TAG, "catHealthButton");
                retrievingData("Health");
                break;
            case R.id.catTransportButton:
                Log.d(TAG, "catTransportButton");
                retrievingData("Transport");
                break;
            case R.id.catShoppingButton:
                Log.d(TAG, "catShoppingButton");
                retrievingData("Shopping");
                break;
            case R.id.catPersonalCareButton:
                Log.d(TAG, "catPersonalCareButton");
                retrievingData("Personal Care");
                break;
            case R.id.catBillsButton:
                Log.d(TAG, "catBillsButton");
                retrievingData("Bills");
                break;
            case R.id.catFoodButton:
                Log.d(TAG, "catFoodButton");
                retrievingData("Food");
                break;
        }
    }

    private void initPopUpShowMessage(String title,String Message) // pop up
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void retrievingData(String cat) // retrive data from db
    {
        Cursor res = myDB.getCategoryData(cat);
        if(res.getCount() == 0)
        {
            initPopUpShowMessage("Error","Nothing found");
            return; // keluar terus dari method ini
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext())
        {
            buffer.append("Expense :RM" + res.getString(1) + "\n");
            buffer.append("Description :" + res.getString(2) + "\n");
            buffer.append("Date :" + res.getString(3) + "\n\n");
        }
        initPopUpShowMessage(cat,buffer.toString());
    }
}
