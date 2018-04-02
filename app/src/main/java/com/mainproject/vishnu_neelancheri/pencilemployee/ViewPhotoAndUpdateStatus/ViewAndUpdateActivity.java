package com.mainproject.vishnu_neelancheri.pencilemployee.ViewPhotoAndUpdateStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PrefModel;
import com.mainproject.vishnu_neelancheri.pencilemployee.work_today.WorkTodayModel;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAndUpdateActivity extends AppCompatActivity {
    private WorkTodayModel mWorkTodayModel;
    private Button btnUpdateStatus;
    private String updateUrl = "";
    private TextView txtShowBalance;
    private ImageView imgFirst, imgsecond, imgthird, imgFourth;
    private String status = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update);
        Bundle bundle = getIntent().getExtras();
        WorkTodayModel workTodayModel = bundle.getParcelable( getString(R.string.app_name));
        mWorkTodayModel = workTodayModel;
        btnUpdateStatus = findViewById( R.id.btn_update_status );
        txtShowBalance = findViewById( R.id.txt_balance );
        imgFirst = findViewById( R.id.imageView );
        imgsecond = findViewById( R.id.img_second );
        imgthird = findViewById( R.id.img_third );
        imgFourth = findViewById( R.id.img_fourth );
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeDrawing(updateUrl);
            }
        });
        txtShowBalance.setText( workTodayModel.getBalance() );
        getImage( workTodayModel );
    }
    public void getImage(WorkTodayModel workTodayModel){

        String balanceString = "Balance: "+ mWorkTodayModel.getBalance() ;
        if ( workTodayModel.getBillPaymentStatus() == 1 ){
            btnUpdateStatus.setText("Lock the work");
            updateUrl = "employee/complete_drawing";
            txtShowBalance.setText( balanceString );
            status = "lock";
        }
        else if ( workTodayModel.getBillPaymentStatus() == 2 ){
            btnUpdateStatus.setText("Complete drawing");
            updateUrl = "employee/complete_drawing";
            txtShowBalance.setText( balanceString );
            status = "complete";
        }
        else if ( workTodayModel.getBillPaymentStatus() == 3 ){
            btnUpdateStatus.setText("Balnce payment");
            updateUrl = "employee/final_payment";
            txtShowBalance.setText( balanceString );
        }
        else {
            btnUpdateStatus.setText("Completed");
            txtShowBalance.setText( "" );
        }
        final ImageView imageView = findViewById( R.id.imageView );
        String url = PencilUtil.BASE_URL +"employee/get_image";
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref(this);
        Map<String, String> params = new HashMap<>();
        params.put("emp_id", prefModel.getUserId() );
        params.put("bill_id", workTodayModel.getBillId() );
        params.put("token", prefModel.getToken() );
        params.put("status", status );
        NetWorkConnection.getInstance().volleyPost(url, params, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {

               try{
                   TempImageClass tempImageClass = new Gson().fromJson( response, TempImageClass.class );
                   String base=response+"6";
                   viewImage( tempImageClass.getImageArray() );
               }catch ( Exception e ){
                   Log.d("daf", e.toString() );
               }
                /*byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                PencilUtil.toaster(getApplicationContext(), Integer.toString(imageAsBytes.length)  ) ;
                imageView.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));*/
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    private void viewImage(String[] imageArray ){

        int i = 0;
        for ( String img : imageArray  ) {
            i++;
            if ( i == 1 ){
                imgFirst.setImageBitmap( getBitmap( img ));
            }
            else if ( i == 2 ){
                imgsecond.setImageBitmap( getBitmap( img ));
            }
            else if ( i == 3 ){
                imgthird.setImageBitmap( getBitmap( img ));
            }
            else if ( i == 4 ){
                imgFourth.setImageBitmap( getBitmap( img ));
            }
        }
    }
    private Bitmap getBitmap(String data){
         byte[] imageAsBytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
                PencilUtil.toaster(getApplicationContext(), Integer.toString(imageAsBytes.length)  ) ;
               return   BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
    private void completeDrawing(String uurl){
        String url = PencilUtil.BASE_URL + uurl;

        PrefModel prefModel = GetPrefs.getInstance().getSharedPref(this);
        Map<String, String> param = new HashMap<>();
        param.put("emp_id", GetPrefs.getInstance().getSharedPref(this).getUserId());
        param.put("bill_id", mWorkTodayModel.getBillId() );
        param.put("amount", mWorkTodayModel.getBalance());
        param.put("token", prefModel.getToken());
        param.put("emp_id", prefModel.getUserId() );
        param.put("status", status );
;        NetWorkConnection.getInstance().volleyPost(url,param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    ViewAndUpdateResponse viewAndUpdateResponse = new Gson().fromJson( response, ViewAndUpdateResponse.class );
                    PencilUtil.toaster( getApplicationContext(), viewAndUpdateResponse.getMessage());
                    finish();
                }catch (Exception e){

                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }
    private class TempImageClass{
        @SerializedName("img")
        private String[] imageArray;

        public String[] getImageArray() {
            return imageArray;
        }

        public void setImageArray(String[] imageArray) {
            this.imageArray = imageArray;
        }
    }
}
