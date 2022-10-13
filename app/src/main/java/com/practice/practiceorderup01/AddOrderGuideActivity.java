package com.practice.practiceorderup01;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddOrderGuideActivity extends AppCompatActivity {

    //declares the connection to the OrderGuide name editText tag
    private EditText edtTableName;

    //declares the connection to the recycler view of the activity_add_order_guide
    private RecyclerView newListRec;

    private Button btnAddItem; //declares connection to the tag that will add a new item to the list
    private Button btnCreateList; //declares connection to the tag that will create the new orderGuide in the database

    private DBHelper dbConnection; //used to access the DB to create the order guide

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_guide);

        //initialize tags in activity_add_order_guide
        edtTableName = findViewById(R.id.edtTableName);
        newListRec = findViewById(R.id.newListRec);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnCreateList = findViewById(R.id.btnCreateList);

        //initialize the database helper
        dbConnection = new DBHelper(this);

        //arraylist will store a list of all the items in the new order guide
        ArrayList<Item> newItemList = new ArrayList<>();

        //create the adapter for the recycler and set the newItemList arraylist as the arraylist connected to the recycler
        CreateOrderGuideRecAdapter adapter = new CreateOrderGuideRecAdapter();
        adapter.setItemsList(newItemList);

        //connect the adapter to the recycler and set the layout manager to the recycler
        newListRec.setAdapter(adapter);
        newListRec.setLayoutManager(new GridLayoutManager(this,1));

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemList.add(new Item("",0.0));
                adapter.notifyDataSetChanged();
            }
        });

        //Todo: create error checking with toasts if data is not entered properly
        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a table based on the name provided by the user
                dbConnection.createOrderGuideTable(edtTableName.getText().toString()); //Todo: add a toast to tell user if there was an error

                //loop through the newItemList and insert each item into new table
                for(Item item : newItemList){
                    dbConnection.onAdd(item, edtTableName.getText().toString());
                }
            }
        });

    }
}