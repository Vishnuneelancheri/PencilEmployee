package com.mainproject.vishnu_neelancheri.pencilemployee.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 2/14/2018
 */

public class PencilUtil {
    public static final String BASE_URL = "http://192.168.1.7:8080/pencils_api/index.php/"
            /*"http://pencilcaricature.com/app_new/pencils_api/index.php/"*/
            /*"http://192.168.43.196:8080/pencils_api/index.php/"*/;

    public static void toaster(Context context, String message){
        Toast.makeText( context, message, Toast.LENGTH_LONG).show();
    }
}
