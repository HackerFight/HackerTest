package com.hacker.jedis.entrty;

/**
 * @author Hacker
 * @date：2018/12/7
 * @project project
 * @describe
 */
public class Car {

    private String brand;

    private String price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Car(String brand, String price) {
        this.brand = brand;
        this.price = price;
    }

    public Car() {
    }
}
