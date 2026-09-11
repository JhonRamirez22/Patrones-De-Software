package com.globaldocs.model;

public enum Country {
    COLOMBIA("Colombia", "CO"),
    MEXICO("Mexico", "MX"),
    ARGENTINA("Argentina", "AR"),
    CHILE("Chile", "CL");

    private final String name;
    private final String code;

    Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
