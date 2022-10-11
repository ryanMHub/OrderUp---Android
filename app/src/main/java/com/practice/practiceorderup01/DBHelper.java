package com.practice.practiceorderup01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //Database constants
    public static final String ORDER_TABLE = "ORDER_TABLE";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_PAR = "ITEM_PAR";

    public DBHelper(@Nullable Context context) {
        super(context, "orderGuide.db", null, 1);
        //when the database helper class is constructed if the test db does not exist in
        //the database create it
        if(!(tableExists(this.getReadableDatabase(),ORDER_TABLE))){
            buildTestTable();
        }
    }

    //
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //do nothing right now
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //do nothing right now
    }

    //this method will create an item in the table of choice
    //Todo:maybe create error checking a bypass system with a long of rejections
    public boolean onAdd(Item item, String tableName){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ITEM_NAME, item.getItemName());
        cv.put(ITEM_PAR, item.getPar());

        long insert = db.insert(tableName, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //builds a test table
    public void buildTestTable(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + ORDER_TABLE + " (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " TEXT, " + ITEM_PAR + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement);

        this.onAdd(new Item("Bud Light", 10.0), ORDER_TABLE);
        this.onAdd(new Item("Budweiser", 20.0), ORDER_TABLE);
        this.onAdd(new Item("Corona", 15.0), ORDER_TABLE);
        this.onAdd(new Item("Tequila", 0.5), ORDER_TABLE);
    }

    //get all items from a table
    public ArrayList<Item> getItemList(String tableName){

        ArrayList<Item> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + tableName;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                returnList.add(new Item(cursor.getString(1), cursor.getDouble(2) ));
            }while (cursor.moveToNext());
        }else{
            //will return empty list. Todo:What should I do here
        }

        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    //checks if a table exists in the database
    public boolean tableExists(SQLiteDatabase db, String tableName){
        if(tableName == null || db == null || !db.isOpen()){
            return false;
        }

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?",
                new String[] {"table", tableName}
        );

        if(!cursor.moveToFirst()){
            cursor.close();
            return false;
        }

        int count = cursor.getInt(0);
        cursor.close();
        return (count > 0);
    }

    //Todo: check if item exists method
}
