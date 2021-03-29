package com.cenkkaraboa.aktelrnlerkatalou.Activitites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.aktelrnlerkatalou.Database.FavoriteDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Product;
import com.cenkkaraboa.aktelrnlerkatalou.Adapters.ProductAdapter;
import com.cenkkaraboa.aktelrnlerkatalou.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.cenkkaraboa.aktelrnlerkatalou.Utils.Utils.interfaces;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener{
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView back,fav;
    TextView  name;
    LinearLayout layout;
    public List<Product> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        back=findViewById(R.id.back);
        layout=findViewById(R.id.layout);
        name=findViewById(R.id.name);
        fav=findViewById(R.id.fav);
        permissions();
        MobileAds.initialize(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
       /* mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("ca-app-pub-9280706421416251/9316759262");*/
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        getAdViewHeightInDP(ProductActivity.this)+55,
                        r.getDisplayMetrics()
                );

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,0,px);
                layout.setLayoutParams(params);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                System.out.println(adError);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setVisibility(GONE);
        int id;
        Bundle extras = getIntent().getExtras();
        if(!(extras == null)) {
            id= extras.getInt( "product");
            getStores(id);
            name.setText(extras.getString( "name"));
            FavoriteDatabase db = new FavoriteDatabase(getApplicationContext());
            ArrayList<HashMap<String, String>> stores=db.products();

            Boolean value=false;
            for(int a=0;a<stores.size();a++){
                if(String.valueOf(extras.getInt( "product")).equals(stores.get(a).get("store"))){
                    value=true;
                }
            }
            if(value){
                fav.setTag("fav");
                fav.setImageResource(R.drawable.redheart);
            }else {
                fav.setTag("black");
                fav.setImageResource(R.drawable.heart);
            }
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InterstitialAd interstitialAd=new InterstitialAd(getApplicationContext());
                    interstitialAd.setAdUnitId("ca-app-pub-9280706421416251/9462909550");
                    interstitialAd.loadAd(new AdRequest.Builder().build());

                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            interstitialAd.show();
                        }
                    });


                    String imageName= (String) fav.getTag();
                    if(imageName.length()==5){
                        fav.setTag("fav");
                        fav.setImageResource(R.drawable.redheart);
                        db.productAdd(extras.getString( "name"),String.valueOf(extras.getInt( "product")));
                    }else {
                        fav.setTag("black");
                        fav.setImageResource(R.drawable.heart);
                        db.productDelete(extras.getInt( "product"));

                    }
                }

            });
            db.close();

        }



    }

    public void getStores(int id) {
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        itemList=response.body();
                        ProductAdapter storeAdapter = new ProductAdapter(response.body(),getApplicationContext(),"null");
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                        recyclerView.setAdapter(storeAdapter);
                        storeAdapter.setOnItemClickListener(ProductActivity.this);
                        storeAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getApplicationContext(),"Markete ait ürün mevcut değildir.",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        };
        interfaces.getProduct(id).enqueue(listCallBack);
    }


    @Override
    public void onItemClick(int position) {
        String url=itemList.get(position).getPatch();
        Intent intent =new Intent(getApplicationContext(), EmptyActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("name",itemList.get(position).getDate());
        intent.putExtra("id",String.valueOf(itemList.get(position).getId()));
        startActivity(intent);
    }

    public void permissions(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(getApplicationContext(),"Aktüelleri görmek için izinleri vermeniz gerekmektedir",Toast.LENGTH_SHORT).show();
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("İzinlere ihtiyacımız var");
        builder.setMessage("Aktüelleri kullanmanız için izinleri vermeniz gerekmektedir.");
        builder.setPositiveButton("Ayarlar Git", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public static int getAdViewHeightInDP(Activity activity) {
        int adHeight = 0;

        int screenHeightInDP = getScreenHeightInDP(activity);
        if (screenHeightInDP < 400)
            adHeight = 32;
        else if (screenHeightInDP <= 720)
            adHeight = 50;
        else
            adHeight = 90;

        return adHeight;
    }

    public static int getScreenHeightInDP(Activity activity) {
        DisplayMetrics displayMetrics = ((Context) activity).getResources().getDisplayMetrics();

        float screenHeightInDP = displayMetrics.heightPixels / displayMetrics.density;

        return Math.round(screenHeightInDP);
    }

}