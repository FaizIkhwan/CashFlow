package com.fish.cashflow;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class History extends AppCompatActivity implements View.OnClickListener {

    //Log
    private static String TAG = "History";

    //Database
    DatabaseHelper myDB;

    //Interface
    TextView historyTV;
    ImageButton backButton, catEntertainmentButton, catEducationButton, catHealthButton, catTransportButton, catShoppingButton, catPersonalCareButton, catBillsButton, catFoodButton;

    //Variable to use
    private String[] cat = {"ENTERTAINMENT", "EDUCATION", "HEALTH", "TRANSPORT", "SHOPPING", "PERSONAL CARE", "BILLS", "FOOD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initComponent(); //Initialize components.
        initOnClickListener(); //Initialize onClickListener.

        //Database
        myDB = new DatabaseHelper(this);

        checkStateForEachCategory();
    }

    /**
     * Define the UI.
     */
    private void initComponent()
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

    /**
     * Implementing OnClickListener for each button.
     */
    private void initOnClickListener()
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
                Log.d(TAG, "backButton");
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
            case R.id.catEntertainmentButton:
                Log.d(TAG, "catEntertainmentButton");
                retrievingData("ENTERTAINMENT");
                break;
            case R.id.catEducationButton:
                Log.d(TAG, "catEducationButton");
                retrievingData("EDUCATION");
                break;
            case R.id.catHealthButton:
                Log.d(TAG, "catHealthButton");
                retrievingData("HEALTH");
                break;
            case R.id.catTransportButton:
                Log.d(TAG, "catTransportButton");
                retrievingData("TRANSPORT");
                break;
            case R.id.catShoppingButton:
                Log.d(TAG, "catShoppingButton");
                retrievingData("SHOPPING");
                break;
            case R.id.catPersonalCareButton:
                Log.d(TAG, "catPersonalCareButton");
                retrievingData("PERSONAL CARE");
                break;
            case R.id.catBillsButton:
                Log.d(TAG, "catBillsButton");
                retrievingData("BILLS");
                break;
            case R.id.catFoodButton:
                Log.d(TAG, "catFoodButton");
                retrievingData("FOOD");
                break;
        }
    }

    /**
     * Creating pop up when the user click on category button.
     * @param title
     * @param Message
     */
    private void initPopUpShowMessage(String title,String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    /**
     * Retrieve data from table Expense in the database.
     * @param cat
     */
    private void retrievingData(String cat)
    {
        String temp;
        Cursor res = myDB.getCategoryDataFromExpense(cat);
        if(res.getCount() == 0)
        {
            initPopUpShowMessage("Error","Nothing found");
            return; // Exit this method.
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext())
        {
            buffer.append("Expense :RM" + res.getString(1) + "\n");
            buffer.append("Description :" + res.getString(2) + "\n");
            temp = res.getString(3).substring(6, 8)+"/"+res.getString(3).substring(4, 6)+"/"+res.getString(3).substring(0, 4);
            Log.d(TAG, "TEMP ---> "+temp);
            buffer.append("Date :" + temp + "\n\n");
        }
        initPopUpShowMessage(cat,buffer.toString());
    }

    /**
     * Process to check if the category STATE is TRUE or FALSE.
     * If it is TRUE, button will appear.
     * Else, it will not.
     */
    private void checkStateForEachCategory()
    {
        Log.d(TAG, "checkStateForEachCategory");

        for (int i = 0; i < cat.length; i++)
        {
            Cursor res = myDB.getStateForCategory(cat[i]);
            if(res != null && res.moveToFirst()) // If the query result is not empty.
            {
                switch (res.getString(0))
                {
                    case "ENTERTAINMENT":
                        if( res.getString(1).equals("TRUE") )
                            catEntertainmentButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catEntertainmentButton.setVisibility(View.INVISIBLE);
                        break;
                    case "EDUCATION":
                        if( res.getString(1).equals("TRUE") )
                            catEducationButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catEducationButton.setVisibility(View.INVISIBLE);
                        break;
                    case "HEALTH":
                        if( res.getString(1).equals("TRUE") )
                            catHealthButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catHealthButton.setVisibility(View.INVISIBLE);
                        break;
                    case "TRANSPORT":
                        if( res.getString(1).equals("TRUE") )
                            catTransportButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catTransportButton.setVisibility(View.INVISIBLE);
                        break;
                    case "SHOPPING":
                        if( res.getString(1).equals("TRUE") )
                            catShoppingButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catShoppingButton.setVisibility(View.INVISIBLE);
                        break;
                    case "PERSONAL CARE":
                        if( res.getString(1).equals("TRUE") )
                            catPersonalCareButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catPersonalCareButton.setVisibility(View.INVISIBLE);
                        break;
                    case "BILLS":
                        if( res.getString(1).equals("TRUE") )
                            catBillsButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catBillsButton.setVisibility(View.INVISIBLE);
                        break;
                    case "FOOD":
                        if( res.getString(1).equals("TRUE") )
                            catFoodButton.setVisibility(View.VISIBLE);
                        else if( res.getString(1).equals("FALSE") )
                            catFoodButton.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        }
    }
}
