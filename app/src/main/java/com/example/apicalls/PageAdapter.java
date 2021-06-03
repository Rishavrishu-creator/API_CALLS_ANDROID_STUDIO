package com.example.apicalls;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> price;
    ArrayList<String> image;
    public PageAdapter(Context context, ArrayList<String> name, ArrayList<String> price, ArrayList<String> image) {
       this.context=context;
       this.price=price;
       this.name=name;
       this.image=image;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.text.setText(name.get(position));
        holder.image.setImageResource(R.drawable.beer);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Add2.class);
                intent.putExtra("category_id",price.get(position));
                intent.putExtra("name",name.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return price.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView text;
        RelativeLayout relativeLayout;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            image=itemView.findViewById(R.id.image);
            relativeLayout=itemView.findViewById(R.id.relative);
        }
    }
}
