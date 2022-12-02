package com.practice.practiceorderup01;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    //declares the main layout of the current activity
    private ConstraintLayout mainLayout;
    //declares header image view
    private ImageView headerImage;
    //declare DBHelper object
    private DBHelper dbConnection;

    //declare the XML tags
    private TextView qtyLabel;
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
        qtyLabel = findViewById(R.id.qtyLabel);
        mainLayout = findViewById(R.id.orderListMainLayout);
        headerImage = findViewById(R.id.processHeaderImage);

        //create an arraylist with the items in the table selected
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
        processListButton.setOnClickListener(view -> {
            //the if isResult is used as a flag whether the order list adapter
            //or order results adapter
            if(isResults){
                Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                showResults(resultsAdapter);
            }
        });

        //listen for a change in the size of the main layout when the keyboard opens
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            //this will be used to determine if the size of the layout has changed
            Rect currentVisible = new Rect();
            mainLayout.getWindowVisibleDisplayFrame(currentVisible);

            //create a Constraint set for the main constraint layout allows you to alter the percentage of tags in layout
            ConstraintSet set = new ConstraintSet();
            set.clone(mainLayout);

            //determine if the keyboard has been opened or closed
            int heightDiff = mainLayout.getRootView().getHeight() - currentVisible.height();
            if(heightDiff > .25*mainLayout.getRootView().getHeight()){
                set.constrainPercentHeight(R.id.scrollForRecProcess, .69f);
                set.applyTo(mainLayout);
                headerImage.setVisibility(View.GONE);
            } else{
                set.constrainPercentHeight(R.id.scrollForRecProcess, .55f);
                set.applyTo(mainLayout);
                headerImage.setVisibility(View.VISIBLE);
            }
        });
    }

    //order results switch method
    public void showResults(OrderResultsRecAdapter resultsAdapter){
        orderListRec.setAdapter(resultsAdapter);
        processListButton.setText(R.string.mainMenuButton);
        qtyLabel.setText(R.string.orderQty);
        isResults = true;
    }

    //override the back button
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}