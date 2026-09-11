package com.globaldocs.processor;

import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;

import java.util.Objects;

public final class ElectronicInvoiceProcessor implements DocumentProcessor {

    private final DocumentFormat format;
    private final String countryCode;

    public ElectronicInvoiceProcessor(DocumentFormat format, String countryCode) {
        this.format = Objects.requireNonNull(format, "format must not be null");
        this.countryCode = Objects.requireNonNull(countryCode, "countryCode must not be null");
    }

    @Override
    public String process(String fileName, String content) {
        Objects.requireNonNull(fileName, "fileName must not be null");
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("fileName must not be blank");
        }
        Objects.requireNonNull(content, "content must not be null");

        // TODO: countryCode is passed by the factory, but the "[CO:XX]" compliance prefix is hardcoded.
        //  Consider loading the compliance prefix from external configuration per country.
        return String.format("[CO:%s] Processed Electronic Invoice '%s' (%d bytes) | DIAN compliance: VALIDATED",
                countryCode, fileName, content.length());
    }

    @Override
    public DocumentType getType() {
        return DocumentType.ELECTRONIC_INVOICE;
    }

    @Override
    public DocumentFormat getFormat() {
        return format;
    }
}
