package com.example.mywebquizengine.Model;

public class OrderDetail {
    private String productName;
    private float subtotal;
    private float shipping;
    private float tax;
    private float total;

    public OrderDetail(String productName, String subtotal,
                       String shipping, String tax, String total) {
        this.productName = productName;
        this.subtotal = Float.parseFloat(subtotal);
        this.shipping = Float.parseFloat(shipping);
        this.tax = Float.parseFloat(tax);
        this.total = Float.parseFloat(total);
    }

    public String getProductName() {
        return productName;
    }

    public String getSubtotal() {
        //return String.format("%.2f", subtotal);
        return "" + subtotal;
    }

    public String getShipping() {
        //return String.format("%.2f", shipping);
        return "" + shipping;
    }

    public String getTax() {
        //return String.format("%.2f", tax);
        return "" + tax;
    }

    public String getTotal() {
        //return String.format("%.2f", total);
        return "" + total;
    }
}
