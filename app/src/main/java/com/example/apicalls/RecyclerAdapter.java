package com.example.apicalls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> name;
    ArrayList<String> price;
    ArrayList<String> image;
    public RecyclerAdapter(Context context, ArrayList<String> name, ArrayList<String> price, ArrayList<String> image) {
        this.context=context;
        this.name=name;
        this.image=image;
        this.price=price;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(name.get(position));
        holder.price.setText("Rs "+price.get(position));
        Picasso.get().load(image.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount()
    {
        return name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,price;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
}
