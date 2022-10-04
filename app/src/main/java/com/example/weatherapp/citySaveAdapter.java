package com.example.weatherapp;

import android.app.LauncherActivity;
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
        holder.degreesTV.setText(listItem.getDegreesTV());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView savedCityTV;
        public TextView degreesTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            savedCityTV = (TextView) itemView.findViewById(R.id.savedCityTV);
            degreesTV = (TextView) itemView.findViewById(R.id.degreesTV);
        }
    }
}
