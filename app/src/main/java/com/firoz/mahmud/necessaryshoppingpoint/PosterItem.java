package com.firoz.mahmud.necessaryshoppingpoint;

public class PosterItem {
    private String id,picurl,catgory,productid;
    public PosterItem(){

    }

    public PosterItem(String id, String picurl, String catgory, String productid) {
        this.id = id;
        this.picurl = picurl;
        this.catgory = catgory;
        this.productid = productid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getCatgory() {
        return catgory;
    }

    public void setCatgory(String catgory) {
        this.catgory = catgory;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
