package model;

import java.util.ArrayList;
import java.util.List;

public class RepairOrder implements Cloneable {
    private static int counter = 0;

    private int id;
    private String entryDate;
    private String estimatedDeliveryDate;
    private Client client;
    private Vehicle vehicle;
    private Mechanic mechanic;
    private List<Part> parts;
    private List<Service> services;
    private String diagnosis;
    private String status;
    private double totalCost;
    private String notes;

    public RepairOrder() {
        this.id = ++counter;
        this.parts = new ArrayList<>();
        this.services = new ArrayList<>();
        this.status = "Pending";
        this.notes = "";
    }

    public void calculateTotalCost() {
        double total = 0;
        for (Part p : parts) {
            total += p.getSubtotal();
        }
        for (Service s : services) {
            total += s.getLaborCost();
        }
        this.totalCost = total;
    }

    public double getTotalPartsCost() {
        double total = 0;
        for (Part p : parts) total += p.getSubtotal();
        return total;
    }

    public double getTotalServicesCost() {
        double total = 0;
        for (Service s : services) total += s.getLaborCost();
        return total;
    }

    public double getTotalEstimatedHours() {
        double total = 0;
        for (Service s : services) total += s.getDurationHours();
        return total;
    }

    public void addPart(Part part) {
        parts.add(part);
    }

    public void removePart(int index) {
        if (index >= 0 && index < parts.size()) {
            parts.remove(index);
        }
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(int index) {
        if (index >= 0 && index < services.size()) {
            services.remove(index);
        }
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════\n");
        sb.append("  REPAIR ORDER #").append(id).append("\n");
        sb.append("═══════════════════════════════════════════\n");
        sb.append("  Status: ").append(status).append("\n");
        sb.append("  Entry Date: ").append(entryDate).append("\n");
        sb.append("  Est. Delivery: ").append(estimatedDeliveryDate).append("\n\n");

        if (client != null) {
            sb.append("  CLIENT:\n");
            sb.append("    ").append(client.getName()).append("\n");
            sb.append("    ID: ").append(client.getDocumentId()).append("\n");
            sb.append("    Phone: ").append(client.getPhone()).append("\n\n");
        }

        if (vehicle != null) {
            sb.append("  VEHICLE:\n");
            sb.append("    ").append(vehicle.getFullDescription()).append("\n\n");
        }

        if (mechanic != null) {
            sb.append("  MECHANIC:\n");
            sb.append("    ").append(mechanic.getName()).append("\n");
            sb.append("    Specialty: ").append(mechanic.getSpecialty()).append("\n");
            sb.append("    Level: ").append(mechanic.getLevel()).append("\n\n");
        }

        sb.append("  DIAGNOSIS:\n    ").append(diagnosis).append("\n\n");

        if (!parts.isEmpty()) {
            sb.append("  PARTS:\n");
            for (Part p : parts) {
                sb.append("    • ").append(p.getDescription()).append("\n");
            }
            sb.append("    Parts subtotal: $").append(String.format("%.2f", getTotalPartsCost())).append("\n\n");
        }

        if (!services.isEmpty()) {
            sb.append("  SERVICES:\n");
            for (Service s : services) {
                sb.append("    • ").append(s.getDescription()).append("\n");
            }
            sb.append("    Services subtotal: $").append(String.format("%.2f", getTotalServicesCost())).append("\n");
            sb.append("    Est. hours: ").append(String.format("%.1f", getTotalEstimatedHours())).append("h\n\n");
        }

        sb.append("  ─────────────────────────────────────────\n");
        sb.append("  TOTAL COST: $").append(String.format("%.2f", totalCost)).append("\n");
        sb.append("═══════════════════════════════════════════\n");

        if (notes != null && !notes.isEmpty()) {
            sb.append("  NOTES: ").append(notes).append("\n");
        }

        return sb.toString();
    }

    @Override
    public RepairOrder clone() {
        try {
            RepairOrder clone = (RepairOrder) super.clone();
            clone.id = ++counter;
            clone.parts = new ArrayList<>(this.parts);
            clone.services = new ArrayList<>(this.services);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error cloning order", e);
        }
    }

    public int getId() { return id; }
    public String getEntryDate() { return entryDate; }
    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }
    public String getEstimatedDeliveryDate() { return estimatedDeliveryDate; }
    public void setEstimatedDeliveryDate(String date) { this.estimatedDeliveryDate = date; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Mechanic getMechanic() { return mechanic; }
    public void setMechanic(Mechanic mechanic) { this.mechanic = mechanic; }
    public List<Part> getParts() { return parts; }
    public List<Service> getServices() { return services; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}