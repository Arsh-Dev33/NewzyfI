package com.project.newzyfi.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.project.newzyfi.R;
import com.project.newzyfi.adapter.SavedAdapter;
import com.project.newzyfi.adapter.SourceAdapter;
import com.project.newzyfi.adapter.TrendingAdapter;
import com.project.newzyfi.helper.Common;
import com.project.newzyfi.model.SavedNewsModel;
import com.project.newzyfi.response.TrendingResponse;
import com.project.newzyfi.sqlmanager.SQLiteManager;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    View rootView;
    RecyclerView recyclerView;
    SavedAdapter savedAdapter;
    SQLiteManager sqLiteManager;
    SavedAdapter.OnCardClickListener onCardClickListener;
    ArrayList<SavedNewsModel> cache = new ArrayList<>();
    Common common;
    LinearLayout lvNoData;
    AdView mAdView;

    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = rootView.findViewById(R.id.rv_saved);
        sqLiteManager = new SQLiteManager(getContext());
        lvNoData = rootView.findViewById(R.id.lv_no_data);
        common = new Common();
        RefreshCartDetails();

        AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.SMART_BANNER);

        mAdView = rootView.findViewById(R.id.adView_saved);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                        //This is the filter
                        if (event.getAction() != KeyEvent.ACTION_DOWN) {

                            // getActivity().onBackPressed();
                           // DialogLogout();

                            return true;

                        } else {
                            //Hide your keyboard here!!!!!!
                            return true; // pretend we've processed it
                        }

                    } else
                        return false; // pass on to be processed as normal
                }
                return false;
            }
        });


        return rootView;
    }

    public void deleteCard(int index){

        sqLiteManager.deleteSavedItem(index);
        savedAdapter.notifyDataSetChanged();
        RefreshCartDetails();

    }

    public void RefreshCartDetails(){

        cache = sqLiteManager.getSavedNews();

        if (cache.isEmpty()){

            lvNoData.setVisibility(View.VISIBLE);

        }

            setonClickListener();
            savedAdapter = new SavedAdapter(cache,SavedFragment.this,onCardClickListener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(savedAdapter);

    }

    public void setonClickListener(){
        onCardClickListener = new SavedAdapter.OnCardClickListener() {
            @Override
            public void onItemClick(int position) {

                Bundle bundle = new Bundle();
                bundle.putString("news_image",cache.get(position).getUrl_image());
                bundle.putString("news_content",cache.get(position).getDescription());
                bundle.putString("news_headline",cache.get(position).getTitle());
                bundle.putString("news_published",cache.get(position).getPublished());
                bundle.putString("news_link",cache.get(position).getUrl());
                bundle.putString("from","saved");


                Fragment fragment = new NewsCardDetailFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack("TrendingFragment");
                fragmentTransaction.commit();


            }
        };

    }




}
