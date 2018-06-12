package com.fish.cashflow;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Category extends AppCompatActivity implements View.OnClickListener {

    //log
    private static String TAG = "Category";

    //Interface
    Button backButton;
    TextView categoryTV, catEntertainmentTV, catEducationTV, catHealthTV, catTransportTV, catShoppingTV, catPersonalCareTV, catBillsTV, catFoodTV;
    ImageButton catEntertainmentPlus, catEntertainmentMinus, catEducationPlus, catEducationMinus, catHealthPlus, catHealthMinus, catTransportPlus,
            catTransportMinus, catShoppingPlus, catShoppingMinus, catPersonalCarePlus, catPersonalCareMinus, catBillsPlus, catBillsMinus, catFoodPlus, catFoodMinus,
                addBudgetEntertainment, addBudgetEducation, addBudgetHealth, addBudgetTransport, addBudgetShopping, addBudgetPersonalCare, addBudgetBills, addBudgetFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d(TAG, "onCreate: Category");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        // if category == 8
        closeAllPlus();
    }

    private void initComponent()
    {
        Log.d(TAG, "initComponent");
        //Creating all object components
        backButton = findViewById(R.id.backButton);
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

        addBudgetEntertainment = findViewById(R.id.addBudgetEntertainment);
        addBudgetEducation = findViewById(R.id.addBudgetEducation);
        addBudgetHealth = findViewById(R.id.addBudgetHealth);
        addBudgetTransport = findViewById(R.id.addBudgetTransport);
        addBudgetShopping = findViewById(R.id.addBudgetShopping);
        addBudgetPersonalCare = findViewById(R.id.addBudgetPersonalCare);
        addBudgetBills = findViewById(R.id.addBudgetBills);
        addBudgetFood = findViewById(R.id.addBudgetFood);
    }

    private void initOnClickListener() // Method for set on click listener for all button
    {
        Log.d(TAG, "initOnClickListener");
        backButton.setOnClickListener(this);

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

        addBudgetEntertainment.setOnClickListener(this);
        addBudgetEducation.setOnClickListener(this);
        addBudgetHealth.setOnClickListener(this);
        addBudgetTransport.setOnClickListener(this);
        addBudgetShopping.setOnClickListener(this);
        addBudgetPersonalCare.setOnClickListener(this);
        addBudgetBills.setOnClickListener(this);
        addBudgetFood.setOnClickListener(this);
    }

    private void closeAllPlus() // invisible semua plus button kalau catogory ada 8
    {
        catEntertainmentPlus.setVisibility(View.INVISIBLE);
        catEducationPlus.setVisibility(View.INVISIBLE);
        catHealthPlus.setVisibility(View.INVISIBLE);
        catTransportPlus.setVisibility(View.INVISIBLE);
        catShoppingPlus.setVisibility(View.INVISIBLE);
        catPersonalCarePlus.setVisibility(View.INVISIBLE);
        catBillsPlus.setVisibility(View.INVISIBLE);
        catFoodPlus.setVisibility(View.INVISIBLE);
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
                // ++++++
                catEntertainmentPlus.setVisibility(View.INVISIBLE);
                catEntertainmentMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catEntertainmentMinus:
                // ------
                catEntertainmentPlus.setVisibility(View.VISIBLE);
                catEntertainmentMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catEducationPlus:
                // ++++++
                catEducationPlus.setVisibility(View.INVISIBLE);
                catEducationMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catEducationMinus:
                // ------
                catEducationPlus.setVisibility(View.VISIBLE);
                catEducationMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catHealthPlus:
                // ++++++
                catHealthPlus.setVisibility(View.INVISIBLE);
                catHealthMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catHealthMinus:
                // ------
                catHealthPlus.setVisibility(View.VISIBLE);
                catHealthMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catTransportPlus:
                // ++++++
                catTransportPlus.setVisibility(View.INVISIBLE);
                catTransportMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catTransportMinus:
                // ------
                catTransportPlus.setVisibility(View.VISIBLE);
                catTransportMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catShoppingPlus:
                // ++++++
                catShoppingPlus.setVisibility(View.INVISIBLE);
                catShoppingMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catShoppingMinus:
                // ------
                catShoppingPlus.setVisibility(View.VISIBLE);
                catShoppingMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catPersonalCarePlus:
                // ++++++
                catPersonalCarePlus.setVisibility(View.INVISIBLE);
                catPersonalCareMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catPersonalCareMinus:
                // ------
                catPersonalCarePlus.setVisibility(View.VISIBLE);
                catPersonalCareMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catBillsPlus:
                // ++++++
                catBillsPlus.setVisibility(View.INVISIBLE);
                catBillsMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catBillsMinus:
                // ------
                catBillsPlus.setVisibility(View.VISIBLE);
                catBillsMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catFoodPlus:
                // ++++++
                catFoodPlus.setVisibility(View.INVISIBLE);
                catFoodMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catFoodMinus:
                // ------
                catFoodPlus.setVisibility(View.VISIBLE);
                catFoodMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.addBudgetEntertainment:
                initPopUpAddBudget("Entertainment");
                break;
            case R.id.addBudgetEducation:
                initPopUpAddBudget("Education");
                break;
            case R.id.addBudgetHealth:
                initPopUpAddBudget("Health");
                break;
            case R.id.addBudgetTransport:
                initPopUpAddBudget("Transport");
                break;
            case R.id.addBudgetShopping:
                initPopUpAddBudget("Shopping");
                break;
            case R.id.addBudgetPersonalCare:
                initPopUpAddBudget("Personal Care");
                break;
            case R.id.addBudgetBills:
                initPopUpAddBudget("Bills");
                break;
            case R.id.addBudgetFood:
                initPopUpAddBudget("Food");
                break;
        }
    }

    private void initPopUpAddBudget(String cat) // pop up untuk add budget
    {
        Log.d(TAG, "initPopUpAddBudget");

        AlertDialog.Builder mBuilderAddBudget = new AlertDialog.Builder(this);

        View mViewAddBudget = getLayoutInflater().inflate(R.layout.activity_add_budget, null);
        TextView CategoryLabel = mViewAddBudget.findViewById(R.id.CategoryLabel);
        CategoryLabel.setText(cat);
        final EditText etBudget = mViewAddBudget.findViewById(R.id.etBudget);
        Button DoneButton = mViewAddBudget.findViewById(R.id.DoneButton);

        mBuilderAddBudget.setView(mViewAddBudget);
        final AlertDialog dialogAddBudget = mBuilderAddBudget.create();

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etBudget.getText().toString().isEmpty())
                {
                    // NANTI KENA LETAK DALAM DATABASE
                    Toast.makeText(Category.this, "Success", Toast.LENGTH_SHORT).show();
                    dialogAddBudget.cancel(); //untuk tutup pop up
                }
                else
                {
                    Toast.makeText(Category.this, "Must fill the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogAddBudget.show();
    }
}