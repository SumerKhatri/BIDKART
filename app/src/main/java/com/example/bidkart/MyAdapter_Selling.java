package com.example.bidkart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter_Selling extends RecyclerView.Adapter<MyAdapter_Selling.MyViewHolder>{

    private ArrayList<CardItem_Selling> arrayList;

        public  MyAdapter_Selling(ArrayList<CardItem_Selling> arrayList){
            this.arrayList=arrayList;
        }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_Selling);
            title = itemView.findViewById(R.id.title_selling);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.selling_cardview,parent,false);
        MyViewHolder mvh=new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardItem_Selling currentItem=arrayList.get(position);
        //   holder.imageView.setImageURI(Uri.parse(currentItem.getImageUri()));
        Picasso.get().load(currentItem.getImageUri()).into(holder.imageView);
        holder.title.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
