package com.project.newzyfi.fragment;

import android.app.ProgressDialog;
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

import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.chip.ChipGroup;
import com.project.newzyfi.R;
import com.project.newzyfi.adapter.SourceAdapter;
import com.project.newzyfi.adapter.TrendingAdapter;
import com.project.newzyfi.helper.Common;
import com.project.newzyfi.helper.ConnectionDetector;
import com.project.newzyfi.response.TrendingResponse;
import com.project.newzyfi.webservices.API;
import com.project.newzyfi.webservices.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SourceFragment extends Fragment  {

    View rootView;
    ChipGroup chipGroup;
    Common common;
    ConnectionDetector cd;
    RecyclerView recyclerView;
    SourceAdapter sourceAdapter;
    ArrayList<TrendingResponse.articles> arrayList;
    TrendingResponse trendingResponse;
    SourceAdapter.OnCardClickListener onCardClickListener;

    public SourceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_source, container, false);
        recyclerView = rootView.findViewById(R.id.rv_source);
        chipGroup = rootView.findViewById(R.id.chip_group);
        common = new Common();
        cd = new ConnectionDetector(getActivity());

        callSourceListApi("fox-news");

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
                          //  DialogLogout();

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



        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.chip_one:
                        common.showMessage(getActivity(),"Google");
                        callSourceListApi("google-news");
                        break;

                    case R.id.chip_two:
                        common.showMessage(getActivity(),"BBC");
                        callSourceListApi("bbc-news");
                        break;

                    case R.id.chip_three:
                        common.showMessage(getActivity(),"FOX");
                        callSourceListApi("fox-news");
                        break;

                    case R.id.chip_four:
                        common.showMessage(getActivity(),"CNN");
                        callSourceListApi("cnn");
                        break;

                }
            }
        });




        return rootView;
    }

    private void callSourceListApi(String source_name) {

        if (cd.isConnectingToInternet()) {

            //   shimmerFrameLayout.startShimmerAnimation();
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Please Wait...");
            dialog.show();
            //     OkHttpClient okClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.Base_url).client(common.getUnsafeOkHttpClient().build()).addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);

//            String userId = SharedPreferenceManager.getUserId(getActivity());
//            String jwtToken =  "Bearer " + SharedPreferenceManager.getJwtToken(getActivity());

            Call<TrendingResponse> call = api.getSource(source_name);

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
                        sourceAdapter = new SourceAdapter(arrayList,SourceFragment.this,onCardClickListener);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        //    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(sourceAdapter);


                    } else {

                        common.showMessage(getActivity(),"NO DATA SHOWN");

                    }

                }

                public void setonClickListener(){
                    onCardClickListener = new SourceAdapter.OnCardClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            Bundle bundle = new Bundle();
                            bundle.putString("news_image",arrayList.get(position).getUrlToImage());
                            bundle.putString("news_content",arrayList.get(position).getContent());
                            bundle.putString("news_headline",arrayList.get(position).getTitle());
                            bundle.putString("news_published",arrayList.get(position).getPublishedAt());
                            bundle.putString("news_link",arrayList.get(position).getUrl());
                            bundle.putString("from","source");


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


}
