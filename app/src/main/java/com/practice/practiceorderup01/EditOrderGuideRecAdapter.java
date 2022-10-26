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
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    //link the values of the list by position in the list to each viewholder created for each item
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.edtItemNameEdit.setText(editItemList.get(position).getItemName()); //link item name by position in list
        holder.edtItemParEdit.setText(String.valueOf(editItemList.get(position).getPar()));
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        //declares all tags in edit_list_rec.xml
        private Button btnDeleteItem;
        private EditText edtItemNameEdit;
        private EditText edtItemParEdit;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //initialize tags in edit_list_rec.xml
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItemEdit);
            edtItemNameEdit = itemView.findViewById(R.id.edtItemNameEdit);
            edtItemParEdit = itemView.findViewById(R.id.edtItemParEdit);

            //delete item from list when button clicked and update recycler
            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editItemList.remove(getBindingAdapterPosition());
                    notifyDataSetChanged();
                }
            });

            //monitor for changes to the item name and update editItemList when it occurs
            edtItemNameEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editItemList.get(getBindingAdapterPosition()).setItemName(edtItemNameEdit.getText().toString());
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
                    editItemList.get(getBindingAdapterPosition()).setPar(Double.parseDouble(edtItemParEdit.getText().toString()));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
