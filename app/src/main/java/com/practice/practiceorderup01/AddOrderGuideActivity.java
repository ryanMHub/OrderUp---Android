package com.practice.practiceorderup01;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddOrderGuideActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {

    //Todo: increase the appearance of the toasts
    //Todo: send a toast when creation is complete
    //declares the connection to the OrderGuide name editText tag
    private EditText edtTableName;

    //declares the connection to the recycler view of the activity_add_order_guide
    private RecyclerView newListRec;

    private Button btnAddItem; //declares connection to the tag that will add a new item to the list
    private Button btnCreateList; //declares connection to the tag that will create the new orderGuide in the database
    private Button btnCancelCreate; //declares connection to the tag that will be used to cancel create an order guide activity

    private CustomSpinner iconSpinner; //declares the spinner that will contain the selectable icons for order guides
    private IconAdapter iconAdapter; //will adapt the icons list to the spinner
    private List<Icons> iconsList; //stores the list of icons locally
    private int selectedIcon; //stores the id of the currently selected icon

    private DBHelper dbConnection; //used to access the DB to create the order guide

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_guide);

        //initialize tags in activity_add_order_guide
        edtTableName = findViewById(R.id.edtOrderGuideNameCreate);
        newListRec = findViewById(R.id.createListRec);
        btnAddItem = findViewById(R.id.btnAddItemCreate);
        btnCreateList = findViewById(R.id.btnSaveOrderGuideCreate);
        btnCancelCreate = findViewById(R.id.btnCancelCreate);
        iconSpinner = findViewById(R.id.iconSpinner);

        //initialize the database helper
        dbConnection = new DBHelper(this);

        //adapt the icons list to the spinner
        iconsList = IconList.getIconsList();
        iconAdapter = new IconAdapter(this, iconsList);
        iconSpinner.setAdapter(iconAdapter);
        iconSpinner.setDropDownVerticalOffset(110);

        //set spinner event listener
        iconSpinner.setSpinnerEventsListener(this);
        //store the initial icon to selectedIcon
        selectedIcon = iconSpinner.getSelectedItemPosition();

        //on change listener for spinner when user selects icon
        iconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIcon = iconSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //arraylist will store a list of all the items in the new order guide
        ArrayList<Item> newItemList = new ArrayList<>();

        //create the adapter for the recycler and set the newItemList arraylist as the arraylist connected to the recycler
        EditOrderGuideRecAdapter adapter = new EditOrderGuideRecAdapter();
        adapter.setItemList(newItemList);

        //connect the adapter to the recycler and set the layout manager to the recycler
        newListRec.setAdapter(adapter);
        newListRec.setLayoutManager(new GridLayoutManager(this,1));

        //Todo:Possibly remove
        //listen for the virtual keyboard to open to adjust the recycler view
        newListRec.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                newListRec.scrollToPosition(newItemList.size()-1);
            }
        });

        //Todo: Add items that are not used do not include in table currently databases has error when left empty

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemList.add(new Item("Item Name",0.0));
                adapter.notifyItemInserted(newItemList.size()-1);
                adapter.notifyItemRangeChanged(0,newItemList.size());
                adapter.notifyDataSetChanged();
            }
        });


        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if there is a value in the edittext box for the table if the space is empty
                if(edtTableName.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddOrderGuideActivity.this, "Order Guide Name Required", Toast.LENGTH_SHORT).show();
                } else if(dbConnection.tableExists(edtTableName.getText().toString())) {
                    Toast.makeText(AddOrderGuideActivity.this, "Order Guide Name Unavailable", Toast.LENGTH_SHORT).show();
                } else{
                    //create a table based on the name provided by the user
                    dbConnection.createOrderGuideTable(edtTableName.getText().toString()); //Todo: add a toast to tell user if there was an error

                    //loop through the newItemList and insert each item into new table
                    for(Item item : newItemList){
                        dbConnection.onAdd(item, edtTableName.getText().toString());
                    }

                    dbConnection.addIconDirectorEntry(edtTableName.getText().toString(), selectedIcon);

                    Intent intent = new Intent(AddOrderGuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

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

    //overide the back button
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddOrderGuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //action to be taken when spinner is open
    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        iconSpinner.setBackground(getDrawable(R.drawable.bg_spinner_icon_up));
    }

    //action to be taken when spinner closed
    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        iconSpinner.setBackground(getDrawable(R.drawable.bg_spinner_icon));
    }
}