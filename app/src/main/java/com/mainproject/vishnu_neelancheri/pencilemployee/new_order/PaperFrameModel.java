package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/4/2018
 */

public class PaperFrameModel {
    @SerializedName("status")
    private int status;
    @SerializedName("job_id")
    private String jobId;
    @SerializedName("paper")
    private List<PaperModel> paperModelList;
    @SerializedName("frame")
    private List<FrameModel> frameModelList;
    @SerializedName("station")
    private List<StationModel> stationModelList;

    public List<StationModel> getStationModelList() {
        return stationModelList;
    }

    public void setStationModelList(List<StationModel> stationModelList) {
        this.stationModelList = stationModelList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public List<PaperModel> getPaperModelList() {
        return paperModelList;
    }

    public void setPaperModelList(List<PaperModel> paperModelList) {
        this.paperModelList = paperModelList;
    }

    public List<FrameModel> getFrameModelList() {
        return frameModelList;
    }

    public void setFrameModelList(List<FrameModel> frameModelList) {
        this.frameModelList = frameModelList;
    }
}
