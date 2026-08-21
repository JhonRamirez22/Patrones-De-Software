package model;

public class Mechanic extends Person {
    private String specialty;
    private String level;
    private int yearsExperience;

    public Mechanic() { super(); }

    public Mechanic(String name, String documentId, String phone, String email,
                    String specialty, String level, int yearsExperience) {
        super(name, documentId, phone, email);
        this.specialty = specialty;
        this.level = level;
        this.yearsExperience = yearsExperience;
    }

    @Override
    public String getDescription() {
        return "Mechanic: " + name + " | Specialty: " + specialty + " | Level: " + level;
    }

    public double calculateHourlyRate() {
        double base = 30.0;
        if (level.equalsIgnoreCase("Senior")) base *= 1.5;
        else if (level.equalsIgnoreCase("Master")) base *= 2.0;
        return base + (yearsExperience * 2.0);
    }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public int getYearsExperience() { return yearsExperience; }
    public void setYearsExperience(int yearsExperience) { this.yearsExperience = yearsExperience; }
}