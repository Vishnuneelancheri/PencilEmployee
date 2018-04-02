package com.mainproject.vishnu_neelancheri.pencilemployee.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.home.HomeActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetWorkConnection;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.NetworkResponse;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById( R.id.btn_submit ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }
    private void login(){
        String url = PencilUtil.BASE_URL + "employee/login";

        EditText edtTxtPhone = findViewById( R.id.edt_txt_phone );
        EditText edtTxtPwd = findViewById( R.id.edt_txt_password );

        String phone = edtTxtPhone.getText().toString();
        String passwrd = edtTxtPwd.getText().toString();

        Map<String, String> params = new HashMap<>();
        params.put("user_name", phone );
        params.put("password", passwrd );

        NetWorkConnection.getInstance().volleyPost(url, params, this, new NetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try{
                    LoginModel loginModel = new Gson().fromJson( response, LoginModel.class );
                    if ( loginModel.getStatus() == 1 ){

                        SharedPreferences sharedPreferences = getApplicationContext()
                                .getSharedPreferences(getResources().getString( R.string.app_name), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString( getResources().getString(R.string.token), loginModel.getToken() );
                        editor.putString( getResources().getString(R.string.user_id), loginModel.getEmployeeId() );
                        if (editor.commit()){
                            Intent intent = new Intent( LoginActivity.this, HomeActivity.class );
                            startActivity( intent );
                            finish();
                        }
                    }
                    PencilUtil.toaster( LoginActivity.this, loginModel.getMessage());
                }catch (Exception e) {}
            }

            @Override
            public void onError(String errorMessage) {
                PencilUtil.toaster( LoginActivity.this, "Failed");
            }
        });
    }
}
