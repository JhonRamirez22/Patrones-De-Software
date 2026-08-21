package model;

public abstract class Person {
    protected String name;
    protected String documentId;
    protected String phone;
    protected String email;

    public Person() {}

    public Person(String name, String documentId, String phone, String email) {
        this.name = name;
        this.documentId = documentId;
        this.phone = phone;
        this.email = email;
    }

    public abstract String getDescription();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}