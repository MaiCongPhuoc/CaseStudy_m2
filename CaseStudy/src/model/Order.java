package model;

import java.time.Instant;

public class Order {
    private Long id;
    private String fullName;
    private String mobile;
    private String address;
//    private OrderStatus status;
    private Double grandTotal;
    private Instant createdAt;

    public Order() {

    }

    public Order(long id, String fullName, String mobile, String address) {
        this.id = id;
        this.fullName = fullName;
        this.mobile = mobile;
        this.address = address;
    }

    public static Order parseOrder(String record) {
        Order order = new Order();
        String[] field = record.split(",");
        order.id = Long.parseLong(field[0]);
        order.fullName = field[1];
        order.mobile = field[2];
        order.address = field[3];
        return order;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return address;
    }

    public void setEmail(String address) {
        this.address = address;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return id + "," + fullName + "," + mobile + "," + address;
    }
}
