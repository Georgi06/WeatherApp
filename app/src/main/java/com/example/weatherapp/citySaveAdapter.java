package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.ads.mediation.Adapter;

import java.util.List;


public class citySaveAdapter extends RecyclerView.Adapter<citySaveAdapter.ViewHolder> {


    private List<citySaveModel> listItems;
    private Context context;

    public citySaveAdapter(List<citySaveModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        citySaveModel listItem = listItems.get(position);

        holder.savedCityTV.setText(listItem.getSavedCityTV());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView savedCityTV;
        private citySaveAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            savedCityTV = (TextView) itemView.findViewById(R.id.savedCityTV);

//            itemView.findViewById(R.id.btnDelete).setOnClickListener(view ->{
//                adapter.listItems.remove(getAbsoluteAdapterPosition());
//                adapter.notifyItemRemoved(getAbsoluteAdapterPosition());
//
//             });

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = getAbsoluteAdapterPosition();
//                    adapter.onItemLongClick(pos);
//                    return true;
//                }
//            });
        }


        public ViewHolder linkAdapter(citySaveAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }




}
