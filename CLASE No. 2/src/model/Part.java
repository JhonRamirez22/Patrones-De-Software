package model;

public class Part {
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private int warrantyMonths;

    public Part() {}

    public Part(String name, String brand, double price, int quantity, int warrantyMonths) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.warrantyMonths = warrantyMonths;
    }

    public double getSubtotal() {
        return price * quantity;
    }

    public String getDescription() {
        return name + " (" + brand + ") x" + quantity +
               " - $" + String.format("%.2f", getSubtotal()) +
               " [Warranty: " + warrantyMonths + " months]";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }
}