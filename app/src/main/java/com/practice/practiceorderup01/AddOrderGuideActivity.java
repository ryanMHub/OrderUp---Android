package com.practice.practiceorderup01;

import android.content.Intent;
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
    //Todo: Add delete item to Create Order guide
    //Todo: When you click create the activity closes and does not open Main Activity ? Happened when I added a large amount of items or maybe a space in the name
    //declares the connection to the OrderGuide name editText tag
    private EditText edtTableName;

    //declares the connection to the recycler view of the activity_add_order_guide
    private RecyclerView newListRec;

    private Button btnAddItem; //declares connection to the tag that will add a new item to the list
    private Button btnCreateList; //declares connection to the tag that will create the new orderGuide in the database
    private Button btnCancelCreate; //declares connection to the tag that will be used to cancel create an order guide activity

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
        btnCancelCreate = findViewById(R.id.btnCancelCreate);

        //initialize the database helper
        dbConnection = new DBHelper(this);

        //Todo: move to global
        //arraylist will store a list of all the items in the new order guide
        ArrayList<Item> newItemList = new ArrayList<>();

        //create the adapter for the recycler and set the newItemList arraylist as the arraylist connected to the recycler
        CreateOrderGuideRecAdapter adapter = new CreateOrderGuideRecAdapter();
        adapter.setItemsList(newItemList);

        //connect the adapter to the recycler and set the layout manager to the recycler
        newListRec.setAdapter(adapter);
        newListRec.setLayoutManager(new GridLayoutManager(this,1));

        //Todo: Add items that are not used do not include in table currently databases has error when left empty
        //Todo: Duplicate items creates creation of database error
        //Todo: also when space added to name causes error
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemList.add(new Item("",0.0));
                adapter.notifyDataSetChanged();
            }
        });

        //Todo: create error checking with toasts if data is not entered properly
        //Todo: error checking if someone enters a nomnnumerical character currently crashes activity
        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a table based on the name provided by the user
                dbConnection.createOrderGuideTable(edtTableName.getText().toString()); //Todo: add a toast to tell user if there was an error

                //loop through the newItemList and insert each item into new table
                for(Item item : newItemList){
                    dbConnection.onAdd(item, edtTableName.getText().toString());
                }

                Intent intent = new Intent(AddOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //cancel the current activity and go back to main activity
        btnCancelCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}