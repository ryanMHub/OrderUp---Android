package com.practice.practiceorderup01;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderListRecAdapter extends RecyclerView.Adapter<OrderListRecAdapter.ViewHolder> {

    //stores the reference to the main array of the order guide table from the database
    ArrayList<Item> items = new ArrayList<>();


    @NonNull
    @NotNull
    @Override //creates a new order_list_item.xml for each item in the array
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(view); //return holder
    }

    //bind each item name to the text box in order_list_item.xml
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getItemName());
    }

    //returns the size of the order list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //pass the reference of the main array to the adapter
    public void setItemsList(ArrayList<Item> items){
        this.items = items;
        notifyItemRangeChanged(0,items.size()); //refresh the recycler with the new list of items
    }

    //inner class is used to make a copy of the XML tags from the order_list_item.XML page used to make multiple copies.
    //Based on the size of items arraylist a ViewHolder is created for each item
    public class ViewHolder extends RecyclerView.ViewHolder{

        //declare tags in order_list_item.xml
        private final TextView itemName;
        private final EditText edtOnHand;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //declare xml tags
            itemName = itemView.findViewById(R.id.txtItemName);
            edtOnHand = itemView.findViewById(R.id.edtOnhand);

            //when a value changes in the edittext tag, assign the value to the on hand
            //for each item changed
            edtOnHand.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    try{
                        Double newOnHand = Double.parseDouble(edtOnHand.getText().toString().trim());
                        items.get(getBindingAdapterPosition()).setOnHand(newOnHand);
                    } catch(NumberFormatException ex){
                        if(!(edtOnHand.getText().toString().equals("."))){
                            edtOnHand.getText().clear();
                        }
                        items.get(getBindingAdapterPosition()).setOnHand(0d);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

}
