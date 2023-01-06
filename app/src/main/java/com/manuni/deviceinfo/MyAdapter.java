package com.manuni.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {
    Context context;
    ArrayList<Model> list,filterList;
    CustomFilter filter;

    public MyAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model data = list.get(position);
        holder.binding.modelTxt.setText(data.getName());
        holder.binding.modelImage.setImageResource(data.getImage());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                if (data.getName().equals("General")){
                    Intent intent = new Intent(context,GeneralActivity.class);
                    context.startActivity(intent);
                }
                 if (data.getName().equals("Device Id")){
                    Intent deviceIdintent = new Intent(context,DeviceIdActivity.class);
                    context.startActivity(deviceIdintent);
                }
                 if (data.getName().equals("Display")){
                   context.startActivity(new Intent(context,DisplayActivity.class));
                }
                 if (data.getName().equals("Battery")){
                    Toast.makeText(context, "Battery is clicked.", Toast.LENGTH_SHORT).show();
                }
                 if (data.getName().equals("System Apps")){
                    Toast.makeText(context, "System Apps is clicked.", Toast.LENGTH_SHORT).show();
                }
                 if (data.getName().equals("User Apps")){
                    Toast.makeText(context, "User Apps is clicked.", Toast.LENGTH_SHORT).show();
                }
                 if (data.getName().equals("Memory")){
                    Toast.makeText(context, "Memory is clicked.", Toast.LENGTH_SHORT).show();
                }
                 if (data.getName().equals("CPU")){
                    Toast.makeText(context, "CPU is clicked.", Toast.LENGTH_SHORT).show();
                }
                 if (data.getName().equals("Sensor")){
                    Toast.makeText(context, "Sensor is clicked.", Toast.LENGTH_SHORT).show();
                }
                 if (data.getName().equals("SIM")){
                    Toast.makeText(context, "SIM is clicked.", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter(filterList,this);
        }
        return filter;
    }
}
