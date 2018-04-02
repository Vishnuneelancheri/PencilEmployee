package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;


import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PrefModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewOrderActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioGroup mRadioGroup;
    private RadioButton mRadioPortrait, mRadioCartoon;
    private Spinner mSpinnerPaper, mSpinnerFrame, mSpinnerDeliveryPoint;
    private EditText mEdtCourierCharge, mEdtMobilePhone, mEdtName, mEdtNumberofCopies, mEdtNumberOfFace;
    private CheckBox mCheckChangeDeliveryPoint;
    private Button   mBtnSubmit;
    private PaperFrameModel mPaperFrameModel;
    private List<PaperModel> paperModelList;
    private List<FrameModel> frameModelList;
    private List<StationModel> stationModelList;
    private boolean isPortr = true;
    private LinearLayout linearAmountContainer;
    private TextView txtTotal;
    private ScrollView scrollView;
    private PrepareBillResponseModel prepareBillResponseModel;
    private EditText edtTxtadvance;
    private String billId, jobId, totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        mRadioGroup = findViewById( R.id.radio_group_portrait_cartoon );
        mRadioCartoon = findViewById( R.id.radio_cartoon );
        mRadioPortrait = findViewById( R.id.radio_portrait );
        mSpinnerPaper = findViewById( R.id.spinner_paper );
        mSpinnerFrame = findViewById( R.id.spinner_frame );
        mSpinnerDeliveryPoint = findViewById( R.id.spinner_delivery_point );
        mEdtCourierCharge = findViewById( R.id.edt_txt_courier );
        mEdtMobilePhone = findViewById( R.id.edt_phone_mobile );
        mEdtName = findViewById( R.id.edt_name );
        edtTxtadvance = findViewById( R.id.edt_advance );
        mEdtNumberofCopies = findViewById( R.id.edt_num_copy );
        mEdtNumberOfFace = findViewById( R.id.edt_num_face );

        mCheckChangeDeliveryPoint = findViewById( R.id.check_change_delivery_point );

        mBtnSubmit = findViewById( R.id.btn_submit );
        scrollView = findViewById( R.id.scrollview );
        mBtnSubmit.setOnClickListener( this );


        mRadioPortrait.setChecked( true );
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if ( i == R.id.radio_portrait )
                    isPortr = true;
                else
                    isPortr = false;
                setUpPaperSpinner( paperModelList, isPortr );
            }
        });
        mCheckChangeDeliveryPoint.setOnClickListener( this );
        mSpinnerDeliveryPoint.setVisibility( View.GONE );
        linearAmountContainer = findViewById( R.id.linear_amount_container );
        linearAmountContainer.setVisibility(View.GONE);
        txtTotal = findViewById( R.id.txt_total );
        initalSetupment();
        edtTxtadvance.setVisibility(View.GONE);
    }
    @Override
    public void onClick( View view ){
        switch ( view.getId() ){

            case R.id.btn_submit:
                prepareBill();
                break;

            case R.id.check_change_delivery_point:{
                if ( ( (CheckBox) view ).isChecked() ){
                    mSpinnerDeliveryPoint.setVisibility(View.VISIBLE);
                }else
                    mSpinnerDeliveryPoint.setVisibility(View.GONE);
            }
                break;
            default:break;
        }
    }
    /*private void addPhoto(){
        CharSequence colors[] = new CharSequence[] { "Camera", "Gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select image from...");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( which == 0 ){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else {
                    Intent intent = new Intent();
                    intent.setType("image*//*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST  );
                }
            }
        });
        builder.show();
    }*/
    private void prepareBill(){
        String url = PencilUtil.BASE_URL + "employee/prepare_bill";
        Map< String, String > param = prepareParam();
        NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    prepareBillResponseModel = new Gson().fromJson( response, PrepareBillResponseModel.class);
                    if ( prepareBillResponseModel.getStatus() == 1 ){

                        billId = prepareBillResponseModel.getBillId();
                        jobId = prepareBillResponseModel.getJobId();
                        totalAmount = prepareBillResponseModel.getTotal();
                        edtTxtadvance.setVisibility(View.VISIBLE);
                        mBtnSubmit.setText("Proceed");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        EntereAdvanceFragment entereAdvanceFragment = EntereAdvanceFragment.newInstance( prepareBillResponseModel
                        .getTotal());
                        entereAdvanceFragment.setCancelable(false);
                        entereAdvanceFragment.show( fragmentManager, "Dialog");
                    }
                }catch ( Exception e ){

                }
            }

            @Override
            public void onError(String errorMessage) {
                String d = errorMessage;
            }
        });
    }
    private  Map< String, String > prepareParam(){
        Map< String, String > param = new HashMap<>();
        String name = mEdtName.getText().toString();
        String phone = mEdtMobilePhone.getText().toString();
        String portrId;
        String cartoonId;
        String token;
        String empId;
        String framePriceId;
        String stationId;
        String courierCharge;
        int paperPosition = mSpinnerPaper.getSelectedItemPosition();
        if ( isPortr ){
            portrId = Integer.toString( paperModelList.get(paperPosition).getPortraitId());
            cartoonId = "0";
        }else {
            cartoonId = Integer.toString( paperModelList.get(paperPosition).getCartoonId());
            portrId = "0";
        }
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref( this );
        token = prefModel.getToken();
        empId = prefModel.getUserId();
        int framePricePosition = mSpinnerFrame.getSelectedItemPosition();
        if ( framePricePosition == 0 )
            framePriceId = "0";
        else{
            framePriceId = frameModelList.get( framePricePosition ).getFramePriceId();
        }
        if ( mCheckChangeDeliveryPoint.isChecked() ){
            stationId = stationModelList.get( mSpinnerDeliveryPoint.getSelectedItemPosition() ).getStationId();
        }else {
            stationId = "0";
        }
        courierCharge = mEdtCourierCharge.getText().toString();

        String numFace = mEdtNumberOfFace.getText().toString();
        String numCopies = mEdtNumberofCopies.getText().toString();
        if ( numFace.isEmpty() )
            numFace = "0";
        if ( numCopies.isEmpty() )
            numCopies = "0";

        if ( courierCharge.isEmpty() )
            courierCharge = "0";
        param.put("emp_id", empId );
        param.put("job_id", mPaperFrameModel.getJobId() );
        param.put("emp_token", token );
        param.put("user_name", name );
        param.put("user_phone", phone );
        param.put("portrait_id", portrId );
        param.put("cartoon_id", cartoonId );
        param.put("frame_price_id", framePriceId );
        param.put("courier_charge", courierCharge );
        param.put("delivery_point", stationId );
        param.put("number_of_image","1");
        param.put("number_of_face", numFace );
        param.put("number_of_copies", numCopies );
        return param;
    }



    private void initalSetupment(){
        Map< String, String > param = new HashMap<>();
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref( NewOrderActivity.this );
        param.put("emp_id", prefModel.getUserId() );
        param.put("emp_token", prefModel.getToken());
        String url = PencilUtil.BASE_URL + "employee/get_all_frame_paper";

        NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    mPaperFrameModel = new Gson().fromJson( response, PaperFrameModel.class );
                    if ( mPaperFrameModel.getStatus() == 1 ){
                        paperModelList = mPaperFrameModel.getPaperModelList();
                        frameModelList = mPaperFrameModel.getFrameModelList();
                        stationModelList = mPaperFrameModel.getStationModelList();
                        setUpPaperSpinner( mPaperFrameModel.getPaperModelList(), true );
                        setUpFrameList( frameModelList );
                        setUpStation( stationModelList );

                    }else {
                        PencilUtil.toaster(NewOrderActivity.this, "uer Error");
                    }
                }catch (Exception e ){
                    PencilUtil.toaster(NewOrderActivity.this, "Error");
                    finish();
                }
            }

            @Override
            public void onError(String errorMessage) {
                PencilUtil.toaster(NewOrderActivity.this, "Network Error");
                finish();
            }
        });
    }
    private void setUpPaperSpinner(List<PaperModel> list, boolean isPortr){
        List<String> tempList = new ArrayList<>();
        for (PaperModel paper: list
             ) {
            if ( isPortr ){
                tempList.add( paper.getPaperName()+"/"+paper.getPortraitPrice()+"/");
            }
            else {
                tempList.add( paper.getPaperName()+"/"+paper.getCartoonPrice()+"/");
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tempList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPaper.setAdapter( adapter );
    }
    private void setUpFrameList(List<FrameModel> list){
        List<String> tempList = new ArrayList<>();
        for (FrameModel frameModel: list
                ) {
            tempList.add( frameModel.getFrameName()+"/"+frameModel.getFramePrice());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter< >(this, android.R.layout.simple_spinner_item, tempList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerFrame.setAdapter(adapter);
    }
    private void setUpStation(List<StationModel> list){
        List<String> tempList = new ArrayList<>();
        for (StationModel stationModel: list
                ) {
            tempList.add( stationModel.getStationName()+"/"+stationModel.getStationCode());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter< >(this, android.R.layout.simple_spinner_item, tempList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDeliveryPoint.setAdapter(adapter);
    }

    public void advancePayment( String advance ){
        Map<String,String> param = new HashMap<>();
        param.put("job_id", jobId );
        param.put("amount", advance );
        param.put("bill_id", billId );
        PrefModel prefModel = GetPrefs.getInstance().getSharedPref( getApplicationContext() );
        param.put("emp_id", prefModel.getUserId() );
        param.put("emp_token", prefModel.getToken() );
        String url = PencilUtil.BASE_URL + "Employee/first_payment";
        NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    PrepareBillResponseModel firstPaymentModel = new Gson().fromJson( response, PrepareBillResponseModel.class );
                    if ( firstPaymentModel.getStatus() > 0 ){
                        finish();
                        AddPhotoActivity.RecieveParam( firstPaymentModel.getBillId(), "0",
                                NewOrderActivity.this, firstPaymentModel.getStatus() );

                    }else {
                        PencilUtil.toaster(getApplicationContext(), "Network error  please try again ");
                    }
                }catch ( Exception e ){

                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("df", errorMessage );
            }
        });
    }
}
