package com.practice.practiceorderup01;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EditOrderGuideRecAdapter extends RecyclerView.Adapter<EditOrderGuideRecAdapter.ViewHolder>{

    //contains the item list pulled from database selected by user
    ArrayList<Item> editItemList;

    @NonNull
    @NotNull
    @Override
    //this method will inflate the edit_list_rec for each item in the list into the recycler
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_list_rec, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //link the values of the list by position in the list to each viewHolder created for each item
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.btnDeleteItem.setOnClickListener(view -> deleteItem(holder.getBindingAdapterPosition()));

        if(editItemList.get(position).getItemName().equals("Item Name") || editItemList.get(position).getItemName().trim().isEmpty()){
            holder.edtItemNameEdit.setText("");
            holder.edtItemParEdit.setText("");
            holder.edtItemNameEdit.setHint("Item Name"); //link item name by position in list
            holder.edtItemParEdit.setHint("0.0");
        } else{
            holder.edtItemNameEdit.setText(editItemList.get(position).getItemName()); //link item name by position in list
            holder.edtItemParEdit.setText(String.valueOf(editItemList.get(position).getPar()));
        }

        if(position == getItemCount()-1) holder.edtItemNameEdit.requestFocus();

    }

    @Override
    //return size of the editItemList
    public int getItemCount() {
        return editItemList.size();
    }

    //pass the reference address of the table that will be edited to the adapter then update the recycler data
    public void setItemList(ArrayList<Item> editItemList){
        this.editItemList = editItemList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        editItemList.remove(position);
        notifyItemRemoved(position);

       // notifyItemRangeChanged(0,editItemList.size()-1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //declares all tags in edit_list_rec.xml
        private final Button btnDeleteItem;
        private final EditText edtItemNameEdit;
        private final EditText edtItemParEdit;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //initialize tags in edit_list_rec.xml
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItemEdit);
            edtItemNameEdit = itemView.findViewById(R.id.edtItemNameEdit);
            edtItemParEdit = itemView.findViewById(R.id.edtItemParEdit);


            //monitor for changes to the item name and update editItemList when it occurs
            edtItemNameEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(edtItemNameEdit.getText().toString().trim().isEmpty()){
                        editItemList.get(getBindingAdapterPosition()).setItemName("Item Name");
                    } else{
                        editItemList.get(getBindingAdapterPosition()).setItemName(edtItemNameEdit.getText().toString());
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            //monitor for changes to the item par level and update editItemLIst when it occurs
            edtItemParEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    try{
                        Double newPar = Double.parseDouble(edtItemParEdit.getText().toString());
                        editItemList.get(getBindingAdapterPosition()).setPar(newPar);
                    } catch (NumberFormatException ex) {
                        if(!(edtItemParEdit.getText().toString().equals("."))){
                            edtItemParEdit.getText().clear();
                        }

                        editItemList.get(getBindingAdapterPosition()).setPar(0.0);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
