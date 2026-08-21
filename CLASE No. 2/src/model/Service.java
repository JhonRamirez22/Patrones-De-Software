package model;

public class Service {
    private String name;
    private String description;
    private double laborCost;
    private double durationHours;

    public Service() {}

    public Service(String name, String description, double laborCost, double durationHours) {
        this.name = name;
        this.description = description;
        this.laborCost = laborCost;
        this.durationHours = durationHours;
    }

    public String getDescription() {
        return name + " (" + durationHours + "h) - $" +
               String.format("%.2f", laborCost);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescriptionDetail() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getLaborCost() { return laborCost; }
    public void setLaborCost(double laborCost) { this.laborCost = laborCost; }
    public double getDurationHours() { return durationHours; }
    public void setDurationHours(double durationHours) { this.durationHours = durationHours; }
}