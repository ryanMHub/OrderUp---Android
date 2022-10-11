package com.practice.practiceorderup01;

//this class will be used to create an Item object for the recycler view
public class Item {

    private String itemName;
    private Double par;
    private Double onHand;

    public Item(String itemName, Double par){
        this.itemName = itemName;
        this.par = par;
        this.onHand = 0.0;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPar() {
        return par;
    }

    public void setPar(Double par) {
        this.par = par;
    }

    public Double getOnHand() {
        return onHand;
    }

    public void setOnHand(Double onHand) {
        this.onHand = onHand;
    }
}
