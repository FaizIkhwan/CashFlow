package com.fish.cashflow;

import android.content.Intent;
import android.database.Cursor;
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
    TextView categoryTV, catEntertainmentTV, catEducationTV, catHealthTV, catTransportTV, catShoppingTV, catPersonalCareTV, catBillsTV, catFoodTV;
    ImageButton catEntertainmentPlus, catEntertainmentMinus, catEducationPlus, catEducationMinus, catHealthPlus, catHealthMinus, catTransportPlus,
            catTransportMinus, catShoppingPlus, catShoppingMinus, catPersonalCarePlus, catPersonalCareMinus, catBillsPlus, catBillsMinus, catFoodPlus, catFoodMinus,
                addBudgetEntertainment, addBudgetEducation, addBudgetHealth, addBudgetTransport, addBudgetShopping, addBudgetPersonalCare, addBudgetBills, addBudgetFood
                    , backButton;

    //database
    DatabaseHelper myDB;

    //usable variable
    private String[] cat = {"ENTERTAINMENT", "EDUCATION", "HEALTH", "TRANSPORT", "SHOPPING", "PERSONAL CARE", "BILLS", "FOOD"};
    private Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d(TAG, "onCreate: Category");

        initComponent(); //Initialize components
        initOnClickListener(); //Initialize onClickListener

        toCloseOrOpen(); // check database to determine which button should appear
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

    private void toCloseOrOpen() // invisible semua plus button kalau catogory ada 8
    {
        //Database
        myDB = new DatabaseHelper(this);

        for(int i=0; i<cat.length; i++)
        {
            res = myDB.getStateForCategory(cat[i].toUpperCase());
            if(res != null && res.moveToFirst()) // tak kosong
            {
                do
                {
                    switch ( res.getString(0) )
                    {
                        case "ENTERTAINMENT":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catEntertainmentMinus.setVisibility(View.VISIBLE);
                                catEntertainmentPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catEntertainmentMinus.setVisibility(View.INVISIBLE);
                                catEntertainmentPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "EDUCATION":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catEducationMinus.setVisibility(View.VISIBLE);
                                catEducationPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catEducationMinus.setVisibility(View.INVISIBLE);
                                catEducationPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "HEALTH":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catHealthMinus.setVisibility(View.VISIBLE);
                                catHealthPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catHealthMinus.setVisibility(View.INVISIBLE);
                                catHealthPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "TRANSPORT":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catTransportMinus.setVisibility(View.VISIBLE);
                                catTransportPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catTransportMinus.setVisibility(View.INVISIBLE);
                                catTransportPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "SHOPPING":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catShoppingMinus.setVisibility(View.VISIBLE);
                                catShoppingPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catShoppingMinus.setVisibility(View.INVISIBLE);
                                catShoppingPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "PERSONAL CARE":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catPersonalCareMinus.setVisibility(View.VISIBLE);
                                catPersonalCarePlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catPersonalCareMinus.setVisibility(View.INVISIBLE);
                                catPersonalCarePlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "BILLS":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catBillsMinus.setVisibility(View.VISIBLE);
                                catBillsPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catBillsMinus.setVisibility(View.INVISIBLE);
                                catBillsPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "FOOD":
                            if( res.getString(1).equals("TRUE") )
                            {
                                catFoodMinus.setVisibility(View.VISIBLE);
                                catFoodPlus.setVisibility(View.INVISIBLE);
                            }
                            else if( res.getString(1).equals("FALSE") )
                            {
                                catFoodMinus.setVisibility(View.INVISIBLE);
                                catFoodPlus.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                }
                while(res.moveToNext());
            }
        }
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
                res = myDB.getBudgetOnlyForCategory("ENTERTAINMENT");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("1", "ENTERTAINMENT", res.getString(0), "TRUE");
                catEntertainmentPlus.setVisibility(View.INVISIBLE);
                catEntertainmentMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catEntertainmentMinus:
                res = myDB.getBudgetOnlyForCategory("ENTERTAINMENT");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("1", "ENTERTAINMENT", res.getString(0), "FALSE");
                catEntertainmentPlus.setVisibility(View.VISIBLE);
                catEntertainmentMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catEducationPlus:
                res = myDB.getBudgetOnlyForCategory("EDUCATION");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("2", "EDUCATION", res.getString(0), "TRUE");
                catEducationPlus.setVisibility(View.INVISIBLE);
                catEducationMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catEducationMinus:
                res = myDB.getBudgetOnlyForCategory("EDUCATION");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("2", "EDUCATION", res.getString(0), "FALSE");
                catEducationPlus.setVisibility(View.VISIBLE);
                catEducationMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catHealthPlus:
                res = myDB.getBudgetOnlyForCategory("HEALTH");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("3", "HEALTH", res.getString(0), "TRUE");
                catHealthPlus.setVisibility(View.INVISIBLE);
                catHealthMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catHealthMinus:
                res = myDB.getBudgetOnlyForCategory("HEALTH");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("3", "HEALTH", res.getString(0), "FALSE");
                catHealthPlus.setVisibility(View.VISIBLE);
                catHealthMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catTransportPlus:
                res = myDB.getBudgetOnlyForCategory("TRANSPORT");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("4", "TRANSPORT", res.getString(0), "TRUE");
                catTransportPlus.setVisibility(View.INVISIBLE);
                catTransportMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catTransportMinus:
                res = myDB.getBudgetOnlyForCategory("TRANSPORT");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("4", "TRANSPORT", res.getString(0), "FALSE");
                catTransportPlus.setVisibility(View.VISIBLE);
                catTransportMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catShoppingPlus:
                res = myDB.getBudgetOnlyForCategory("SHOPPING");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("5", "SHOPPING", res.getString(0), "TRUE");
                catShoppingPlus.setVisibility(View.INVISIBLE);
                catShoppingMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catShoppingMinus:
                res = myDB.getBudgetOnlyForCategory("SHOPPING");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("5", "SHOPPING", res.getString(0), "FALSE");
                catShoppingPlus.setVisibility(View.VISIBLE);
                catShoppingMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catPersonalCarePlus:
                res = myDB.getBudgetOnlyForCategory("PERSONAL CARE");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("6", "PERSONAL CARE", res.getString(0), "TRUE");
                catPersonalCarePlus.setVisibility(View.INVISIBLE);
                catPersonalCareMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catPersonalCareMinus:
                res = myDB.getBudgetOnlyForCategory("PERSONAL CARE");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("6", "PERSONAL CARE", res.getString(0), "FALSE");
                catPersonalCarePlus.setVisibility(View.VISIBLE);
                catPersonalCareMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catBillsPlus:
                res = myDB.getBudgetOnlyForCategory("BILLS");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("7", "BILLS", res.getString(0), "TRUE");
                catBillsPlus.setVisibility(View.INVISIBLE);
                catBillsMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catBillsMinus:
                res = myDB.getBudgetOnlyForCategory("BILLS");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("7", "BILLS", res.getString(0), "FALSE");
                catBillsPlus.setVisibility(View.VISIBLE);
                catBillsMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.catFoodPlus:
                res = myDB.getBudgetOnlyForCategory("FOOD");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("8", "FOOD", res.getString(0), "TRUE");
                catFoodPlus.setVisibility(View.INVISIBLE);
                catFoodMinus.setVisibility(View.VISIBLE);
                break;
            case R.id.catFoodMinus:
                res = myDB.getBudgetOnlyForCategory("FOOD");
                if(res != null && res.moveToFirst())
                    myDB.updateDataCategory("8", "FOOD", res.getString(0), "FALSE");
                catFoodPlus.setVisibility(View.VISIBLE);
                catFoodMinus.setVisibility(View.INVISIBLE);
                break;
            case R.id.addBudgetEntertainment:
                initPopUpAddBudget("ENTERTAINMENT");
                break;
            case R.id.addBudgetEducation:
                initPopUpAddBudget("EDUCATION");
                break;
            case R.id.addBudgetHealth:
                initPopUpAddBudget("HEALTH");
                break;
            case R.id.addBudgetTransport:
                initPopUpAddBudget("TRANSPORT");
                break;
            case R.id.addBudgetShopping:
                initPopUpAddBudget("SHOPPING");
                break;
            case R.id.addBudgetPersonalCare:
                initPopUpAddBudget("PERSONAL CARE");
                break;
            case R.id.addBudgetBills:
                initPopUpAddBudget("BILLS");
                break;
            case R.id.addBudgetFood:
                initPopUpAddBudget("FOOD");
                break;
        }
    }

    private void initPopUpAddBudget(final String localCat) // pop up untuk add budget
    {
        Log.d(TAG, "initPopUpAddBudget");

        AlertDialog.Builder mBuilderAddBudget = new AlertDialog.Builder(this);

        View mViewAddBudget = getLayoutInflater().inflate(R.layout.activity_add_budget, null);
        TextView CategoryLabel = mViewAddBudget.findViewById(R.id.CategoryLabel);
        CategoryLabel.setText(localCat);
        final EditText etBudget = mViewAddBudget.findViewById(R.id.etBudget);
        Button DoneButton = mViewAddBudget.findViewById(R.id.DoneButton);

        mBuilderAddBudget.setView(mViewAddBudget);
        final AlertDialog dialogAddBudget = mBuilderAddBudget.create();

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etBudget.getText().toString().isEmpty()) // kalau dia isi semua part
                {
                    switch (localCat)
                    {
                        case "ENTERTAINMENT":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("1", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "EDUCATION":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("2", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "HEALTH":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("3", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "TRANSPORT":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("4", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "SHOPPING":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("5", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "PERSONAL CARE":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("6", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "BILLS":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("7", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                        case "FOOD":
                            res = myDB.getStateForCategory(localCat);
                            if(res != null && res.moveToFirst())
                                myDB.updateDataCategory("8", localCat, etBudget.getText().toString(), res.getString(1));
                            break;
                    }
                    Toast.makeText(Category.this, "Add success", Toast.LENGTH_SHORT).show();
                    dialogAddBudget.cancel(); //untuk tutup pop up
                }
                else
                {
                    Toast.makeText(Category.this, "Must fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogAddBudget.show();
    }
}