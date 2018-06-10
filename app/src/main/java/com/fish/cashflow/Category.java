package com.fish.cashflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Category extends AppCompatActivity implements View.OnClickListener {

    //log
    private static String TAG = "Category";

    //Interface
    TextView categoryTV, catEntertainmentTV, catEducationTV, catHealthTV, catTransportTV, catShoppingTV, catPersonalCareTV, catBillsTV, catFoodTV;
    ImageButton catEntertainmentPlus, catEntertainmentMinus, catEducationPlus, catEducationMinus, catHealthPlus, catHealthMinus, catTransportPlus,
        catTransportMinus, catShoppingPlus, catShoppingMinus, catPersonalCarePlus, catPersonalCareMinus, catBillsPlus, catBillsMinus, catFoodPlus, catFoodMinus;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d(TAG, "onCreate: Category");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener
    }

    private void initComponent()
    {
        Log.d(TAG, "initComponent");
        //Creating all object components
        categoryTV = findViewById(R.id.categoryTV);
        catEntertainmentTV = findViewById(R.id.catEntertainmentTV);
        catEducationTV = findViewById(R.id.catEducationTV);
        catHealthTV = findViewById(R.id.catHealthTV);
        catTransportTV = findViewById(R.id.catTransportTV);
        catShoppingTV = findViewById(R.id.catShoppingTV);
        catPersonalCareTV = findViewById(R.id.catPersonalCareTV);
        catBillsTV = findViewById(R.id.catBillsTV);
        catFoodTV = findViewById(R.id.catFoodTV);
        catEntertainmentPlus = findViewById(R.id.catEntertainmentPlus);
        catEntertainmentMinus = findViewById(R.id.catEntertainmentMinus);
        catEducationPlus = findViewById(R.id.catEducationPlus);
        catEducationMinus = findViewById(R.id.catEducationMinus);
        catHealthPlus = findViewById(R.id.catHealthPlus);
        catHealthMinus = findViewById(R.id.catHealthMinus);
        catTransportPlus = findViewById(R.id.catTransportPlus);
        catTransportMinus = findViewById(R.id.catTransportMinus);
        catShoppingPlus = findViewById(R.id.catShoppingPlus);
        catShoppingMinus = findViewById(R.id.catShoppingMinus);
        catPersonalCarePlus = findViewById(R.id.catPersonalCarePlus);
        catPersonalCareMinus = findViewById(R.id.catPersonalCareMinus);
        catBillsPlus = findViewById(R.id.catBillsPlus);
        catBillsMinus = findViewById(R.id.catBillsMinus);
        catFoodPlus = findViewById(R.id.catFoodPlus);
        catFoodMinus = findViewById(R.id.catFoodMinus);
        backButton = findViewById(R.id.backButton);
    }

    private void initOnClickListener() // Method for set on click listener for all button
    {
        Log.d(TAG, "initOnClickListener");
        catEntertainmentPlus.setOnClickListener(this);
        catEntertainmentMinus.setOnClickListener(this);
        catEducationPlus.setOnClickListener(this);
        catEducationMinus.setOnClickListener(this);
        catHealthPlus.setOnClickListener(this);
        catHealthMinus.setOnClickListener(this);
        catTransportPlus.setOnClickListener(this);
        catTransportMinus.setOnClickListener(this);
        catShoppingPlus.setOnClickListener(this);
        catShoppingMinus.setOnClickListener(this);
        catPersonalCarePlus.setOnClickListener(this);
        catPersonalCareMinus.setOnClickListener(this);
        catBillsPlus.setOnClickListener(this);
        catBillsMinus.setOnClickListener(this);
        catFoodPlus.setOnClickListener(this);
        catFoodMinus.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

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
            case R.id.catEntertainmentPlus:
                Toast.makeText(this,"catEntertainmentPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catEntertainmentMinus:
                Toast.makeText(this,"catEntertainmentMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catEducationPlus:
                Toast.makeText(this,"catEducationPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catEducationMinus:
                Toast.makeText(this,"catEducationMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catHealthPlus:
                Toast.makeText(this,"catHealthPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catHealthMinus:
                Toast.makeText(this,"catHealthMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catTransportPlus:
                Toast.makeText(this,"catTransportPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catTransportMinus:
                Toast.makeText(this,"catTransportMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catShoppingPlus:
                Toast.makeText(this,"catShoppingPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catShoppingMinus:
                Toast.makeText(this,"catShoppingMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catPersonalCarePlus:
                Toast.makeText(this,"catPersonalCarePlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catPersonalCareMinus:
                Toast.makeText(this,"catPersonalCareMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catBillsPlus:
                Toast.makeText(this,"catBillsPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catBillsMinus:
                Toast.makeText(this,"catBillsMinus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catFoodPlus:
                Toast.makeText(this,"catFoodPlus",Toast.LENGTH_SHORT).show();
                break;
            case R.id.catFoodMinus:
                Toast.makeText(this,"catFoodMinus",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
