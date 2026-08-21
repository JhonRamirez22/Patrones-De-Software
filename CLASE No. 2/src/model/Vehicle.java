package model;

public class Vehicle {
    private String brand;
    private String model;
    private int year;
    private String licensePlate;
    private String color;
    private int mileage;
    private String engineType;

    public Vehicle() {}

    public Vehicle(String brand, String model, int year, String licensePlate,
                   String color, int mileage, String engineType) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.color = color;
        this.mileage = mileage;
        this.engineType = engineType;
    }

    public String getFullDescription() {
        return brand + " " + model + " " + year + " | Plate: " + licensePlate +
               " | Engine: " + engineType + " | Miles: " + mileage;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }
}