package com.cenkkaraboa.aktelrnlerkatalou.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cenkkaraboa.aktelrnlerkatalou.Database.FavoriteDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Stores;
import com.cenkkaraboa.aktelrnlerkatalou.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.Holder> {
    public List<Stores> itemList;
    public Context context;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public StoreAdapter(List<Stores> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public StoreAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoreAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(StoreAdapter.Holder holder, int position) {
        FavoriteDatabase db = new FavoriteDatabase(context);

        holder.ad.setText(itemList.get(position).getStoreName());
        Picasso.get()
                .load("https://aktuel.cenkkaraboa.com/public/images/stores/"+itemList.get(position).getPicturePatch())
                .into(holder.market);

        ArrayList<HashMap<String, String>> stores=db.products();
        Boolean value=false;
        for(int a=0;a<stores.size();a++){
            if(String.valueOf(itemList.get(position).getId()).equals(stores.get(a).get("store"))){
                value=true;
            }
        }
        if(value){
            holder.favorite.setTag("fav");
            holder.favorite.setImageResource(R.drawable.redheart);
        }else {
            holder.favorite.setTag("black");
            holder.favorite.setImageResource(R.drawable.heart);
        }
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageName= (String) holder.favorite.getTag();
                if(imageName.length()==5){
                    holder.favorite.setTag("fav");
                    holder.favorite.setImageResource(R.drawable.redheart);
                    db.productAdd(itemList.get(position).getStoreName(),String.valueOf(itemList.get(position).getId()));
                }else {
                    holder.favorite.setTag("black");
                    holder.favorite.setImageResource(R.drawable.heart);
                    db.productDelete(itemList.get(position).getId());

                }
            }

        });
        db.close();
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView ad, numara,time,date;
        ImageView market;
        ImageView favorite;
        public Holder(final View itemView) {
            super(itemView);
            ad = (TextView) itemView.findViewById(R.id.name);
            market =itemView.findViewById(R.id.market);
            favorite =itemView.findViewById(R.id.favorite);
         ;   itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}