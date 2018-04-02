package com.mainproject.vishnu_neelancheri.pencilemployee.work_today;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/7/2018
 */

public class WorkTodayModel implements Parcelable {
    @SerializedName("bill_id")
    private String billId;
    @SerializedName("order_received_emp_id")
    private String orderReceivedEmpId;
    @SerializedName("order_cust_name")
    private String custName;
    @SerializedName("order_cust_phone")
    private String custPhone;
    @SerializedName("frame_name")
    private String frameName;
    @SerializedName("paperport")
    private String paperPortrait;
    @SerializedName("papercart")
    private String paperCart;
    @SerializedName("order_courier_charge")
    private String courierCharge;
    @SerializedName("order_total_price")
    private String totalPrice;
    @SerializedName("bill_payment_status")
    private int billPaymentStatus;
    @SerializedName("balance")
    private String balance;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOrderReceivedEmpId() {
        return orderReceivedEmpId;
    }

    public void setOrderReceivedEmpId(String orderReceivedEmpId) {
        this.orderReceivedEmpId = orderReceivedEmpId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public String getPaperPortrait() {
        return paperPortrait;
    }

    public void setPaperPortrait(String paperPortrait) {
        this.paperPortrait = paperPortrait;
    }

    public String getPaperCart() {
        return paperCart;
    }

    public void setPaperCart(String paperCart) {
        this.paperCart = paperCart;
    }

    public String getCourierCharge() {
        return courierCharge;
    }

    public void setCourierCharge(String courierCharge) {
        this.courierCharge = courierCharge;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBillPaymentStatus() {
        return billPaymentStatus;
    }

    public void setBillPaymentStatus(int billPaymentStatus) {
        this.billPaymentStatus = billPaymentStatus;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.billId);
        dest.writeString(this.orderReceivedEmpId);
        dest.writeString(this.custName);
        dest.writeString(this.custPhone);
        dest.writeString(this.frameName);
        dest.writeString(this.paperPortrait);
        dest.writeString(this.paperCart);
        dest.writeString(this.courierCharge);
        dest.writeString(this.totalPrice);
        dest.writeInt(this.billPaymentStatus);
        dest.writeString(this.balance);
    }

    public WorkTodayModel() {
    }

    protected WorkTodayModel(Parcel in) {
        this.billId = in.readString();
        this.orderReceivedEmpId = in.readString();
        this.custName = in.readString();
        this.custPhone = in.readString();
        this.frameName = in.readString();
        this.paperPortrait = in.readString();
        this.paperCart = in.readString();
        this.courierCharge = in.readString();
        this.totalPrice = in.readString();
        this.billPaymentStatus = in.readInt();
        this.balance = in.readString();
    }

    public static final Parcelable.Creator<WorkTodayModel> CREATOR = new Parcelable.Creator<WorkTodayModel>() {
        @Override
        public WorkTodayModel createFromParcel(Parcel source) {
            return new WorkTodayModel(source);
        }

        @Override
        public WorkTodayModel[] newArray(int size) {
            return new WorkTodayModel[size];
        }
    };
}
