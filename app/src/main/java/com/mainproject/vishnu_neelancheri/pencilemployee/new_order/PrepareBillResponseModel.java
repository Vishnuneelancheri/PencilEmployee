package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/6/2018
 */

public class PrepareBillResponseModel {
    @SerializedName("total")
    private String total;
    @SerializedName("bill_id")
    private String billId;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("job_id")
    private String jobId;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
