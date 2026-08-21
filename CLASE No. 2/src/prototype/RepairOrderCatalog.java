package prototype;

import model.*;
import builder.ConcreteRepairOrderBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RepairOrderCatalog {
    private Map<String, RepairOrder> prototypes;

    public RepairOrderCatalog() {
        prototypes = new HashMap<>();
        loadPrototypes();
    }

    private void loadPrototypes() {
        // Prototype 1: Oil Change
        ConcreteRepairOrderBuilder b1 = new ConcreteRepairOrderBuilder();
        RepairOrder oilChange = b1
            .setDiagnosis("Scheduled oil and filter change")
            .setStatus("Template")
            .addPart(new Part("Synthetic Oil 5W-30", "Mobil", 35.0, 4, 6))
            .addPart(new Part("Oil Filter", "Mann", 12.0, 1, 6))
            .addPart(new Part("Drain Plug Washer", "Generic", 2.0, 1, 0))
            .addService(new Service("Oil Change", "Drain, filter change, refill", 25.0, 1.0))
            .addService(new Service("General Inspection", "Check fluid levels and leaks", 15.0, 0.5))
            .setNotes("Includes brake fluid, coolant, and steering fluid level checks.")
            .build();
        prototypes.put("Oil Change", oilChange);

        // Prototype 2: Brake Change
        ConcreteRepairOrderBuilder b2 = new ConcreteRepairOrderBuilder();
        RepairOrder brakeChange = b2
            .setDiagnosis("Front brake pads and rotors worn")
            .setStatus("Template")
            .addPart(new Part("Front Brake Pads", "Bosch", 45.0, 2, 12))
            .addPart(new Part("Front Brake Rotor", "Brembo", 85.0, 2, 12))
            .addPart(new Part("Brake Fluid DOT4", "Pentosin", 18.0, 1, 24))
            .addService(new Service("Brake Pad Replacement", "Remove and install brake pads", 40.0, 1.5))
            .addService(new Service("Brake Rotor Replacement", "Remove and install rotors", 50.0, 2.0))
            .addService(new Service("Brake Bleeding", "Bleed brake system", 30.0, 1.0))
            .setNotes("Check caliper wear. Includes brake balance.")
            .build();
        prototypes.put("Brake Change", brakeChange);

        // Prototype 3: Full Service
        ConcreteRepairOrderBuilder b3 = new ConcreteRepairOrderBuilder();
        RepairOrder fullService = b3
            .setDiagnosis("Complete preventive maintenance service")
            .setStatus("Template")
            .addPart(new Part("Synthetic Oil 5W-30", "Mobil", 35.0, 4, 6))
            .addPart(new Part("Oil Filter", "Mann", 12.0, 1, 6))
            .addPart(new Part("Air Filter", "K&N", 22.0, 1, 12))
            .addPart(new Part("Fuel Filter", "Bosch", 18.0, 1, 12))
            .addPart(new Part("Iridium Spark Plugs", "NGK", 15.0, 4, 24))
            .addPart(new Part("Front Brake Pads", "Bosch", 45.0, 2, 12))
            .addService(new Service("Oil & Filter Change", "Complete lubrication service", 30.0, 1.0))
            .addService(new Service("Spark Plug Replacement", "Replace 4 spark plugs", 35.0, 1.5))
            .addService(new Service("Brake Inspection", "Inspect and adjust brakes", 25.0, 1.0))
            .addService(new Service("Alignment & Balancing", "4-wheel alignment + balancing", 60.0, 2.0))
            .setNotes("Full service every 10,000 km. Includes engine wash.")
            .build();
        prototypes.put("Full Service", fullService);

        // Prototype 4: Alignment & Balancing
        ConcreteRepairOrderBuilder b4 = new ConcreteRepairOrderBuilder();
        RepairOrder alignment = b4
            .setDiagnosis("Vehicle pulls to one side, vibration above 50 mph")
            .setStatus("Template")
            .addPart(new Part("Wheel Weights", "Generic", 1.5, 8, 0))
            .addService(new Service("Computerized Alignment", "4-wheel alignment with camera", 45.0, 1.5))
            .addService(new Service("Wheel Balancing", "Electronic balancing 4 wheels", 30.0, 1.0))
            .addService(new Service("Suspension Inspection", "Check control arms, shocks", 20.0, 0.5))
            .setNotes("Check irregular tire wear. Recommend rotation if needed.")
            .build();
        prototypes.put("Alignment & Balancing", alignment);
    }

    public void addPrototype(String name, RepairOrder template) {
        prototypes.put(name, template);
    }

    public RepairOrder clone(String name) {
        RepairOrder prototype = prototypes.get(name);
        if (prototype == null) {
            return null;
        }
        return prototype.clone();
    }

    public Set<String> getPrototypeNames() {
        return prototypes.keySet();
    }

    public RepairOrder getPrototype(String name) {
        return prototypes.get(name);
    }
}