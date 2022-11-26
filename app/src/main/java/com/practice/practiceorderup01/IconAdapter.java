package com.practice.practiceorderup01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class IconAdapter  extends BaseAdapter {

    private Context context;
    private List<Icons> iconsList;

    //constructor will create adapter with provide context and icons list
    public IconAdapter(Context context, List<Icons> iconsList){
        this.context = context;
        this.iconsList = iconsList;
    }

    @Override
    public int getCount() {
        return iconsList != null ? iconsList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //create view for each item in the icons list
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //retrieve the icon_view.xml to inflate each item into the spinner
        View rootView = LayoutInflater.from(context).inflate(R.layout.icon_view, viewGroup, false);

        //link the tags in the xml file to the icon data in each element of the list
        TextView nameHolder = rootView.findViewById(R.id.nameHolder);
        ImageView iconHolder = rootView.findViewById(R.id.iconHolder);

        //Set the data for each item into the tag
        nameHolder.setText(iconsList.get(i).getName());
        iconHolder.setImageResource(iconsList.get(i).getImage());

        return rootView;
    }
}
