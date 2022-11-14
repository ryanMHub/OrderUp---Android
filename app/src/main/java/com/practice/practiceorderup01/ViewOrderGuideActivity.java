package com.practice.practiceorderup01;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewOrderGuideActivity extends AppCompatActivity {

    //declare DBHelper object
    private DBHelper dbConnection;

    //declare the XML tags
    private RecyclerView orderListRec;
    private Button mainMenuBtn;

    //stores the current table that is being used in the order list
    private String currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_guide);


        //set the table selected by the user from MainActivity
        currentTable = getIntent().getStringExtra("tableName");

        //create connection to embedded database, And initialize the value for the current Table that is being used
        dbConnection = new DBHelper(this);

        //initialize XML Tags
        orderListRec = findViewById(R.id.orderListRecView);
        mainMenuBtn = findViewById(R.id.returnMainBtn);

        //create an arraylist with the items in the table selected
        ArrayList<Item> items = dbConnection.getItemList(currentTable);

        //Declare and initialize adapters for recycler view
        OrderViewRecAdapter adapter = new OrderViewRecAdapter();
        adapter.setItemsList(items);

        //set order_list_item.XML adapter to the recyclerView
        orderListRec.setAdapter(adapter);
        orderListRec.setLayoutManager(new GridLayoutManager(this,1));

        //On button click for main button
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}