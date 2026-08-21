package builder;

import model.*;

public interface RepairOrderBuilder {
    RepairOrderBuilder setClient(Client client);
    RepairOrderBuilder setVehicle(Vehicle vehicle);
    RepairOrderBuilder setMechanic(Mechanic mechanic);
    RepairOrderBuilder setEntryDate(String date);
    RepairOrderBuilder setEstimatedDeliveryDate(String date);
    RepairOrderBuilder setDiagnosis(String diagnosis);
    RepairOrderBuilder setStatus(String status);
    RepairOrderBuilder setNotes(String notes);
    RepairOrderBuilder addPart(Part part);
    RepairOrderBuilder addService(Service service);
    RepairOrder build();
}