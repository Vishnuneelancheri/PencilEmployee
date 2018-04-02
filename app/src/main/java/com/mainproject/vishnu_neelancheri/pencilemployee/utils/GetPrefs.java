package com.mainproject.vishnu_neelancheri.pencilemployee.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mainproject.vishnu_neelancheri.pencilemployee.R;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 2/25/2018
 */

public class GetPrefs {
    public static volatile GetPrefs getPrefs;
    public static GetPrefs getInstance(){
        if ( getPrefs == null ){
            synchronized ( GetPrefs.class ){
                if ( getPrefs == null ){
                    getPrefs = new GetPrefs();
                }
            }
        }
        return getPrefs;
    }
    public PrefModel getSharedPref(Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences( context.getResources().getString( R.string.app_name), Context.MODE_PRIVATE );
        PrefModel prefModel = new PrefModel();
        prefModel.setToken( sharedPreferences.getString(context.getResources().getString(R.string.token), "") );
        prefModel.setUserId(sharedPreferences.getString(context.getResources().getString(R.string.user_id), ""));
        return prefModel;
    }
    public boolean logout( Context context ){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences( context.getResources().getString( R.string.app_name), Context.MODE_PRIVATE );
        return sharedPreferences.edit().clear().commit();
    }
}
