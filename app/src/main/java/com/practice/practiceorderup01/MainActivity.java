package com.practice.practiceorderup01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener{

    //Todo: Reorganize all files into a different structure
    private enum Action { //enum is used to switch which activity to execute when dealing with existing order guides
        View(ViewOrderGuideActivity.class),
        Process(OrderListActivity.class),
        Delete(DeleteOrderGuideActivity.class),
        Edit(EditOrderGuideActivity.class);

        final Class className;

        Action(final Class className){
            this.className = className;
        }
    }

    //declare spinners
    private CustomSpinner spinnerOrderGuides;
    private IconAdapter iconAdapter; //will adapt the icons list to the spinner
    private ArrayList<MenuItemList> orderGuides; //array to store all the table names  and icon indexes in table ICON_DIRECTOR*

    //Declare the database connection
    private DBHelper dbConnection;

    //Declare and initialize the values that will store the selection in the spinners
    private String tableName = "";

    //declare buttons from activity_main.xml
    private Button createOrderBtn;
    private Button viewOrderBtn;
    private Button processOrderBtn;
    private Button editOrderBtn;
    private Button deleteOrderBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a connection to the database with this activity
        dbConnection = new DBHelper(this);

        //links to the spinner on activity_main that displays the table names in the orderGuide database
        spinnerOrderGuides = findViewById(R.id.spinnerOrderGuides);

        //Arraylist contains the table names to be used in the orderGuide spinner in activity_main.xml
        //and adapt the arraylist to the spinner in the display
        orderGuides = dbConnection.getAllIconTableList();
        iconAdapter = new IconAdapter(this, IconList.convertIndexToReferenceImage(orderGuides));
        spinnerOrderGuides.setAdapter(iconAdapter);
        spinnerOrderGuides.setDropDownVerticalOffset(110);

        //set spinner event listener
        spinnerOrderGuides.setSpinnerEventsListener(this);

        //action taken when you select a value in the spinner
        spinnerOrderGuides.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(MainActivity.this, "The selected table is " + orderGuides.get(position), Toast.LENGTH_SHORT).show();
                tableName = orderGuides.get(position).getTableName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //initialize all buttons from the main_activity.xml
        createOrderBtn = findViewById(R.id.createOrderButton);
        viewOrderBtn = findViewById(R.id.viewOrderButton);
        processOrderBtn = findViewById(R.id.processOrderButton);
        editOrderBtn = findViewById(R.id.editOrderButton);
        deleteOrderBtn = findViewById(R.id.deleteOrderButton);

        //Create Order Guide button will execute AddOrderGuideActivity
        createOrderBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddOrderGuideActivity.class);
            startActivity(intent);
            finish();
        });

        //View Order Guide executes ViewOrderGuideActivity
        viewOrderBtn.setOnClickListener(view -> executeActivity(Action.View));

        //Process Order Button will execute OrderListActivity
        processOrderBtn.setOnClickListener(view -> executeActivity(Action.Process));

        //Edit Order Button executes EditOrderGuideActivity
        editOrderBtn.setOnClickListener(view -> executeActivity(Action.Edit));

        //Delete Order Guide Button executes DeleteOrderGuideActivity
        deleteOrderBtn.setOnClickListener(view -> executeActivity(Action.Delete));


    }

    //this function is used to execute an activity based of the enum passed
    private void executeActivity(Action action){

        if(dbConnection.tableExists(tableName)){
            Intent intentDeleteOrderGuide= new Intent(MainActivity.this, action.className);
            intentDeleteOrderGuide.putExtra("tableName", tableName);
            startActivity(intentDeleteOrderGuide);
            finish();
        } else{
            //Todo: Snackbar
            Toast.makeText(this, "Order Guide Does not Exist", Toast.LENGTH_SHORT).show();
        }
    }

    //action to be taken when spinner is open
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spinnerOrderGuides.setBackground(getDrawable(R.drawable.bg_spinner_icon_up));
    }

    //action to be taken when spinner closed
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spinnerOrderGuides.setBackground(getDrawable(R.drawable.bg_spinner_icon));
    }

}
