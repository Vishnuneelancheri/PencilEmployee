package com.mainproject.vishnu_neelancheri.pencilemployee.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 2/25/2018
 */

public class NetWorkConnection {
    private static volatile  NetWorkConnection mNetWorkConnection;

    public static NetWorkConnection getInstance(){
        if ( mNetWorkConnection == null ){
            synchronized ( NetWorkConnection.class ){
                if ( mNetWorkConnection == null ){
                    mNetWorkConnection = new NetWorkConnection();
                }
            }
        }
        return mNetWorkConnection;
    }

    public void volleyPost(String url, final Map<String, String> params,
                           Context context, final NetworkResponse networkResponse ){

        final ProgressDialog progressDialog = new ProgressDialog( context );
        progressDialog.setMessage("Loading, Please wait...");
        progressDialog.setTitle("Pencil admin!!!");
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue requestQueue = Volley.newRequestQueue( context );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        networkResponse.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                networkResponse.onError(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void volleyGetting(String url, Context context, final NetworkResponse networkResponse ){
        final ProgressDialog progressDialog = new ProgressDialog( context );
        progressDialog.setMessage("Loading, Please wait...");
        progressDialog.setTitle("Pencil admin!!!");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        networkResponse.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                networkResponse.onError(error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}
