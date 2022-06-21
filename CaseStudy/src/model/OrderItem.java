package model;

public class OrderItem {
    private Long id;
    private double price;
    private int quantity;
    private long orderId;
    private int productId;
    private String productName;
    private double total;
    private double grandTotal;

    public OrderItem(long id, double price, int quantity, long orderId, int productId, String productName, double total) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.total = total;
    }

    public OrderItem() {

    }

    // doi mot chuoi trong file thanh mang
    public static OrderItem parseOrderItem(String record) {
        OrderItem orderItem = new OrderItem();
        String[] fields = record.split(",");
        orderItem.id = Long.parseLong(fields[0]);
        orderItem.price = Double.parseDouble(fields[1]);
        orderItem.quantity = Integer.parseInt(fields[2]);
        orderItem.orderId = Long.parseLong(fields[3]);
        orderItem.productId = Integer.parseInt(fields[4]);
        orderItem.productName = fields[5];
        orderItem.total = Double.parseDouble(fields[6]);
        orderItem.grandTotal = Double.parseDouble(fields[7]);
        return orderItem ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }
    public double getTotal(){
        return price * quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getGrandTotal() {
        return grandTotal ;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return id + "," + price + "," + quantity + "," + orderId + "," + productId + "," + productName+","+total+","+grandTotal;
    }
}
