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
        ViewHolder holder = new ViewHolder(view); //Create the inner object with the view that was created above
        return holder; //return holder
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
        notifyDataSetChanged(); //refresh the recycler with the new list of items
    }

    //inner class is used to make a copy of the XML tags from the order_list_item.XML page used to make multiple copies.
    //Based on the size of items arraylist a ViewHolder is created for each item
    public class ViewHolder extends RecyclerView.ViewHolder{

        //declare tags in order_list_item.xml
        private TextView itemName;
        private EditText edtOnhand;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //declare xml tags
            itemName = itemView.findViewById(R.id.txtItemName);
            edtOnhand = itemView.findViewById(R.id.edtOnhand);

            //when a value changes in the edittext tag, assign the value to the onhand
            //for each item changed
            edtOnhand.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override //Todo: check that all the error checking is done with try catch
                //Todo: add a changeable max value
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { TODO: //Allow user to set max order size
                    if(!(edtOnhand.getText().toString().trim().length() == 0 || Double.parseDouble(edtOnhand.getText().toString()) > 100 || 0 > Double.parseDouble(edtOnhand.getText().toString()))){
                        items.get(getBindingAdapterPosition()).setOnHand(Double.parseDouble(String.valueOf(edtOnhand.getText())));
                    }else{
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
