package com.practice.practiceorderup01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
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

        //Used for createing a test table when starting
        /*
        if(!(tableExists(ORDER_TABLE))){
            buildTestTable();
        }*/
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

        tableName = "\"" + tableName +"\"";
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
        String createTableStatement = "CREATE TABLE IF NOT EXISTS \"" + ORDER_TABLE + "\" (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " TEXT, " + ITEM_PAR + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement);

        this.onAdd(new Item("Bud Light", 10.0), ORDER_TABLE);
        this.onAdd(new Item("Budweiser", 20.0), ORDER_TABLE);
        this.onAdd(new Item("Corona", 15.0), ORDER_TABLE);
        this.onAdd(new Item("Tequila", 0.5), ORDER_TABLE);
    }

    //creates a table in the database based on the name provided by the user
    public boolean createOrderGuideTable(String tableName){

        tableName = "\"" + tableName +"\"";
        //if the table already exists return false
        if(tableExists(tableName)){
            return false;
        } else{
            String createTableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " TEXT, " + ITEM_PAR + " REAL)";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(createTableStatement);
            return true;
        }
    }

    //Returns a list of items from the provided tablename
    public ArrayList<Item> getItemList(String tableName){

        tableName = "\"" + tableName +"\""; //add quotations around the table names to protect from white space errors

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

    //Returns an array list with all the table names in the orderGuide database
    public ArrayList<String> getTableNames(){
        ArrayList<String> returnList = new ArrayList<>();
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND (name NOT LIKE 'sqlite_sequence' AND name NOT LIKE 'android_metadata')";

        SQLiteDatabase db = this.getReadableDatabase();

        if(db == null || !db.isOpen()){
            return returnList;
        }

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                String str = cursor.getString(0);
                returnList.add(str);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    //checks if a table exists in the database
    public boolean tableExists(String tableName){

        SQLiteDatabase db = this.getReadableDatabase();

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
        db.close();
        return (count > 0);
    }

    //this method will drop the table that is passed to it
    public void dropTable(String tableName){

        tableName = "\"" + tableName +"\"";

        SQLiteDatabase db = this.getWritableDatabase();

        String statement = "DROP TABLE IF EXISTS " + tableName;

        db.execSQL(statement);

        db.close();

    }

    //commit changes to database
    public void commitDataChanges(){

        SQLiteDatabase db = this.getWritableDatabase();

        String statement = "COMMIT";

        db.execSQL(statement);
        db.close();
    }

    //Todo: check if item exists method
}
