package com.practice.practiceorderup01;

import java.util.ArrayList;
import java.util.List;

//this class will be used to compile the list of icons in the spinner
public class IconList {

    //creates and returns the hard coded icon list
    public static List<Icons> getIconsList(){
        List<Icons> iconsList = new ArrayList<>();

        //add the icon for bar order
        Icons barIcon = new Icons();
        barIcon.setName("Bar");
        barIcon.setImage(R.drawable.bar_small);
        iconsList.add(barIcon);

        //add the icon for beer order
        Icons beerIcon = new Icons();
        beerIcon.setName("Beer");
        beerIcon.setImage(R.drawable.beer_small);
        iconsList.add(beerIcon);

        //add the liquor icon
        Icons liquorIcon = new Icons();
        liquorIcon.setName("Liquor");
        liquorIcon.setImage(R.drawable.liquer_small);
        iconsList.add(liquorIcon);

        //add the wine order icon
        Icons wineIcon = new Icons();
        wineIcon.setName("Wine");
        wineIcon.setImage(R.drawable.wine_small);
        iconsList.add(wineIcon);

        //add paper icon
        Icons paperIcon = new Icons();
        paperIcon.setName("Paper");
        paperIcon.setImage(R.drawable.paper_small);
        iconsList.add(paperIcon);

        //add bread icon
        Icons breadIcon = new Icons();
        breadIcon.setName("Bread");
        breadIcon.setImage(R.drawable.bread_small);
        iconsList.add(breadIcon);

        //add seafood icon
        Icons seafoodIcon = new Icons();
        seafoodIcon.setName("Seafood");
        seafoodIcon.setImage(R.drawable.seafood_small);
        iconsList.add(seafoodIcon);

        //add meat icon
        Icons meatIcon = new Icons();
        meatIcon.setName("Meat");
        meatIcon.setImage(R.drawable.meat_small);
        iconsList.add(meatIcon);

        //add produce icon
        Icons produceIcon = new Icons();
        produceIcon.setName("Produce");
        produceIcon.setImage(R.drawable.produce_small);
        iconsList.add(produceIcon);

        //add US foods icon
        Icons usFoodIcon = new Icons();
        usFoodIcon.setName("US Foods");
        usFoodIcon.setImage(R.drawable.usfoods_small);
        iconsList.add(usFoodIcon);

        //add Soda icon
        Icons sodaIcon = new Icons();
        sodaIcon.setName("NA Beverages");
        sodaIcon.setImage(R.drawable.soda_small);
        iconsList.add(sodaIcon);

        //add misc icon
        Icons miscIcon = new Icons();
        miscIcon.setName("Misc");
        miscIcon.setImage(R.drawable.misc_small);
        iconsList.add(miscIcon);

        //return the iconList to caller
        return iconsList;
    }
}
