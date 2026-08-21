package model;

public class Client extends Person {
    private String address;
    private int totalRepairs;

    public Client() { super(); }

    public Client(String name, String documentId, String phone, String email, String address) {
        super(name, documentId, phone, email);
        this.address = address;
        this.totalRepairs = 0;
    }

    @Override
    public String getDescription() {
        return "Client: " + name + " | ID: " + documentId + " | Phone: " + phone;
    }

    public void incrementRepairs() { totalRepairs++; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getTotalRepairs() { return totalRepairs; }
}