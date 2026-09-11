package com.globaldocs.exception;

import com.globaldocs.model.DocumentType;
import com.globaldocs.model.Country;

public class UnsupportedDocumentTypeException extends DocumentProcessingException {

    private final DocumentType documentType;
    private final Country country;

    public UnsupportedDocumentTypeException(DocumentType documentType, Country country) {
        super(String.format("Document type '%s' is not supported in %s", documentType.getEnglishName(), country.getName()),
                null, country.getCode());
        this.documentType = documentType;
        this.country = country;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public Country getCountry() {
        return country;
    }
}
