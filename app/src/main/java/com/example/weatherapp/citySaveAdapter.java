package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.ads.mediation.Adapter;

import java.util.List;


public class citySaveAdapter extends RecyclerView.Adapter<citySaveAdapter.ViewHolder> {


    private List<citySaveModel> listItems;
    private Context context;
    private final SelectListener listener;

    public citySaveAdapter(List<citySaveModel> listItems, Context context,SelectListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.listener = listener;
    }



    @NonNull
    @Override
    public citySaveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent,false);
        //return new ViewHolder(v).linkAdapter(this);
        return new citySaveAdapter.ViewHolder(v,listener);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        citySaveModel listItem = listItems.get(position);

        holder.savedCityTV.setText(listItem.getSavedCityTV());

//        holder.savedCityTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClicked(holder.getAbsoluteAdapterPosition());
//
//            }
//        });

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button deleteBtn;
        public TextView savedCityTV;
        public citySaveAdapter adapter;
        public ConstraintLayout itemHolder;



        public ViewHolder(@NonNull View itemView,SelectListener listener) {
            super(itemView);


            savedCityTV = (TextView) itemView.findViewById(R.id.savedCityTV);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
            itemHolder = itemView.findViewById(R.id.itemHolder);

//            itemView.findViewById(R.id.savedCityTV).setOnClickListener(view ->{
//
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            listener.onItemClicked(pos);
                        }
                    }
                }
            });


            itemView.findViewById(R.id.btnDelete).setOnClickListener(view ->{
                adapter.listItems.remove(getAbsoluteAdapterPosition());
                adapter.notifyItemRemoved(getAbsoluteAdapterPosition());

             });

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = getAbsoluteAdapterPosition();
//                    adapter.onItemLongClick(pos);
//                    return true;
//                }
//            });


        }

        public ViewHolder linkAdapter(citySaveAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public interface OnBtnClickListener{
            void onDeleteBtnClick(int position);
        }


    }




}
