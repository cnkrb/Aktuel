package com.cenkkaraboa.aktelrnlerkatalou.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cenkkaraboa.aktelrnlerkatalou.Activitites.EmptyActivity;
import com.cenkkaraboa.aktelrnlerkatalou.Activitites.SettingsActivity;
import com.cenkkaraboa.aktelrnlerkatalou.Adapters.ProductAdapter;
import com.cenkkaraboa.aktelrnlerkatalou.Database.FavoriteDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.Database.SaveDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Product;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Stores;
import com.cenkkaraboa.aktelrnlerkatalou.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.cenkkaraboa.aktelrnlerkatalou.Utils.Utils.interfaces;


public class ProfileFragment extends Fragment implements  ProductAdapter.OnItemClickListener {

    ImageView settings;
    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SaveDatabase db ;

    ArrayList<HashMap<String, String>> products;
    public List<Product> itemList,newItemList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_profile, container, false);
        settings=view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        products=getProducts();
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView.setVisibility(GONE);
        getStores();
        db.close();
        return view;
    }


    public void getStores() {
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() <= 0) {
                        Toast.makeText(getContext(), "Son Eklenen Ürün Yok", Toast.LENGTH_SHORT).show();
                    }else {
                        itemList=response.body();
                        load();
                    }
                    progressBar.setVisibility(GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        };
        interfaces.allProduct().enqueue(listCallBack);
    }

    @Override
    public void onItemClick(int position) {
        String url=newItemList.get(position).getPatch();
        Intent intent =new Intent(getContext(), EmptyActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("name",newItemList.get(position).getDate());
        intent.putExtra("id",String.valueOf(newItemList.get(position).getId()));
        startActivity(intent);
    }

    public ArrayList<HashMap<String, String>> getProducts(){
        db = new SaveDatabase(getContext());
        return db.products();
    }

    public void load(){
        if(products.size()>0){
            for(int a=0;a<products.size();a++){
                for(int x=0;x<itemList.size();x++){
                    if(products.get(a).get("store").equals(String.valueOf(itemList.get(x).getId()))){
                        newItemList.add(newItemList.size(),itemList.get(x));
                    }
                }
            }
            System.out.println(newItemList.size()+"asdsadsadasdasdasdasdasd");
            ProductAdapter storeAdapter = new ProductAdapter(newItemList,getContext(),"add");
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(storeAdapter);
            storeAdapter.setOnItemClickListener(ProfileFragment.this);
            storeAdapter.notifyDataSetChanged();
        }else {
           Toast.makeText(getContext(),"Kaydedilmiş Ürün Yok",Toast.LENGTH_SHORT).show();
        }

    }
}