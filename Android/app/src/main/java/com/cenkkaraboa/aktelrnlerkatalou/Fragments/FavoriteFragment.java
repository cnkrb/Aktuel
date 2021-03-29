package com.cenkkaraboa.aktelrnlerkatalou.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cenkkaraboa.aktelrnlerkatalou.Activitites.ProductActivity;
import com.cenkkaraboa.aktelrnlerkatalou.Adapters.StoreAdapter;
import com.cenkkaraboa.aktelrnlerkatalou.Database.FavoriteDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Stores;
import com.cenkkaraboa.aktelrnlerkatalou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.cenkkaraboa.aktelrnlerkatalou.Utils.Utils.interfaces;

public class FavoriteFragment extends Fragment  implements  StoreAdapter.OnItemClickListener{

    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    public List<Stores> itemList;
    public List<Stores> newItemList=new ArrayList<>();
    ArrayList<HashMap<String, String>> stores;
    FavoriteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView.setVisibility(GONE);
        stores=getProducts();
        if(stores.size()>0){
            getStores();
        }else {
            Toast.makeText(getContext(),"Favori Market Yok.",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(GONE);
        }
        db.close();
        return view;
    }

    public ArrayList<HashMap<String, String>> getProducts(){
        db = new FavoriteDatabase(getContext());
        return db.products();
    }


    public void getStores() {
        Callback<List<Stores>> listCallBack = new Callback<List<Stores>>() {
            @Override
            public void onResponse(Call<List<Stores>> call, Response<List<Stores>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        itemList=response.body();
                        load();

                    }else {
                        Toast.makeText(getContext(),"Mağaza Yok",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Stores>> call, Throwable t) {

            }
        };
        interfaces.getStores().enqueue(listCallBack);
    }

    public void  load(){
        if(stores.size()>0){
            for(int a=0;a<stores.size();a++){
                for(int x=0;x<itemList.size();x++){
                    if(stores.get(a).get("store").equals(String.valueOf(itemList.get(x).getId()))){
                        newItemList.add(newItemList.size(),itemList.get(x));
                    }
                }
            }
            StoreAdapter storeAdapter = new StoreAdapter(newItemList,getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(storeAdapter);
            storeAdapter.setOnItemClickListener(FavoriteFragment.this);
            storeAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(),"Favori Market Yok.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(int position) {
        Integer id = newItemList.get(position).getId();
        Intent intent =new Intent(getContext(), ProductActivity.class);
        intent.putExtra("product",id);
        intent.putExtra("name",newItemList.get(position).getStoreName());
        startActivity(intent);
    }

}