package builder;

import model.*;

public class ConcreteRepairOrderBuilder implements RepairOrderBuilder {
    private RepairOrder order;

    public ConcreteRepairOrderBuilder() {
        this.order = new RepairOrder();
    }

    public void reset() {
        this.order = new RepairOrder();
    }

    @Override
    public RepairOrderBuilder setClient(Client client) {
        order.setClient(client);
        return this;
    }

    @Override
    public RepairOrderBuilder setVehicle(Vehicle vehicle) {
        order.setVehicle(vehicle);
        return this;
    }

    @Override
    public RepairOrderBuilder setMechanic(Mechanic mechanic) {
        order.setMechanic(mechanic);
        return this;
    }

    @Override
    public RepairOrderBuilder setEntryDate(String date) {
        order.setEntryDate(date);
        return this;
    }

    @Override
    public RepairOrderBuilder setEstimatedDeliveryDate(String date) {
        order.setEstimatedDeliveryDate(date);
        return this;
    }

    @Override
    public RepairOrderBuilder setDiagnosis(String diagnosis) {
        order.setDiagnosis(diagnosis);
        return this;
    }

    @Override
    public RepairOrderBuilder setStatus(String status) {
        order.setStatus(status);
        return this;
    }

    @Override
    public RepairOrderBuilder setNotes(String notes) {
        order.setNotes(notes);
        return this;
    }

    @Override
    public RepairOrderBuilder addPart(Part part) {
        order.addPart(part);
        return this;
    }

    @Override
    public RepairOrderBuilder addService(Service service) {
        order.addService(service);
        return this;
    }

    @Override
    public RepairOrder build() {
        order.calculateTotalCost();
        RepairOrder result = this.order;
        this.order = new RepairOrder();
        return result;
    }
}