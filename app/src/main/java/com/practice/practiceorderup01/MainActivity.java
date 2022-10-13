package com.practice.practiceorderup01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerOrderGuides;
    private Button selectTableButton;
    private TextView addOrderGuide;

    private DBHelper dbConnection;
    private String tableName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a connection to the database with this activity
        dbConnection = new DBHelper(this);

        //links to the spinner on activity_main that displays the tablenames in the orderGuide database
        spinnerOrderGuides = findViewById(R.id.spinnerOrderGuides);
        //links to the button that will pass the tablename to the OrderListActivity
        selectTableButton = findViewById(R.id.selectTableButton);
        //links the textview that will pass user to the add orderGuide activity
        addOrderGuide = findViewById(R.id.txtAddOrderGuide);


        ArrayList<String> orderGuides = dbConnection.getTableNames();

        ArrayAdapter<String> tableNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderGuides);
        tableNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrderGuides.setAdapter(tableNameAdapter);

        //action taken when you select a value in the spinner
        spinnerOrderGuides.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this, "The selected table is " + orderGuides.get(position), Toast.LENGTH_SHORT).show();
                tableName = orderGuides.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        selectTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                Toast.makeText(MainActivity.this, "Table Name " + tableName, Toast.LENGTH_SHORT).show();
                intent.putExtra("tableName", tableName);
                startActivity(intent);
            }
        });

        addOrderGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrderGuideActivity.class);
                startActivity(intent);
            }
        });


    }
}
