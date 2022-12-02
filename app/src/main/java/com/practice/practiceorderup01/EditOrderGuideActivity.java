package com.practice.practiceorderup01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditOrderGuideActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
    //declare tag for main layout
    private ConstraintLayout mainLayout;
    //declares header image view
    private ImageView headerImage;
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
        mainLayout = findViewById(R.id.editMainLayout);
        headerImage = findViewById(R.id.editHeaderImage);

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
        btnAddItem.setOnClickListener(view -> {
            editItemList.add(new Item("Item Name", 0.0));
            adapter.notifyItemInserted(editItemList.size()-1);
            adapter.notifyItemRangeChanged(0,editItemList.size());
            adapter.notifyDataSetChanged();
        });

        //when the save changes button is clicked the original table will be dropped. And then the new table will be created based on the information
        btnSaveChanges.setOnClickListener(view -> {

            if(edtOrderGuideName.getText().toString().trim().isEmpty()){
                //Toast.makeText(EditOrderGuideActivity.this, "Order Guide Name Required", Toast.LENGTH_SHORT).show();
                CustomSnackBar.ShowSnackBar(EditOrderGuideActivity.this, mainLayout, "Order Guide Name Required", R.drawable.wrongred);
            } else{
                //drop the original table in the database, and it's associated icon from ICON_DIRECTOR
                dbConnection.dropTable(tableName);
                dbConnection.dropRowInIconDirector(tableName);

                //create the new table
                dbConnection.createOrderGuideTable(edtOrderGuideName.getText().toString().trim());
                //Todo: check if failure to add item
                //loop through the editTableList adding each item to the new table
                for(Item item : editItemList){
                    dbConnection.onAdd(item, edtOrderGuideName.getText().toString().trim());
                }

                //add entry in ICON_DIRECTOR for this table
                dbConnection.addIconDirectorEntry(edtOrderGuideName.getText().toString().trim(), selectedIcon);

                //Todo: Verify that this is true
                CustomSnackBar.ShowSnackBar(EditOrderGuideActivity.this, mainLayout, "Order Guide " + edtOrderGuideName.getText().toString().trim(), R.drawable.rightblue);

                //return to main activity
                Intent intent = new Intent(EditOrderGuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //action taken when cancel button is pressed return to main activity
        btnCancel.setOnClickListener(view -> {
            Intent intent = new Intent(EditOrderGuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
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
                set.constrainPercentHeight(R.id.layoutRecEdit, .33f);
                set.applyTo(mainLayout);
                headerImage.setVisibility(View.GONE);
            } else{
                set.constrainPercentHeight(R.id.layoutRecEdit, .44f);
                set.applyTo(mainLayout);
                headerImage.setVisibility(View.VISIBLE);
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
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        iconSpinner.setBackground(getDrawable(R.drawable.bg_spinner_icon_up));
    }

    //action to be taken when spinner closed
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        iconSpinner.setBackground(getDrawable(R.drawable.bg_spinner_icon));
    }
}