package com.globaldocs.exception;

import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.Country;

public class UnsupportedFormatException extends DocumentProcessingException {

    private final DocumentFormat format;
    private final Country country;

    public UnsupportedFormatException(DocumentFormat format, Country country) {
        super(String.format("Format '%s' is not supported in %s", format.getExtension(), country.getName()),
                null, country.getCode());
        this.format = format;
        this.country = country;
    }

    public DocumentFormat getFormat() {
        return format;
    }

    public Country getCountry() {
        return country;
    }
}
