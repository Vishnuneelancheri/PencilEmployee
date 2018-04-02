package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.home.HomeActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PrefModel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AddPhotoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String BILL_ID = "bill_id";
    private static final String JOB_ID="JOB_ID";
    private static final String IMG_COUNT = "current_img_count";
    private static final int CAMERA_REQUEST  = 100;
    private static final int GALLERY_REQUEST = 200;
    private int imageCount;
    private String billId, jobId;
    private ImageView imgFirst, imgSecond, imgThird, imgFourth, imgMain;
    private Button btnAddImage, btnSubmitImage;
    public static void RecieveParam(String billId, String currentImageCount , Context context, int jobId){
        Intent intent = new Intent( context, AddPhotoActivity.class );
        Bundle bundle = new Bundle();
        bundle.putString(BILL_ID, billId );
        bundle.putString(IMG_COUNT, currentImageCount );
        bundle.putString(JOB_ID, Integer.toString(jobId));
        intent.putExtras( bundle );
        context.startActivity( intent );

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Bundle bundle = getIntent().getExtras();
        billId = bundle.getString(BILL_ID);
        imageCount = Integer.parseInt(bundle.getString(IMG_COUNT));
        jobId = bundle.getString( JOB_ID );

        imgFirst = findViewById( R.id.img_first );
        imgSecond = findViewById( R.id.img_second );
        imgThird = findViewById( R.id.img_third );
        imgFourth = findViewById( R.id.img_fourth );
        imgMain = findViewById( R.id.img_main );

        btnSubmitImage = findViewById( R.id.btn_sbmt_image );
        btnAddImage = findViewById( R.id.btn_add_image );

        btnAddImage.setOnClickListener(this);
        btnSubmitImage.setOnClickListener(this);

        putLableToButtons();
    }
    private void putLableToButtons(){
        String submitBntLabel = "Submit";
        String addImageLabel = "Choose image";
        switch ( imageCount ){
            case 0:
                submitBntLabel = "Submit first image";
                addImageLabel = "Add first image";
                break;
            case 1:
                submitBntLabel =  "Submit second image";
                addImageLabel = "Add second image";
                break;
            case 2:
                submitBntLabel =  "Submit third image";
                addImageLabel = "Add third image";
                break;
            case 3:
                submitBntLabel =  "Submit fourth image";
                addImageLabel = "Add fourth image";
                break;
        }

        btnAddImage.setText(addImageLabel);
        btnSubmitImage.setText(submitBntLabel);
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ){
            case R.id.btn_add_image:
                addImage();
                break;
            case R.id.btn_sbmt_image:
                submitImage();
                break;
            default:
        }
    }
    public void submitImage(){
        if ( imageCount == 4 ){
            Intent intent = new Intent( AddPhotoActivity.this, HomeActivity.class );
            startActivity(intent);
            finish();
            return;
        }

        PrefModel prefModel = GetPrefs.getInstance().getSharedPref( this );
        try{
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgMain.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Map<String, String> param = new HashMap<>();
            param.put("image", getEncoded64ImageStringFromBitmap( bitmap ) );
            param.put("emp_id", prefModel.getUserId() );
            param.put("job_id", jobId );
            param.put("emp_token", prefModel.getToken() );
            param.put("img_name", billId+imageCount);
            param.put("img_count", Integer.toString(imageCount ));
            param.put("bill_id", billId );
            String url = PencilUtil.BASE_URL +  "Employee/add_image";
            NetWorkConnection.getInstance().volleyPost(url, param, this, new NetworkResponse() {
                @Override
                public void onSuccess(String response) {
                    imageCount++;
                    putLableToButtons();

                }

                @Override
                public void onError(String errorMessage) {
                    Log.d("d",errorMessage );
                }
            });
        }catch ( Exception e ){
            Log.d("d",e.toString());
        }
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }
    public void addImage(){
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
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK ){
            if ( requestCode == CAMERA_REQUEST ){

            }else if ( requestCode == GALLERY_REQUEST ){
                try{
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    imgMain.setImageBitmap(bitmap);
                    putImage( bitmap );
                }catch ( Exception e ){
                    Log.d("a", e.toString() );
                }
            }
        }
    }

    private void putImage(Bitmap bitmap ){
       /* switch ( imageCount ){
            case 0:
                imgFirst.setImageBitmap( bitmap );
                break;
            case 1:
                imgSecond.setImageBitmap( bitmap );
                break;
            case 2:
                imgThird.setImageBitmap( bitmap );
                break;
            case 3:
                imgFourth.setImageBitmap( bitmap );
                break;

        }*/
    }
}
