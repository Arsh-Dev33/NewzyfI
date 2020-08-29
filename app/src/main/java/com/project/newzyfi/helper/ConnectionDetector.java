package com.project.newzyfi.helper;

/**
 * Created by Sony on 3/21/2016.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector
{


private Context _context;

public ConnectionDetector(Context context){
        this._context = context;
        }

/**
 * Checking for all possible internet providers
 * **/
public boolean isConnectingToInternet()
        {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null)
        for (int i = 0; i < info.length; i++)
        {
        System.out.println("Network Info--"+info[i].getState());
        if (info[i].getState() == NetworkInfo.State.CONNECTED)
        {

        return true;
        }
        }


        }
        return false;
        }
        }

