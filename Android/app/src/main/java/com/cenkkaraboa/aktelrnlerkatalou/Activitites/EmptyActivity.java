package com.cenkkaraboa.aktelrnlerkatalou.Activitites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.aktelrnlerkatalou.Database.FavoriteDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.Database.SaveDatabase;
import com.cenkkaraboa.aktelrnlerkatalou.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.request.FileLoadRequest;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EmptyActivity extends AppCompatActivity {

    PDFView pdfView;
    ProgressBar progressBar;
    TextView name;
    ImageView back,empty;
    LinearLayout layout;
    String productUrl,productName,productId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        layout=findViewById(R.id.layout);


        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getAdViewHeightInDP(EmptyActivity.this),
                r.getDisplayMetrics()
        );

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,px);
        layout.setLayoutParams(params);
        pdfView=findViewById(R.id.pdfView);
        progressBar=findViewById(R.id.progressBar);
        name=findViewById(R.id.name);
        back=findViewById(R.id.back);
        empty=findViewById(R.id.empty);
        Bundle extras = getIntent().getExtras();
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
        String url=null;
        SaveDatabase  db = new SaveDatabase(getApplicationContext());
        if(extras == null) {

        } else {
            productName=extras.getString( "name");
            productId=extras.getString( "id");
            productUrl= extras.getString( "url");
            name.setText(productName);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayList<HashMap<String, String>> stores=db.products();
        Boolean value=false;
        for(int a=0;a<stores.size();a++){
            if(String.valueOf(productId).equals(stores.get(a).get("store"))){
                value=true;
            }
        }
        if(value){
            empty.setTag("save");
            empty.setImageResource(R.drawable.save);
        }else {
            empty.setTag("empty");
            empty.setImageResource(R.drawable.empty);
        }
        empty.setOnClickListener(new View.OnClickListener() {
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

                String imageName= (String) empty.getTag();
                if(imageName.length()==5){
                    empty.setTag("save");
                    empty.setImageResource(R.drawable.save);
                    db.productAdd(productName,productId);
                }else {
                    empty.setTag("empty");
                    empty.setImageResource(R.drawable.empty);
                    db.productDelete(Integer.parseInt(productId));
                }
            }
        });

        db.close();
        FileLoader.with(getApplicationContext())
                .load("https://aktuel.cenkkaraboa.com/public/images/products/"+productUrl)
                .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progressBar.setVisibility(View.GONE);
                        pdfView.setVisibility(View.VISIBLE);
                        File pdfFile=response.getBody();
                        pdfView.fromFile(pdfFile)
                                .password(null)
                                .enableDoubletap(true)
                                .swipeHorizontal(true)
                                .enableSwipe(true)
                                .pageSnap(true)
                                .autoSpacing(true)
                                .pageFling(true)
                                .onDraw((canvas, pageWidth, pageHeight, displayedPage) -> {

                                })
                                .onDrawAll((canvas, pageWidth, pageHeight, displayedPage) -> {

                                })
                                .onPageError((page, t) -> {
                                })
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

                                    }
                                })
                                .onTap(new OnTapListener() {
                                    @Override
                                    public boolean onTap(MotionEvent e) {
                                        return true;
                                    }
                                })
                                .enableAnnotationRendering(true)
                                .load();

                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        System.out.println(request.getFileExtension());
                    }
                });
    }


    public void permissions(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EmptyActivity.this);
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