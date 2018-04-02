package com.mainproject.vishnu_neelancheri.pencilemployee.utils;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 2/20/2018
 */

public interface NetworkResponse {
    void onSuccess(String response);
    void onError(String errorMessage);
}
