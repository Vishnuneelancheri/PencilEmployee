package com.mainproject.vishnu_neelancheri.pencilemployee.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
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

public class SearchWithDateActivity extends AppCompatActivity {
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_date);
        findViewById( R.id.btn_choose_date ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchData();
            }
        });
        datePicker = findViewById( R.id.datePicker );
    }
    private void searchData(){
        String url = PencilUtil.BASE_URL+"employee/search_by_date";
        String date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
        Map<String, String > param = new HashMap<>();
        param.put("emp_id", GetPrefs.getInstance().getSharedPref( this ).getUserId() );
        param.put("date", date );
        NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    Type listType = new TypeToken<ArrayList<WorkTodayModel>>(){}.getType();
                    ArrayList<WorkTodayModel> workTodayModelList = (ArrayList<WorkTodayModel> ) new Gson().fromJson( response, listType);
                    if ( workTodayModelList .size() > 0){
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList( getResources().getString(R.string.app_name ), workTodayModelList );
                        Intent intent = new Intent(SearchWithDateActivity.this, TodaysWorkActivity.class );
                        intent.putExtras( bundle );
                        startActivity( intent );
                        finish();
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
