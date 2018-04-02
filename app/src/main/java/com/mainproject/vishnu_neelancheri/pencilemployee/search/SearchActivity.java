package com.mainproject.vishnu_neelancheri.pencilemployee.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class SearchActivity extends AppCompatActivity {
    String url;
    String status;
    Map<String, String> param = new HashMap<>();
    EditText edtPhoneBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle bundle = getIntent().getExtras();
        /*url = bundle.getString(getResources().getString(R.string.app_name ));*/
        url = getIntent().getStringExtra(getResources().getString(R.string.app_name ));
        status = bundle.getString(getResources().getString(R.string.status ));

        edtPhoneBill  = findViewById( R.id.edt_phone_bill );
        findViewById( R.id.btn_submit ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }
    private void getData(){
        if ( url.isEmpty() || url == null ){
            PencilUtil.toaster(this, "Error, no data");
            finish();
        }else {
            String phoneBill = edtPhoneBill.getText().toString();
            if ( status.equals("phone")){
                url = PencilUtil.BASE_URL + "employee/search_by_phone";
                param.put("emp_id", GetPrefs.getInstance().getSharedPref(this).getUserId() );
                param.put("phone", phoneBill );
            }else {
                url = PencilUtil.BASE_URL + "employee/search_by_bill";
                param.put("emp_id", GetPrefs.getInstance().getSharedPref(this).getUserId() );
                param.put("bill", phoneBill );
            }
            NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
                @Override
                public void onSuccess(String response) {
                    try{
                        Type listType = new TypeToken<ArrayList<WorkTodayModel>>(){}.getType();
                        ArrayList<WorkTodayModel> workTodayModelList = (ArrayList<WorkTodayModel> ) new Gson().fromJson( response, listType);
                        if ( workTodayModelList .size() > 0){
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList( getResources().getString(R.string.app_name ), workTodayModelList );
                            Intent intent = new Intent(SearchActivity.this, TodaysWorkActivity.class );
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

                }
            });
        }

    }
}
