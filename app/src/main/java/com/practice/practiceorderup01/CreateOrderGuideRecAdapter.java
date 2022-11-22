package com.practice.practiceorderup01;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateOrderGuideRecAdapter extends RecyclerView.Adapter<CreateOrderGuideRecAdapter.ViewHolder> {

    //This array will store the array of the new order guide items
    ArrayList<Item> newItemList;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ceate_list_rec, parent, false);
        ViewHolder holder = new ViewHolder(view); //Create the inner object with the view that was created above
        return holder; //return holder
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //Do I need to add something here
    }

    @Override
    public int getItemCount() {
        return newItemList.size();
    }

    //pass the reference of the main array to the adapter
    public void setItemsList(ArrayList<Item> newItemList){
        this.newItemList = newItemList;
        notifyDataSetChanged(); //refresh the recycler with the new list of items
    }

    //Links all the create_list_rec.xml
    public class ViewHolder extends RecyclerView.ViewHolder{

        //declare all tags in the create_list_rec XML
        private EditText itemName; //user will input item name
        private EditText parLevel; //user will input item par level

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //initialize all tags in the create_list_rec XML recycler
            itemName = itemView.findViewById(R.id.edtItemName);
            parLevel = itemView.findViewById(R.id.edtParLevel);

            //on change listener for itemName edit text
            itemName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    newItemList.get(getBindingAdapterPosition()).setItemName(itemName.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            //on change listener for parLevel edit text
            parLevel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if(parLevel.getText().toString().equals("")) {
                        newItemList.get(getBindingAdapterPosition()).setPar(0.0);
                    } else{
                        Double newPar = Double.parseDouble(parLevel.getText().toString());
                        newItemList.get(getBindingAdapterPosition()).setPar(newPar);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
