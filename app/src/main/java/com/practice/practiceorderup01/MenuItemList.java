package com.practice.practiceorderup01;

import java.io.Serializable;

//this classes primary purpose is to store each tableName and its associated
// icon from the database in a list that will be displayed on the Main Activity
public class MenuItemList implements Serializable {

    private int iconID; //stores the index of the icon for each table
    private String tableName; //stores each table name in the database

    //constructor will build each item that will be in the main menu spinner
    public MenuItemList(String tableName, int iconID){
        this.tableName = tableName;
        this.iconID = iconID;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
