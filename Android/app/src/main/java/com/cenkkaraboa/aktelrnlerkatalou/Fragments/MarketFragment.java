package com.cenkkaraboa.aktelrnlerkatalou.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.aktelrnlerkatalou.Activitites.ProductActivity;
import com.cenkkaraboa.aktelrnlerkatalou.R;
import com.cenkkaraboa.aktelrnlerkatalou.Adapters.StoreAdapter;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Stores;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.cenkkaraboa.aktelrnlerkatalou.Utils.Utils.interfaces;


public class MarketFragment extends Fragment  implements  StoreAdapter.OnItemClickListener{


    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout layout;
    TextView textSearch;
    ImageView close;
    EditText editSearch;
    public List<Stores> itemList;
    StoreAdapter storeAdapter;
    RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_market, container, false);
        layout=view.findViewById(R.id.layout);
        editSearch=view.findViewById(R.id.editSearch);

        textSearch=view.findViewById(R.id.textSearch);
        relativeLayout=view.findViewById(R.id.rela);
        close=view.findViewById(R.id.close);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(VISIBLE);
                recyclerView.setVisibility(GONE);
                close.setVisibility(View.INVISIBLE);
                editSearch.getText().clear();
                hideKeyboard(requireActivity());
                getStores();
            }
        });
        textSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search=editSearch.getText().toString();
                if(search.length()>0){
                    progressBar.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                    searchStores(search);
                    close.setVisibility(VISIBLE);
                }else {
                    Toast.makeText(getContext(),"Boş Bırakmayınız.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView.setVisibility(GONE);

        getStores();
        return view;
    }



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void getStores() {
        Callback<List<Stores>> listCallBack = new Callback<List<Stores>>() {
            @Override
            public void onResponse(Call<List<Stores>> call, Response<List<Stores>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(VISIBLE);
                        itemList=response.body();
                        storeAdapter = new StoreAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(MarketFragment.this);
                        storeAdapter.notifyDataSetChanged();
                    }else {
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(VISIBLE);
                        itemList=response.body();
                        storeAdapter = new StoreAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(MarketFragment.this);
                        storeAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        if(storeAdapter!=null){
            storeAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onItemClick(int position) {
        Integer id = itemList.get(position).getId();
        Intent intent =new Intent(getContext(), ProductActivity.class);
        intent.putExtra("product",id);
        intent.putExtra("name",itemList.get(position).getStoreName());
        startActivity(intent);
    }

    public void searchStores(String search) {
        Callback<List<Stores>> listCallBack = new Callback<List<Stores>>() {
            @Override
            public void onResponse(Call<List<Stores>> call, Response<List<Stores>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(VISIBLE);
                        itemList=response.body();
                        storeAdapter = new StoreAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(MarketFragment.this);
                        storeAdapter.notifyDataSetChanged();

                    }else {
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(VISIBLE);
                        itemList=response.body();
                        storeAdapter = new StoreAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(MarketFragment.this);
                        storeAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"Mağaza Yok",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Stores>> call, Throwable t) {

            }
        };
        interfaces.searchStores(search).enqueue(listCallBack);
    }

}