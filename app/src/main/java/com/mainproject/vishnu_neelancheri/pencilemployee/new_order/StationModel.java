package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/4/2018
 */

public class StationModel {
    @SerializedName("station_id")
    private String stationId;
    @SerializedName("station_name")
    private String stationName;
    @SerializedName("station_code")
    private String stationCode;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
}
