package com.mainproject.vishnu_neelancheri.pencilemployee.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.due.ViewDueActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.new_order.NewOrderActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.search.SearchActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.search.SearchWithDateActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;
import com.mainproject.vishnu_neelancheri.pencilemployee.work_today.TodaysWorkActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.work_today.WorkTodayModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById( R.id.btn_new_order ).setOnClickListener( this );
        findViewById( R.id.btn_today_work ).setOnClickListener( this );
        findViewById( R.id.btn_search_bill ).setOnClickListener( this );
        findViewById( R.id.btn_search_phone ).setOnClickListener( this );
        findViewById( R.id.btn_search_date ).setOnClickListener( this );
        findViewById( R.id.btn_logout ).setOnClickListener( this );
        findViewById( R.id.btn_pending_works ).setOnClickListener( this );
        findViewById(R.id.btn_commission_payment ).setOnClickListener( this );
    }
    @Override
    public void onClick( View view ){
        Intent intent;
        switch ( view.getId() ){
            case R.id.btn_new_order:
                intent = new Intent( HomeActivity.this, NewOrderActivity.class );
                startActivity( intent );
                break;
            case R.id.btn_today_work:{
                intent = new Intent( HomeActivity.this, TodaysWorkActivity.class );
                startActivity( intent );
            }
            break;
            case R.id.btn_search_bill:{
                intent = new Intent( HomeActivity.this, SearchActivity.class );
                intent.putExtra(getResources().getString(R.string.app_name ), "employee/search_by_bill");
                intent.putExtra(getResources().getString(R.string.status ), "bill");

                startActivity( intent );
            }
            break;
            case R.id.btn_search_phone:{
                intent = new Intent( HomeActivity.this, SearchActivity.class );

                intent.putExtra(getResources().getString(R.string.app_name ), "employee/search_by_bill");
                intent.putExtra(getResources().getString(R.string.status ), "phone");
                startActivity( intent );
            }
            break;
            case R.id.btn_search_date :{
                intent = new Intent( HomeActivity.this, SearchWithDateActivity.class );
                startActivity( intent );
            }
            break;
            case R.id.btn_commission_payment:{
                intent = new Intent( HomeActivity.this, ViewDueActivity.class );
                startActivity( intent );
            }
            break;
            case R.id.btn_logout:{
                if (GetPrefs.getInstance().logout( getApplicationContext() ))
                    finish();
            }
            break;
            case R.id.btn_pending_works:{
                pendingWOrk();
            }
            break;

            default:break;
        }
    }
    private void pendingWOrk(){
        String url = PencilUtil.BASE_URL+"employee/get_pending_works";
        Map<String, String > param = new HashMap<>();
        param.put("emp_id", GetPrefs.getInstance().getSharedPref( this ).getUserId() );
        NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    Type listType = new TypeToken<ArrayList<WorkTodayModel>>(){}.getType();
                    ArrayList<WorkTodayModel> workTodayModelList = (ArrayList<WorkTodayModel> ) new Gson().fromJson( response, listType);
                    if ( workTodayModelList .size() > 0){
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList( getResources().getString(R.string.app_name ), workTodayModelList );
                        Intent intent = new Intent(HomeActivity.this, TodaysWorkActivity.class );
                        intent.putExtras( bundle );
                        startActivity( intent );

                    }else {
                        PencilUtil.toaster( getApplicationContext(), "No data found");
                    }

                }catch ( Exception e){
                    PencilUtil.toaster( getApplicationContext(), e.toString() );
                    finish();
                }
            }

            @Override
            public void onError(String errorMessage) {
                PencilUtil.toaster( getApplicationContext(), errorMessage);
            }
        });
    }
}
