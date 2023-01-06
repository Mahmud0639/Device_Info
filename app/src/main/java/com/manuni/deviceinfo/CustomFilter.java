package com.manuni.deviceinfo;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    MyAdapter adapter;
    ArrayList<Model> filterList;

    public CustomFilter( ArrayList<Model> filterList,MyAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        if (constraint !=null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();

            ArrayList<Model> filterModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                if (filterList.get(i).getName().toUpperCase().contains(constraint)){
                    filterModels.add(filterList.get(i));
                }
            }
            filterResults.count = filterModels.size();
            filterResults.values = filterModels;
        }else {
            filterResults.count = filterList.size();
            filterResults.values = filterList;
        }


        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.list = (ArrayList<Model>) results.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}
