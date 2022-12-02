package com.practice.practiceorderup01;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderViewRecAdapter extends RecyclerView.Adapter<OrderViewRecAdapter.ViewHolder> {

    ////stores the reference to the main array of the order guide table from the database
    ArrayList<Item> items = new ArrayList<>();

    //creates a new order_list_results.xml for each item in the array
    @NonNull
    @NotNull
    @Override
    public OrderViewRecAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_results, parent, false);
        return new ViewHolder(view); //return holder
    }

    @SuppressLint("DefaultLocale")
    @Override //bind the item name to the text box and the order results to the text box
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtItemName.setText(items.get(position).getItemName());
        holder.txtResults.setText(String.format("%.1f",items.get(position).getPar()));
    }

    //return the size of the array
    @Override
    public int getItemCount() {
        return items.size();
    }

    //adds new list to the adapter
    public void setItemsList(ArrayList<Item> items){
        this.items = items;
        notifyItemRangeChanged(0,items.size()); //recycle the view windows
    }

    //inner class is used to make a copy of the XML tags from the order_list_results.XML page used to make multiple copies.
    //Based on the size of items arraylist
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView txtItemName;
        private final TextView txtResults;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemNameResult);
            txtResults = itemView.findViewById(R.id.txtOrderTotals);
        }


    }
}
