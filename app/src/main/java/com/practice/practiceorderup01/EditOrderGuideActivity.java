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

public class EditOrderGuideActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
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

    private CustomSpinner iconSpinner; //declares the spinner that will contain the selectable icons for order guides
    private IconAdapter iconAdapter; //will adapt the icons list to the spinner
    private List<Icons> iconsList; //stores the list of icons locally
    private int selectedIcon; //stores the id of the currently selected icon

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
        iconSpinner = findViewById(R.id.iconSpinner);

        //initialize database connection
        dbConnection = new DBHelper(this);

        //adapt the icons list to the spinner
        iconsList = IconList.getIconsList();
        iconAdapter = new IconAdapter(this, iconsList);
        iconSpinner.setAdapter(iconAdapter);
        iconSpinner.setDropDownVerticalOffset(110);

        //set spinner event listener
        iconSpinner.setSpinnerEventsListener(this);
        //set spinner position to the tables saved icon by position in array
        iconSpinner.setSelection(dbConnection.getIconIndex(tableName));
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
                    //drop the original table in the database, and it's associated icon from ICON_DIRECTOR
                    dbConnection.dropTable(tableName);
                    dbConnection.dropRowInIconDirector(tableName);

                    //create the new table
                    dbConnection.createOrderGuideTable(edtOrderGuideName.getText().toString());

                    //loop through the editTableList adding each item to the new table
                    for(Item item : editItemList){
                        dbConnection.onAdd(item, edtOrderGuideName.getText().toString());
                    }

                    //add entry in ICON_DIRECTOR for this table
                    dbConnection.addIconDirectorEntry(edtOrderGuideName.getText().toString(), selectedIcon);

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

    //override the back button
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(EditOrderGuideActivity.this, MainActivity.class);
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