package com.mainproject.vishnu_neelancheri.pencilemployee.due;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PrefModel;
import com.mainproject.vishnu_neelancheri.pencilemployee.work_today.WorkTodayModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewDueActivity extends AppCompatActivity {
    private RecyclerView recyclerViewDue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_due);
        recyclerViewDue = findViewById( R.id.recycler_view_due );
        recyclerViewDue.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewDue.setLayoutManager( layoutManager );
        initialSetUp();
    }
    private void initialSetUp(){
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref( getApplicationContext() );
        Map<String, String > param = new HashMap<>();
        param.put("emp_id", prefModel.getUserId() );
        param.put("token", prefModel.getToken() );
        String url = PencilUtil.BASE_URL + "employee/bank_due";
        NetWorkConnection.getInstance().volleyPost(url, param, ViewDueActivity.this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    Type listType = new TypeToken<ArrayList<DueModel>>(){}.getType();
                    ArrayList<DueModel> dueModelList = (ArrayList<DueModel>) new Gson().fromJson(response, listType);
                    setUpRecycler( dueModelList );
                }catch ( Exception e ){
                    //Do nothing
                    finish();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("temp", errorMessage );
                PencilUtil.toaster( getBaseContext(), "Can't reach server");
                finish();
            }
        });
    }
    private void setUpRecycler(ArrayList<DueModel> dueModels){
        ViewDueAdapter viewDueAdapter = new ViewDueAdapter(ViewDueActivity.this, dueModels, new ViewDueAdapter.Clicker() {
            @Override
            public void click(String id) {
                payAmount( id );
            }
        });
        recyclerViewDue.setAdapter( viewDueAdapter );
    }
    private void payAmount(String paymentId){
        String url = PencilUtil.BASE_URL + "employee/update_bank_due";
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref( getApplicationContext() );
        Map<String, String > param = new HashMap<>();
        param.put("emp_id", prefModel.getUserId() );
        param.put("token", prefModel.getToken() );
        param.put("due_id", paymentId );
        NetWorkConnection.getInstance().volleyPost(url, param, ViewDueActivity.this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {

                if ( response.equals("1")){
                    PencilUtil.toaster( getBaseContext(), "Status updated successfully");
                    finish();
                }else {
                    PencilUtil.toaster( getBaseContext(), "Status update failed");
                }
            }

            @Override
            public void onError(String errorMessage) {
                PencilUtil.toaster( getBaseContext(), "Status update failed due to network error");
            }
        });
    }
}
