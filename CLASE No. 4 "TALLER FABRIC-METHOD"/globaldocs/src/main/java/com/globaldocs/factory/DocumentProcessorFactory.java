package com.globaldocs.factory;

import com.globaldocs.exception.DocumentProcessingException;
import com.globaldocs.exception.UnsupportedDocumentTypeException;
import com.globaldocs.exception.UnsupportedFormatException;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;
import com.globaldocs.processor.DocumentProcessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public abstract class DocumentProcessorFactory {

    private final Country country;
    private final Set<DocumentFormat> supportedFormats;
    private final Set<DocumentType> supportedTypes;

    protected DocumentProcessorFactory(Country country, Set<DocumentFormat> supportedFormats,
                                       Set<DocumentType> supportedTypes) {
        this.country = country;
        this.supportedFormats = Collections.unmodifiableSet(
                supportedFormats != null ? EnumSet.copyOf(supportedFormats) : EnumSet.noneOf(DocumentFormat.class));
        this.supportedTypes = Collections.unmodifiableSet(
                supportedTypes != null ? EnumSet.copyOf(supportedTypes) : EnumSet.noneOf(DocumentType.class));
    }

    public final DocumentProcessor createProcessor(DocumentType type, DocumentFormat format) {
        if (type == null || format == null) {
            throw new IllegalArgumentException("DocumentType and DocumentFormat must not be null");
        }
        if (!supportedTypes.contains(type)) {
            throw new UnsupportedDocumentTypeException(type, country);
        }
        if (!supportedFormats.contains(format)) {
            throw new UnsupportedFormatException(format, country);
        }
        return createProcessorByType(type, format);
    }

    protected abstract DocumentProcessor createProcessorByType(DocumentType type, DocumentFormat format);

    public Country getCountry() {
        return country;
    }

    public Set<DocumentFormat> getSupportedFormats() {
        return supportedFormats;
    }

    public Set<DocumentType> getSupportedTypes() {
        return supportedTypes;
    }

    protected static Set<DocumentFormat> formats(DocumentFormat... formats) {
        return EnumSet.copyOf(Arrays.asList(formats));
    }

    protected static Set<DocumentType> types(DocumentType... types) {
        return EnumSet.copyOf(Arrays.asList(types));
    }
}
