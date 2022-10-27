package com.practice.practiceorderup01;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    //declare DBHelper object
    private DBHelper dbConnection;

    //declare the XML tags
    private RecyclerView orderListRec;
    private Button processListButton;

    //used as a flag for current recycler adapter that is displayed
    private boolean isResults = false;

    //stores the current table that is being used in the order list
    private String currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_main);

        //set the table selected by the user from MainActivity
        currentTable = getIntent().getStringExtra("tableName");

        //create connection to embedded database, And initialize the value for the current Table that is being used
        dbConnection = new DBHelper(this);

        //initialize XML Tags
        orderListRec = findViewById(R.id.orderListRec);
        processListButton = findViewById(R.id.submitOrderButton);

        ArrayList<Item> items = dbConnection.getItemList(currentTable);

        //Declare and initialize adapters for recycler view
        OrderListRecAdapter adapter = new OrderListRecAdapter();
        adapter.setItemsList(items);
        OrderResultsRecAdapter resultsAdapter = new OrderResultsRecAdapter();
        resultsAdapter.setItemsList(items);

        //set order_list_item.XML adapter to the recyclerView
        orderListRec.setAdapter(adapter);
        orderListRec.setLayoutManager(new GridLayoutManager(this,1));

        //On button click for main button
        processListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //the if isResult is used as a flag wether the order list adapter
                //or order results adapter
                if(isResults){
                    Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    showResults(resultsAdapter);
                }
            }
        });
    }

    //order results switch method
    public void showResults(OrderResultsRecAdapter resultsAdapter){
        orderListRec.setAdapter(resultsAdapter);
        processListButton.setText(R.string.mainMenuButton);
        isResults = true;
    }
}