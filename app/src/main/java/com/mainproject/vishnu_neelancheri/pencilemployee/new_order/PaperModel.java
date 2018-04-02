package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/4/2018
 */

public class PaperModel {
    @SerializedName("paper_id")
    private int paperId;
    @SerializedName("paper_name")
    private String paperName;
    @SerializedName("id_paper_cartoon_price")
    private int cartoonId;
    @SerializedName("paper_cartoon_price")
    private int cartoonPrice;
    @SerializedName("id_paper_portrait_price")
    private int portraitId;
    @SerializedName("paper_portrait_price")
    private int portraitPrice;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(int cartoonId) {
        this.cartoonId = cartoonId;
    }

    public int getCartoonPrice() {
        return cartoonPrice;
    }

    public void setCartoonPrice(int cartoonPrice) {
        this.cartoonPrice = cartoonPrice;
    }

    public int getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(int portraitId) {
        this.portraitId = portraitId;
    }

    public int getPortraitPrice() {
        return portraitPrice;
    }

    public void setPortraitPrice(int portraitPrice) {
        this.portraitPrice = portraitPrice;
    }
}
