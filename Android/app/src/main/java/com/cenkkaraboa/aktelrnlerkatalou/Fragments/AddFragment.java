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

import com.cenkkaraboa.aktelrnlerkatalou.Activitites.EmptyActivity;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Product;
import com.cenkkaraboa.aktelrnlerkatalou.Adapters.ProductAdapter;
import com.cenkkaraboa.aktelrnlerkatalou.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.cenkkaraboa.aktelrnlerkatalou.Utils.Utils.interfaces;


public class AddFragment extends Fragment implements ProductAdapter.OnItemClickListener {

    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public List<Product> itemList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView.setVisibility(GONE);
        getStores();
        return view;
    }


    public void getStores() {
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        itemList=response.body();
                        ProductAdapter storeAdapter = new ProductAdapter(response.body(),getContext(),"add");
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(AddFragment.this);
                        storeAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getContext(),"Son Eklenen Ürün Yok",Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        itemList=response.body();
                        ProductAdapter storeAdapter = new ProductAdapter(response.body(),getContext(),"add");
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(AddFragment.this);
                        storeAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        };
        interfaces.add().enqueue(listCallBack);
    }


    @Override
    public void onItemClick(int position) {
        String url=itemList.get(position).getPatch();
        Intent intent =new Intent(getContext(), EmptyActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("name",itemList.get(position).getDate());
        intent.putExtra("id",String.valueOf(itemList.get(position).getId()));
        startActivity(intent);
    }
}