package com.cenkkaraboa.aktelrnlerkatalou.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cenkkaraboa.aktelrnlerkatalou.Models.Product;
import com.cenkkaraboa.aktelrnlerkatalou.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    public List<Product> itemList;
    public Context context;
    public String add;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductAdapter(List<Product> itemList, Context context,String add) {
        this.itemList = itemList;
        this.context = context;
        this.add = add;
    }

    @Override
    public ProductAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(ProductAdapter.Holder holder, int position) {
        Picasso.get()
                .load("https://aktuel.cenkkaraboa.com/public/images/products/"+itemList.get(position).getImage())
                .into(holder.market);
        //     holder.date.setText(itemList.get(itemList.size()-(position+1)).getDate().toString());
        holder.ad.setText(itemList.get(position).getDate());

        if(add.equals("add")){
            holder.go.setText(itemList.get(position).getStore());
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView ad, numara,time,date,go,store;
        ImageView market;
        public Holder(final View itemView) {
            super(itemView);
            ad = (TextView) itemView.findViewById(R.id.name);
            go = (TextView) itemView.findViewById(R.id.go);
            market =itemView.findViewById(R.id.market);
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