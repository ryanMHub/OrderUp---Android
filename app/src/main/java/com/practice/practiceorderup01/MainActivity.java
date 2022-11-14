package com.practice.practiceorderup01;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Todo:Enhance the spinner design
    //declare spinners
    private Spinner spinnerOrderGuides;

    //Todo: Clean up code when finished replacing
    //All the comments below have been replaced by buttons
    //private Spinner spinnerActionOption;
    //declare button
    //private Button selectActionButton;
    //declare textviews
    //private TextView addOrderGuide;

    //Declare the database connection
    private DBHelper dbConnection;

    //Declare and initialize the values that will store the selection in the spinners
    private String tableName = "";
    private String actionOption = "";

    //array to store all the table names in the database *Order Guides*
    private ArrayList<String> orderGuides;
    //Array adapter for the order guides array
    private ArrayAdapter<String> tableNameAdapter;

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

        //links to the spinner on activity_main that displays the tablenames in the orderGuide database
        spinnerOrderGuides = findViewById(R.id.spinnerOrderGuides);

        //Todo: clean up replaced by buttons final
        //links to the spinner on the activity_main that displays the action options to perform on the selected table
        //spinnerActionOption = findViewById(R.id.spinnerActionOption);
        //links to the button that will pass the tablename to the OrderListActivity
        //selectActionButton = findViewById(R.id.selectTableButton);
        //links the textview that will pass user to the add orderGuide activity
        //addOrderGuide = findViewById(R.id.txtAddOrderGuide);

        //Arraylist contains the table names to be used in the orderGuide spinner in activity_main.xml
        //and adapt the arraylist to the spinner in the display
        orderGuides = dbConnection.getTableNames();
        tableNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderGuides);
        tableNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrderGuides.setAdapter(tableNameAdapter);

        //action taken when you select a value in the spinner
        spinnerOrderGuides.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(MainActivity.this, "The selected table is " + orderGuides.get(position), Toast.LENGTH_SHORT).show();
                tableName = orderGuides.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Todo: Remove when done changing to buttons
        //Arraylist is create to store the action options that will be adapted to the actionOptions spinner on the activity_main.xml
        //Resources res = getResources();
        //ArrayList<String> actionOptions = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.actionOptions)));
        //create the adapter for the above list
        //ArrayAdapter<String> actionOptionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, actionOptions);
        //actionOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerActionOption.setAdapter(actionOptionAdapter);
        //action taken when the action option is changed
        /*spinnerActionOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(MainActivity.this, "The selected option is " + actionOptions.get(position), Toast.LENGTH_SHORT).show();
                actionOption = actionOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        //initialize all buttons from the main_activity.xml
        createOrderBtn = findViewById(R.id.createOrderButton);
        viewOrderBtn = findViewById(R.id.viewOrderButton);
        processOrderBtn = findViewById(R.id.processOrderButton);
        editOrderBtn = findViewById(R.id.editOrderButton);
        deleteOrderBtn = findViewById(R.id.deleteOrderButton);

        //Create Order Guide button will execute AddOrderGuideActivity
        createOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrderGuideActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Todo: add the view order guide activity
        //View Order Guide executes ViewOrderGuideActivity
        viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewOrderIntent = new Intent(MainActivity.this, ViewOrderGuideActivity.class);
                viewOrderIntent.putExtra("tableName", tableName);
                startActivity(viewOrderIntent);
                finish();
            }
        });

        //Todo: Do I need to add a fail safe if no table is selected ? Automatically have a table selected
        //Process Order Button will execute OrderListActivity
        processOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProcessOrder = new Intent(MainActivity.this, OrderListActivity.class);
                intentProcessOrder.putExtra("tableName", tableName);
                startActivity(intentProcessOrder);
                finish();
            }
        });

        //Edit Order Button executes EditOrderGuideActivity
        editOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEditOrderGuide= new Intent(MainActivity.this, EditOrderGuideActivity.class);
                intentEditOrderGuide.putExtra("tableName", tableName);
                startActivity(intentEditOrderGuide);
                finish();
            }
        });

        //Delete Order Guide Button executes DeleteOrderGuideActivity
        deleteOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDeleteOrderGuide= new Intent(MainActivity.this, DeleteOrderGuideActivity.class);
                intentDeleteOrderGuide.putExtra("tableName", tableName);
                startActivity(intentDeleteOrderGuide);
                finish();
            }
        });
    //Todo: remove when button replacement complete
   /*     selectActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check that the tablename selected is valid and that there is a table in the arraylist
                //otherwise return
                if(orderGuides.isEmpty() || !(dbConnection.tableExists(tableName))){
                    Toast.makeText(MainActivity.this, "NOt a Valid Table", Toast.LENGTH_SHORT).show();
                    return;
                }

                //matches the selected option in the spinner to the value in the array which will send use to selected activity
                switch(indexOf(actionOptions, actionOption)){
                    case 0: //this case will access the process order
                        Intent intentProcessOrder = new Intent(MainActivity.this, OrderListActivity.class);
                        intentProcessOrder.putExtra("tableName", tableName);
                        startActivity(intentProcessOrder);
                        finish();
                        break;

                    case 1: //this case will access the edit order guide activity
                        Intent intentEditOrderGuide= new Intent(MainActivity.this, EditOrderGuideActivity.class);
                        intentEditOrderGuide.putExtra("tableName", tableName);
                        startActivity(intentEditOrderGuide);
                        finish();
                        break;

                    case 2: //this case will access the delete order guide activity
                        Intent intentDeleteOrderGuide= new Intent(MainActivity.this, DeleteOrderGuideActivity.class);
                        intentDeleteOrderGuide.putExtra("tableName", tableName);
                        startActivity(intentDeleteOrderGuide);
                        finish();
                        break;

                    default:
                        Toast.makeText(MainActivity.this, "Invalid Action Option", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addOrderGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrderGuideActivity.class);
                startActivity(intent);
                finish();
            }
        });*/


    }

    //Todo:Fix the back button
    //Todo: Do I need this.
    @Override
    protected void onRestart(){
        super.onRestart();
        orderGuides = dbConnection.getTableNames();
        tableNameAdapter.notifyDataSetChanged();
        spinnerOrderGuides.setAdapter(tableNameAdapter);

    }

    //tODO: restart or resume which one should handle this action
    /*@Override
    protected void onResume(){
        super.onResume();
        orderGuides = dbConnection.getTableNames();
        tableNameAdapter.notifyDataSetChanged();
        spinnerOrderGuides.setAdapter(tableNameAdapter);
    }*/

    //returns the index of the value in the string array when compared to a value
    private int indexOf(ArrayList<String> values, String keyValue){
        for(int i = 0 ; i < values.size() ; i++){
            if(values.get(i).equals(keyValue)) return i;
        }

        return -1;
    }
}
