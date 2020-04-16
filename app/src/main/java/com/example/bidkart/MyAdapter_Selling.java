package com.example.bidkart;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter_Selling extends RecyclerView.Adapter<MyAdapter_Selling.MyViewHolder>{

    private static ArrayList<CardItem_Selling> arrayList;

        public  MyAdapter_Selling(ArrayList<CardItem_Selling> arrayList){
            this.arrayList=arrayList;
        }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_Selling);
            title = itemView.findViewById(R.id.title_selling);
            //Log.d("current Activity",itemView.getContext().getClass()+"");
            if(itemView.getContext().getClass().toString().equals("class com.example.bidkart.Watchlist")){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        PopupMenu popup = new PopupMenu(itemView.getContext(), v);

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent i=new Intent(itemView.getContext(),Watchlist.class);
                                i.putExtra("title",arrayList.get(getAdapterPosition()).getTitle());
                                itemView.getContext().startActivity(i);
                                return true;
                            }
                        });


                        popup.inflate(R.menu.watchlist_remove);


                        popup.show();
                        return true;
                    }
                });
            }
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
