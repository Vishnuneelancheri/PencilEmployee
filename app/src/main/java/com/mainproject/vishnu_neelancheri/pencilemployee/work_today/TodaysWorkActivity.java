package com.mainproject.vishnu_neelancheri.pencilemployee.work_today;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.ViewPhotoAndUpdateStatus.ViewAndUpdateActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PrefModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodaysWorkActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTodayWork;
    private WorkTodayAdapter workTodayAdapter;
    private ArrayList<WorkTodayModel> listFromOutSide = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_work);
        recyclerViewTodayWork = findViewById( R.id.recycler_todays_work );
        recyclerViewTodayWork.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTodayWork.setLayoutManager( layoutManager );

        Bundle bundle = getIntent().getExtras();
        try{
            listFromOutSide = bundle.getParcelableArrayList(getResources().getString(R.string.app_name ));
        }catch ( NullPointerException ignored ){

        }
        if ( listFromOutSide.size() > 0 ){
            initialiseRecycler( listFromOutSide );
        }else
            getWork();
    }
    private void getWork(){
        String url = PencilUtil.BASE_URL+ "employee/todays_work";
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref(this);
        Map<String, String> param = new HashMap<>();
        param.put("emp_id", prefModel.getUserId() );
        NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    Type listType = new TypeToken<ArrayList<WorkTodayModel>>(){}.getType();
                    ArrayList<WorkTodayModel> workTodayModelList = (ArrayList<WorkTodayModel> ) new Gson().fromJson( response, listType);
                    initialiseRecycler( workTodayModelList );
                }catch (Exception e){

                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("error", errorMessage );
            }
        });
    }
    private void initialiseRecycler(ArrayList<WorkTodayModel> workTodayModelList){
        workTodayAdapter = new WorkTodayAdapter(getApplicationContext(), workTodayModelList, new WorkTodayAdapter.SelectTodayWork() {
            @Override
            public void select(WorkTodayModel workTodayModel) {
                moveToDetails( workTodayModel );
            }
        });
        recyclerViewTodayWork.setAdapter( workTodayAdapter );
    }
    public void moveToDetails(WorkTodayModel workTodayModel){
        Bundle bundle = new Bundle();
        bundle.putParcelable(getResources().getString(R.string.app_name), workTodayModel );
        Intent intent = new Intent( this, ViewAndUpdateActivity.class);
        intent.putExtras( bundle );
        startActivity( intent );
        finish();
    }
}
