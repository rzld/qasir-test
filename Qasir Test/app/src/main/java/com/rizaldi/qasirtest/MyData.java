package com.rizaldi.qasirtest;

public class MyData {

    private int product_id, price, stock;
    private String product_name, description, img_thumb, img_large;
    //private images image;


    public MyData(int product_id, int price, int stock, String product_name, String description, String img_thumb, String img_large) {
        this.product_id = product_id;
        this.price = price;
        this.stock = stock;
        this.product_name = product_name;
        this.description = description;
        this.img_thumb = img_thumb;
        this.img_large = img_large;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_thumb() {
        return img_thumb;
    }

    public void setImg_thumb(String img_thumb) {
        this.img_thumb = img_thumb;
    }

    public String getImg_large() {
        return img_large;
    }

    public void setImg_large(String img_large) {
        this.img_large = img_large;
    }
}