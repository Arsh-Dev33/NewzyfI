package com.project.newzyfi.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.chip.ChipGroup;
import com.project.newzyfi.R;
import com.project.newzyfi.activity.MainActivity;
import com.project.newzyfi.adapter.SourceAdapter;
import com.project.newzyfi.adapter.TrendingAdapter;
import com.project.newzyfi.helper.Common;
import com.project.newzyfi.helper.ConnectionDetector;
import com.project.newzyfi.helper.SharedPreferenceManager;
import com.project.newzyfi.response.TrendingResponse;
import com.project.newzyfi.webservices.API;
import com.project.newzyfi.webservices.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrendingFragment extends Fragment {

    View rootView;
    RecyclerView recyclerView;
    TrendingAdapter trendingAdapter;
    ArrayList<TrendingResponse.articles> arrayList;
    ConnectionDetector cd;
    Common common;
    TrendingResponse trendingResponse;
    CardView cvHealth,cvEntertainment,cvTechnology,cvBusiness,cvScience,cvSports;
    TextView tvTitle;
    TrendingAdapter.OnCardTrendingClickListener onCardTrendingClickListener;
    ChipGroup chipGroup;
    LinearLayout lvCountrySection;
    String tempCountrySelected ="";
   // ShimmerFrameLayout shimmerFrameLayout;

    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_trending, container, false);

        recyclerView = rootView.findViewById(R.id.rv_trending);
        cvHealth = rootView.findViewById(R.id.cv_health);
        cvEntertainment= rootView.findViewById(R.id.cv_entertainment);
        cvTechnology = rootView.findViewById(R.id.cv_technology);
        cvBusiness = rootView.findViewById(R.id.cv_business);
        cvScience = rootView.findViewById(R.id.cv_science);
        cvSports = rootView.findViewById(R.id.cv_sports);
        tvTitle = rootView.findViewById(R.id.tv_title_dashboard);
        chipGroup = rootView.findViewById(R.id.chip_group_country);
        lvCountrySection = rootView.findViewById(R.id.lv_country_selection);

        cd = new ConnectionDetector(getActivity());
       // shimmerFrameLayout = rootView.findViewById(R.id.shimmer);
        common = new Common();

        tempCountrySelected = SharedPreferenceManager.getCountry(getContext());

        if(SharedPreferenceManager.getCountry(getContext()).equalsIgnoreCase("")){
            country_selection_dialog();
        }else {

            callTrendingListApi("general",tempCountrySelected);

        }

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




        cvHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrendingListApi("health",tempCountrySelected);
                tvTitle.setText("Health");
            }
        });

        cvEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrendingListApi("entertainment",tempCountrySelected);
                tvTitle.setText("Entertainment");
            }
        });

        cvTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrendingListApi("technology",tempCountrySelected);
                tvTitle.setText("Technology");
            }
        });

        cvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrendingListApi("business",tempCountrySelected);
                tvTitle.setText("Business");
            }
        });

        cvScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrendingListApi("science",tempCountrySelected);
                tvTitle.setText("Science");
            }
        });

        cvSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrendingListApi("sports",tempCountrySelected);
                tvTitle.setText("Sports");
            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.c_india:
                        SharedPreferenceManager.setCountry(getActivity(),"in");
                        break;

                    case R.id.c_usa:
                        SharedPreferenceManager.setCountry(getActivity(),"us");
                        break;

                    case R.id.c_uk:
                        SharedPreferenceManager.setCountry(getActivity(),"gb");
                        break;

                    case R.id.c_russia:
                        SharedPreferenceManager.setCountry(getActivity(),"ru");
                        break;

                    case R.id.c_france:
                        SharedPreferenceManager.setCountry(getActivity(),"fr");
                        break;

                }

            }
        });

        return rootView;
    }

    private void callTrendingListApi(String category_name,String country_code) {

        if (cd.isConnectingToInternet()) {

         //   shimmerFrameLayout.startShimmerAnimation();
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Please Wait...");
            dialog.show();
            //     OkHttpClient okClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.Base_url).client(common.getUnsafeOkHttpClient().build()).addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);

//            String userId = SharedPreferenceManager.getUserId(getActivity());
//            String jwtToken =  "Bearer " + SharedPreferenceManager.getJwtToken(getActivity());

            Call<TrendingResponse> call = api.getTrending(category_name,country_code);

            call.enqueue(new Callback<TrendingResponse>() {

                @Override
                public void onResponse(Call<TrendingResponse> call, Response<TrendingResponse> response) {
                   // shimmerFrameLayout.stopShimmerAnimation();
                    dialog.dismiss();
                    System.out.println("GetAddressDetailsResponse--" + response.body());
                    trendingResponse = response.body();

                    if (trendingResponse != null && trendingResponse.getStatus() != null && trendingResponse.getStatus().equalsIgnoreCase("ok")) {

                        arrayList = new ArrayList<>();

                        for (int  i = 0;i < trendingResponse.getArticles().length;i++){

                            arrayList.add(trendingResponse.getArticles()[i]);

                        }

                        setonClickListener();
                        trendingAdapter = new TrendingAdapter(arrayList,TrendingFragment.this,onCardTrendingClickListener);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    //    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(trendingAdapter);


                    } else {

                        common.showMessage(getActivity(),"DATA NOT AVAILABLE");

                    }

                }

                public void setonClickListener(){
                    onCardTrendingClickListener = new TrendingAdapter.OnCardTrendingClickListener() {
                        @Override
                        public void onItemTrendingClick(int position) {

                            System.out.println("contents "+arrayList.get(position).getContent());

                            Bundle bundle = new Bundle();
                            bundle.putString("news_image",arrayList.get(position).getUrlToImage());
                            bundle.putString("news_content",arrayList.get(position).getContent());
                            bundle.putString("news_headline",arrayList.get(position).getTitle());
                            bundle.putString("news_published",arrayList.get(position).getPublishedAt());
                            bundle.putString("news_link",arrayList.get(position).getUrl());
                            bundle.putString("from","trending");

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


                @Override
                public void onFailure(Call<TrendingResponse> call, Throwable t) {
                    dialog.dismiss();
                    //shimmerFrameLayout.stopShimmerAnimation();
                    System.out.println("onFailure--" + t.getMessage());
                    common.showMessage(getActivity(), "Failed....");

                }

            });

        } else {

            common.showMessage(getActivity(), "Please Check Your Network Connection..");
            //openNetworkConnectionPopup();
            //navigateToNoInternetAvailableFragment();

        }

    }

    public void checkCountry(){
        //String defaultCountry = "in";
        String selectedCountry = SharedPreferenceManager.getCountry(getActivity());

        if(selectedCountry.equalsIgnoreCase("")){
            tempCountrySelected = "in";
           lvCountrySection.setVisibility(View.VISIBLE);

        }else if(!selectedCountry.equalsIgnoreCase("")){
            tempCountrySelected = SharedPreferenceManager.getCountry(getContext());
            SharedPreferenceManager.setCountry(getContext(),tempCountrySelected);
            lvCountrySection.setVisibility(View.GONE);
        }

    }

    public void country_selection_dialog(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_country_selection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btSubmit = dialog.findViewById(R.id.bt_submit);
        RadioGroup radioGroup = dialog.findViewById(R.id.rd_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.rb_india:
                        SharedPreferenceManager.setCountry(getActivity(),"in");
                        break;

                    case R.id.rb_usa:
                        SharedPreferenceManager.setCountry(getActivity(),"us");
                        break;

                    case R.id.rb_uk:
                        SharedPreferenceManager.setCountry(getActivity(),"gb");
                        break;

                    case R.id.rb_russia:
                        SharedPreferenceManager.setCountry(getActivity(),"ru");
                        break;

                    case R.id.rb_france:
                        SharedPreferenceManager.setCountry(getActivity(),"fr");
                        break;

                }
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempCountrySelected = SharedPreferenceManager.getCountry(getActivity());
                dialog.cancel();
                callTrendingListApi("general",SharedPreferenceManager.getCountry(getContext()));
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }



}
