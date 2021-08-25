package com.firoz.mahmud.necessaryshoppingpoint;

import java.util.ArrayList;
import java.util.List;

public class ProductCatagory {
    private String catagory;
    private List<ProductItem> productItem;

    public ProductCatagory(String catagory, List<ProductItem> productItem) {
        this.catagory = catagory;
        this.productItem = productItem;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public List<ProductItem> getProductItem() {
        return productItem;
    }

    public void setProductItem(List<ProductItem> productItem) {
        this.productItem = productItem;
    }
}
