package com.example.bidkart;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<CardItem> arrayList;


    public MyAdapter(ArrayList<CardItem> arrayList) {
        this.arrayList = arrayList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title,price,time;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_image);
            title=itemView.findViewById(R.id.item_title);
            price= itemView.findViewById(R.id.item_price);
            time=itemView.findViewById(R.id.time_remaining);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent i=new Intent(v.getContext(),Watchlist.class);
                  i.putExtra("position",getAdapterPosition()+"");
                    v.getContext().startActivity(i);

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(), v);

             popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem item) {
                    Intent i=new Intent(itemView.getContext(),Watchlist.class);
                    i.putExtra("position",getAdapterPosition()+"");
                   itemView.getContext().startActivity(i);
                     return true;
                 }
             });


                popup.inflate(R.menu.card_menu);


                popup.show();
                return true;
                }
            });
        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,parent,false);
        MyViewHolder mvh=new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CardItem currentItem=arrayList.get(position);
     //   holder.imageView.setImageURI(Uri.parse(currentItem.getImageUri()));
        Picasso.get().load(currentItem.getImageUri()).into(holder.imageView);
        holder.price.setText(currentItem.getPrice());
        holder.title.setText(currentItem.getTitle());
        holder.time.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
