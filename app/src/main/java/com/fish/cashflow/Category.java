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

    //Log
    private static String TAG = "Category";

    //Interface
    TextView categoryTV, catEntertainmentTV, catEducationTV, catHealthTV, catTransportTV, catShoppingTV, catPersonalCareTV, catBillsTV, catFoodTV;
    ImageButton catEntertainmentPlusMinus,  catEducationPlusMinus,  catHealthPlusMinus,  catTransportPlusMinus, catShoppingPlusMinus,  catPersonalCarePlusMinus,  catBillsPlusMinus,  catFoodPlusMinus,
                addBudgetEntertainment, addBudgetEducation, addBudgetHealth, addBudgetTransport, addBudgetShopping, addBudgetPersonalCare, addBudgetBills, addBudgetFood
                    , backButton;

    //Database
    DatabaseHelper myDB;

    //Variable to use
    private String[] cat = {"ENTERTAINMENT", "EDUCATION", "HEALTH", "TRANSPORT", "SHOPPING", "PERSONAL CARE", "BILLS", "FOOD"};
    private Cursor res;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d(TAG, "onCreate: Category");

        initComponent(); //Initialize components.
        initOnClickListener(); //Initialize onClickListener.

        toCloseOrOpen(); // Check database to determine which button should appear.
    }

    /**
     * Define the UI.
     */
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

        catEntertainmentPlusMinus = findViewById(R.id.catEntertainmentPlusMinus);
        catEducationPlusMinus = findViewById(R.id.catEducationPlusMinus);
        catHealthPlusMinus = findViewById(R.id.catHealthPlusMinus);
        catTransportPlusMinus = findViewById(R.id.catTransportPlusMinus);
        catShoppingPlusMinus = findViewById(R.id.catShoppingPlusMinus);
        catPersonalCarePlusMinus = findViewById(R.id.catPersonalCarePlusMinus);
        catBillsPlusMinus = findViewById(R.id.catBillsPlusMinus);
        catFoodPlusMinus = findViewById(R.id.catFoodPlusMinus);

        addBudgetEntertainment = findViewById(R.id.addBudgetEntertainment);
        addBudgetEducation = findViewById(R.id.addBudgetEducation);
        addBudgetHealth = findViewById(R.id.addBudgetHealth);
        addBudgetTransport = findViewById(R.id.addBudgetTransport);
        addBudgetShopping = findViewById(R.id.addBudgetShopping);
        addBudgetPersonalCare = findViewById(R.id.addBudgetPersonalCare);
        addBudgetBills = findViewById(R.id.addBudgetBills);
        addBudgetFood = findViewById(R.id.addBudgetFood);
    }

    /**
     * Implementing OnClickListener for each button.
     */
    private void initOnClickListener()
    {
        Log.d(TAG, "initOnClickListener");
        backButton.setOnClickListener(this);

        catEntertainmentPlusMinus.setOnClickListener(this);
        catEducationPlusMinus.setOnClickListener(this);
        catHealthPlusMinus.setOnClickListener(this);
        catTransportPlusMinus.setOnClickListener(this);
        catShoppingPlusMinus.setOnClickListener(this);
        catPersonalCarePlusMinus.setOnClickListener(this);
        catBillsPlusMinus.setOnClickListener(this);
        catFoodPlusMinus.setOnClickListener(this);

        addBudgetEntertainment.setOnClickListener(this);
        addBudgetEducation.setOnClickListener(this);
        addBudgetHealth.setOnClickListener(this);
        addBudgetTransport.setOnClickListener(this);
        addBudgetShopping.setOnClickListener(this);
        addBudgetPersonalCare.setOnClickListener(this);
        addBudgetBills.setOnClickListener(this);
        addBudgetFood.setOnClickListener(this);
    }

    /**
     * Process to check if the category STATE is TRUE or FALSE.
     * If it is TRUE, button will appear.
     * Else, it will not.
     */
    private void toCloseOrOpen()
    {
        //Database
        myDB = new DatabaseHelper(this);

        for(int i=0; i<cat.length; i++)
        {
            res = myDB.getStateForCategory(cat[i].toUpperCase());
            if(res != null && res.moveToFirst()) // If the query result is not empty.
            {
                do
                {
                    switch ( res.getString(0) )
                    {
                        case "ENTERTAINMENT":
                            if( res.getString(1).equals("TRUE") )
                                catEntertainmentPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catEntertainmentPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;

                        case "EDUCATION":
                            if( res.getString(1).equals("TRUE") )
                                catEducationPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catEducationPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;
                        case "HEALTH":

                            if( res.getString(1).equals("TRUE") )
                                catHealthPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catHealthPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;

                        case "TRANSPORT":
                            if( res.getString(1).equals("TRUE") )
                                catTransportPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catTransportPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;

                        case "SHOPPING":
                            if( res.getString(1).equals("TRUE") )
                                catShoppingPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catShoppingPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;

                        case "PERSONAL CARE":
                            if( res.getString(1).equals("TRUE") )
                                catPersonalCarePlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catPersonalCarePlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;

                        case "BILLS":
                            if( res.getString(1).equals("TRUE") )
                                catBillsPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catBillsPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;

                        case "FOOD":
                            if( res.getString(1).equals("TRUE") )
                                catFoodPlusMinus.setImageResource(R.drawable.cancel);
                            else if( res.getString(1).equals("FALSE") )
                                catFoodPlusMinus.setImageResource(R.drawable.addplusbutton);
                            break;
                    }
                }
                while(res.moveToNext());
            }
        }
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
                startActivity(new Intent(this, BudgetPieChart.class));
                finish();
                break;
            case R.id.catEntertainmentPlusMinus:
                res = myDB.getStateForCategory("ENTERTAINMENT");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("1", "ENTERTAINMENT", res.getString(2), state);
                break;
            case R.id.catEducationPlusMinus:
                res = myDB.getStateForCategory("EDUCATION");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("2", "EDUCATION", res.getString(2), state);
                break;
            case R.id.catHealthPlusMinus:
                res = myDB.getStateForCategory("HEALTH");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("3", "HEALTH", res.getString(2), state);
                break;
            case R.id.catTransportPlusMinus:
                res = myDB.getStateForCategory("TRANSPORT");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("4", "TRANSPORT", res.getString(2), state);
                break;
            case R.id.catShoppingPlusMinus:
                res = myDB.getStateForCategory("SHOPPING");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("5", "SHOPPING", res.getString(2), state);
                break;
            case R.id.catPersonalCarePlusMinus:
                res = myDB.getStateForCategory("PERSONAL CARE");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("6", "PERSONAL CARE", res.getString(2), state);
                break;
            case R.id.catBillsPlusMinus:
                res = myDB.getStateForCategory("BILLS");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("7", "BILLS", res.getString(2), state);
                break;
            case R.id.catFoodPlusMinus:
                res = myDB.getStateForCategory("FOOD");
                if(res != null && res.moveToFirst())
                {
                    if(res.getString(1).equalsIgnoreCase("TRUE"))
                        state = "FALSE";
                    else
                        state = "TRUE";
                }
                myDB.updateDataCategory("8", "FOOD", res.getString(2), state);
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

        toCloseOrOpen();

    }

    /**
     * Creating pop up when the user click on category setting button.
     * To add or change budget.
     * @param localCat - Category name.
     */
    private void initPopUpAddBudget(final String localCat)
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
                if (!etBudget.getText().toString().isEmpty()) // If the user fill all the parts.
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
                    dialogAddBudget.cancel(); // To close the pop up.
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