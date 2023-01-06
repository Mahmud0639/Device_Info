package com.manuni.deviceinfo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuni.deviceinfo.databinding.ModelLayoutBinding;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ModelLayoutBinding binding;
    ItemClickListener itemClickListener;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        binding = ModelLayoutBinding.bind(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }
}
