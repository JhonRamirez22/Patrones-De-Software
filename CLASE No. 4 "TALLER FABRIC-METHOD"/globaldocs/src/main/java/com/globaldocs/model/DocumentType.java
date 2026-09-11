package com.globaldocs.model;

public enum DocumentType {
    ELECTRONIC_INVOICE("Electronic Invoice", "Factura Electrónica"),
    LEGAL_CONTRACT("Legal Contract", "Contrato Legal"),
    FINANCIAL_REPORT("Financial Report", "Reporte Financiero"),
    DIGITAL_CERTIFICATE("Digital Certificate", "Certificado Digital"),
    TAX_DECLARATION("Tax Declaration", "Declaración Tributaria");

    private final String englishName;
    private final String spanishName;

    DocumentType(String englishName, String spanishName) {
        this.englishName = englishName;
        this.spanishName = spanishName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getSpanishName() {
        return spanishName;
    }
}
