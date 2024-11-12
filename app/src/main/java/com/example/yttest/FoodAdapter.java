// FoodAdapter.java

package com.example.yttest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodClass> foodList;
    private FoodItemClickListener  listener;

    public interface FoodItemClickListener  {
        void onItemClick(int position);
    }


    public FoodAdapter(List<FoodClass> foodList, FoodItemClickListener  listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, @SuppressLint("RecyclerView") int position) {


            FoodClass foodItem = foodList.get(position);
            holder.itemNameTextView.setText(foodItem.getName());
            holder.itemDescriptionTextView.setText(foodItem.getDate());
            holder.itemCalories.setText(String.valueOf(foodItem.getQuantity()) + " k/cal");
//            holder.itemDishImage.setImageBitmap(foodItem.getImage());




        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDeleteConfirmationDialog(position, view.getContext());
//                if (listener != null) {
//                    listener.onItemClick(position);
//                    notifyItemRemoved(position);
//                    notifyDataSetChanged();
//                }
            }
        });

    }

    private void showDeleteConfirmationDialog(final int position, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this food log?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onItemClick(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No," do nothing
                    }
                });
        // Create and show the AlertDialog
        builder.create().show();
    }



    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemDescriptionTextView;

        TextView itemCalories;

        ImageView deleteButton;

//        ImageView itemDishImage;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.foodName);
            itemDescriptionTextView = itemView.findViewById(R.id.foodDescription);
            itemCalories = itemView.findViewById(R.id.caloriesTextRecycler);
            deleteButton = itemView.findViewById(R.id.deleteButton);
//            itemDishImage = itemView.findViewById(R.id.dishImage);
        }
    }


}
