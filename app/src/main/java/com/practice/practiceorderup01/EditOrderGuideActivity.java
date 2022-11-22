package com.practice.practiceorderup01;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditOrderGuideActivity extends AppCompatActivity {
    //Todo: Add hint instead of text to text boxes and par boxes
    //Todo: Create exception for when there is no value in par edt box
    //Todo: add a toast when the edit is complete, custom fix the delete and the add
    //declare tags from activity_edit_order_guide
    private EditText edtOrderGuideName;
    private Button btnAddItem;
    private Button btnSaveChanges;
    private Button btnCancel;
    private RecyclerView orderGuideRec;

    //stores the table name passed from main and changed by user
    private String tableName;
    //Database connection declaration;
    private DBHelper dbConnection;

    //array list will store the data in the selected table and then the changes made to it
    private ArrayList<Item> editItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_guide);

        //retrieve the table name from main activity
        tableName = getIntent().getStringExtra("tableName");

        //initialize tags from activity_edit_order_guide
        edtOrderGuideName = findViewById(R.id.edtOrderGuideNameEdit);
        btnAddItem = findViewById(R.id.btnAddItemEdit);
        btnSaveChanges = findViewById(R.id.btnSaveOrderGuideEdit);
        btnCancel = findViewById(R.id.btnCancelEdit);
        orderGuideRec = findViewById(R.id.editListRec);

        //initialize database connection
        dbConnection = new DBHelper(this);

        //set the text to the order guide name selected
        edtOrderGuideName.setText(tableName);


        //obtain list from the database based on table determined from user
        editItemList = dbConnection.getItemList(tableName);

        //connect editItemList and adapter to recycler view
        EditOrderGuideRecAdapter adapter = new EditOrderGuideRecAdapter();
        adapter.setItemList(editItemList);
        orderGuideRec.setAdapter(adapter);
        orderGuideRec.setLayoutManager(new GridLayoutManager(this,1));

        //add item to the order guide
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItemList.add(new Item("Item Name", 0.0));
                adapter.notifyItemInserted(editItemList.size()-1);
                adapter.notifyItemRangeChanged(0,editItemList.size());
                adapter.notifyDataSetChanged();
            }
        });

        //when the save changes button is clicked the original table will be dropped. And then the new table will be created based on the information
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtOrderGuideName.getText().toString().trim().isEmpty()){
                    Toast.makeText(EditOrderGuideActivity.this, "Order Guide Name Required", Toast.LENGTH_SHORT).show();
                } else{
                    //drop the original table in the database
                    dbConnection.dropTable(tableName);

                    //create the new table
                    dbConnection.createOrderGuideTable(edtOrderGuideName.getText().toString());

                    //loop through the editTableList adding each item to the new table
                    for(Item item : editItemList){
                        dbConnection.onAdd(item, edtOrderGuideName.getText().toString());
                    }

                    //return to main activity
                    Intent intent = new Intent(EditOrderGuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //action taken when cancel button is pressed return to main activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //overide the back button
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(EditOrderGuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}