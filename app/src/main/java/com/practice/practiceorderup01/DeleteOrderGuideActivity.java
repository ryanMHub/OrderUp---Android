package com.practice.practiceorderup01;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DeleteOrderGuideActivity extends AppCompatActivity {

    //declare the buttons
    private Button btnDelete;
    private Button btnCancel;

    //declare the textview that will display the table name
    private TextView txtTableName;

    //store the name of the selected table from intent
    private String currentTable;

    //declare Database helper
    private DBHelper dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_order_guide);

        //retrieves the name of the selected table or order guide form the intent
        currentTable = getIntent().getStringExtra("tableName");

        //initialize XML tags fro activity_delete_order_guide
        txtTableName = findViewById(R.id.txtTableName);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        //initialize DBHelper
        dbConnection = new DBHelper(this);

        //link the tag to the name of the table that is stored form caller
        txtTableName.setText(currentTable);

        //access database and delete table when clicked
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbConnection.dropTable(currentTable);
                Toast.makeText(DeleteOrderGuideActivity.this, currentTable + " Has Been Delete", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //cancel the delete and return to main activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //overide the back button
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DeleteOrderGuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}