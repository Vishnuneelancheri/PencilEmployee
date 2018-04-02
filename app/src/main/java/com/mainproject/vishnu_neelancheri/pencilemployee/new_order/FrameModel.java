package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/4/2018
 */

public class FrameModel {
    @SerializedName("frame_name")
    private String frameName;
    @SerializedName("frame_price")
    private int framePrice;
    @SerializedName("frame_price_id")
    private String framePriceId;

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public int getFramePrice() {
        return framePrice;
    }

    public void setFramePrice(int framePrice) {
        this.framePrice = framePrice;
    }

    public String getFramePriceId() {
        return framePriceId;
    }

    public void setFramePriceId(String framePriceId) {
        this.framePriceId = framePriceId;
    }
}
