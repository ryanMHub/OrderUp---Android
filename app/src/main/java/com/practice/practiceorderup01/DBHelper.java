package com.practice.practiceorderup01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //Database constants
    public static final String ORDER_TABLE = "ORDER_TABLE";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_PAR = "ITEM_PAR";

    public static final String ICON_DIRECTOR = "ICON_DIRECTOR";
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String ICON_ID = "ICON_ID";

    public DBHelper(@Nullable Context context) {
        super(context, "orderGuide.db", null, 1);

        //if the icon director table does not exist create it
        if(!(tableExists(ICON_DIRECTOR))){
            createIconDirector();
        }

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

    //Adds the icon director table to the database
    public boolean createIconDirector(){

        //if the table already exists return false
        if(tableExists(ICON_DIRECTOR)){
            return false;
        } else{
            String createTableStatement = "CREATE TABLE IF NOT EXISTS " + ICON_DIRECTOR + " (" + TABLE_NAME + " TEXT PRIMARY KEY, " + ICON_ID + " INTEGER)";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(createTableStatement);
            return true;
        }
    }

    //add entry to icon director
    public boolean addIconDirectorEntry(String tableKey, int iconID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_NAME, tableKey);
        cv.put(ICON_ID, iconID);

        long insert = db.insert(ICON_DIRECTOR, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //creates a table in the database based on the name provided by the user
    public boolean createOrderGuideTable(String tableName){
        //todo: modify so that if the execSQL fails false is returned
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

    //return the index of the icon associated with the table name passed
    public int getIconIndex(String tableKey){
        tableKey = "\"" + tableKey +"\""; //wrap tableKey in quotations in case there is a space in the name

        String queryString = "SELECT " + ICON_ID + " FROM " + ICON_DIRECTOR + " WHERE " + TABLE_NAME + " = " + tableKey;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //retrieve data from db and store in cursor
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        //if a value was retrieved return. Else return 0
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        } else{
            return 0;
        }

    }

    //returns all rows from ICON_DIRECTOR in an arraylist to its caller
    public ArrayList<MenuItemList> getAllIconTableList(){

        //this is the list that will store the values return from the query
        ArrayList<MenuItemList> returnList = new ArrayList<MenuItemList>();

        String query = "SELECT * FROM " + ICON_DIRECTOR;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(sqLiteDatabase == null || !(sqLiteDatabase.isOpen())){
            return returnList;
        }

        if(cursor.moveToFirst()){
            do{
                returnList.add(new MenuItemList(cursor.getString(0),cursor.getInt(1)));
            }while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return returnList;
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
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND (name NOT LIKE 'sqlite_sequence' AND name NOT LIKE 'android_metadata' AND name NOT LIKE 'ICON_DIRECTOR')";

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

    //this function will drop a row based on provided key
    public void dropRowInIconDirector(String tableKey){

        tableKey = "\"" + tableKey + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        String statement = "DELETE FROM " + ICON_DIRECTOR + " WHERE " + TABLE_NAME + " = " + tableKey;

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
