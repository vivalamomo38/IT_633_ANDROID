package com.example.acmedepartmentstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.acmedepartmentstore.data.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    private List<Item> itemList;


    /** Step1: MyView holder Class**/

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName,numOfItems;
        public ImageView thumbnail;
        public final View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };





        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //Initializing the views:
            itemName= itemView.findViewById(R.id.title);
            numOfItems = itemView.findViewById(R.id.count);
            thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }
    /** Step 2: Create Variables & Constructors**/
    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;

    }

    /** Step 3: Implementing Methods of Adapter**/
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_card_view_layout,parent, false);

        return new MyViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item  = itemList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.numOfItems.setText(String.valueOf(item.getNumOfItems()));

        // Displaying the image using Glide Library:
        Glide.with(context).load(item.getThumbnail()).into(holder.thumbnail);



    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
