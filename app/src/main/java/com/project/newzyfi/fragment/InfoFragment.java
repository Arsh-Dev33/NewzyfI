package com.project.newzyfi.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.newzyfi.R;


public class InfoFragment extends Fragment {

    View rootView;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

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
}
