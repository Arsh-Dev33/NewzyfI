package com.project.newzyfi.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.project.newzyfi.R;

public class BrowserFragment extends Fragment {

    View rootView;
    String link="";
    WebView webView;
    private AdView mAdView;
    public BrowserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_browser, container, false);

        webView = rootView.findViewById(R.id.web_link);

        link = getArguments().getString("link");

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = rootView.findViewById(R.id.adView_browser);
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
                            if(webView.canGoBack()){
                                webView.goBack();
                            }else{
                                getActivity().onBackPressed();
                            }

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


}
