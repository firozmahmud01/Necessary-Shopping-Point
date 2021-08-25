package com.firoz.mahmud.necessaryshoppingpoint;

public class BuyerItem {
    public static String paymentTypebkash="Bkash";
    public static String paymentTypeCashOndelevary="CashOndelevary";
    private String id,productcatagory,productid
            ,bkashnumber,bkashid,username,useremail
            ,userphonenumber,useraddress,paymenttype;

    public BuyerItem(){

    }

    public BuyerItem(String id, String productcatagory, String productid, String bkashnumber,
                     String bkashid, String username, String useremail,
                     String userphonenumber, String useraddress, String paymenttype) {
        this.id = id;
        this.productcatagory = productcatagory;
        this.productid = productid;
        this.bkashnumber = bkashnumber;
        this.bkashid = bkashid;
        this.username = username;
        this.useremail = useremail;
        this.userphonenumber = userphonenumber;
        this.useraddress = useraddress;
        this.paymenttype = paymenttype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductcatagory() {
        return productcatagory;
    }

    public void setProductcatagory(String productcatagory) {
        this.productcatagory = productcatagory;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getBkashnumber() {
        return bkashnumber;
    }

    public void setBkashnumber(String bkashnumber) {
        this.bkashnumber = bkashnumber;
    }

    public String getBkashid() {
        return bkashid;
    }

    public void setBkashid(String bkashid) {
        this.bkashid = bkashid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphonenumber() {
        return userphonenumber;
    }

    public void setUserphonenumber(String userphonenumber) {
        this.userphonenumber = userphonenumber;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

}
