package com.project.newzyfi.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.project.newzyfi.R;
import com.project.newzyfi.helper.Common;
import com.project.newzyfi.helper.ConnectionDetector;
import com.project.newzyfi.sqlmanager.SQLiteManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsCardDetailFragment extends Fragment {

    View rootView;
    TextView tvContent,tvTitle,tvPublished,tvFullArticle;
    ImageView ivDetailImage,ivSave,ivShare;
    String content = "";
    String image = "";
    String url ="";
    String published ="",headline="",from="";
    SQLiteManager sqLiteManager;
    Common common;
    private InterstitialAd mInterstitialAd;
    ConnectionDetector cd;
    AdView mAdView;

    public NewsCardDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_news_card_detail, container, false);

        tvContent = rootView.findViewById(R.id.tv_content);
        ivDetailImage = rootView.findViewById(R.id.iv_news_detail_image);
        tvTitle = rootView.findViewById(R.id.tv_title_details);
        tvPublished = rootView.findViewById(R.id.tv_published_detail);
        sqLiteManager = new SQLiteManager(getContext());
        ivSave = rootView.findViewById(R.id.iv_save);
        ivShare = rootView.findViewById(R.id.iv_share);
        tvFullArticle = rootView.findViewById(R.id.tv_full_article);
        common = new Common();
        cd = new ConnectionDetector(getContext());

        content = getArguments().getString("news_content");
        image = getArguments().getString("news_image");
        url = getArguments().getString("news_link");
        published = getArguments().getString("news_published");
        headline = getArguments().getString("news_headline");
        from = getArguments().getString("from");

        AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.SMART_BANNER);

        String tempPublished[] = published.split("T");
        String published_display = tempPublished[0];

        String tempContent[] = content.split("[+]");
        String content_display = tempContent[0];

        if(from.equalsIgnoreCase("saved")){
            ivSave.setVisibility(View.GONE);
        }else{
            ivSave.setVisibility(View.VISIBLE);
        }

        tvFullArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("link",url);

                Fragment fragment = new BrowserFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack("TrendingFragment");
                fragmentTransaction.commit();
            }
        });

        tvContent.setText(content_display);
        tvTitle.setText(headline);
        tvPublished.setText(published_display);

        Picasso.get().load(image).fit().centerCrop().into(ivDetailImage);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = rootView.findViewById(R.id.adView_detail);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = rootView.findViewById(R.id.adView_detail_one);
       // AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-5395197670211088/4598986372");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissions() == true){

                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            AddDatatoSavedFragment();
                        }
                }else{
                    checkPermissions();
                }


            }
        });

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                AddDatatoSavedFragment();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    shareApp(getActivity());
                }

            }
        });

        return rootView;
    }

    public void AddDatatoSavedFragment(){

        ArrayList<String> data = new ArrayList<>();
        data.add(0,headline);
        data.add(1,content);
        data.add(2,url);
        data.add(3,image);
        data.add(4,published);

        sqLiteManager.addNewsData(data);
        common.showMessage(getContext(),"Added Successfully");

    }

    public static void shareApp(Context context)
    {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"pls check this data");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    private boolean checkPermissions()  {

        if(cd.isConnectingToInternet()) {

            if (/*ActivityCompat.checkSelfPermission(Login.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(Login.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&*/
                    ActivityCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED /*&&
                    ActivityCompat.checkSelfPermission(Login.this,
                            Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED*/) {

                return true;

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{/*Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION,*/Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE/*, Manifest.permission.ACCESS_NETWORK_STATE */}, 2);
                return false;

            }


        } else {

            common.showMessage(getContext(),"Please Check Your Network Connection");
            return false;

        }

    }


}
