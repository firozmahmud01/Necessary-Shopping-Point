package com.firoz.mahmud.necessaryshoppingpoint;

import java.util.ArrayList;

public class ProductItem {
    private String id,catagory,productname,productprize,productdescription,themlink;
    ArrayList<String>imagelinks;
    private int rating;

    public ProductItem(){

    }

    public ProductItem(String id, String catagory, String productname,
                       String productprize, String productdescription,
                       String themlink, ArrayList<String> imagelinks,
                       int rating) {
        this.id = id;
        this.catagory = catagory;
        this.productname = productname;
        this.productprize = productprize;
        this.productdescription = productdescription;
        this.themlink = themlink;
        this.imagelinks = imagelinks;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprize() {
        return productprize;
    }

    public void setProductprize(String productprize) {
        this.productprize = productprize;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getThemlink() {
        return themlink;
    }

    public void setThemlink(String themlink) {
        this.themlink = themlink;
    }

    public ArrayList<String> getImagelinks() {
        return imagelinks;
    }

    public void setImagelinks(ArrayList<String> imagelinks) {
        this.imagelinks = imagelinks;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
