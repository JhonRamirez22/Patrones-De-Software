package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;
import com.globaldocs.processor.*;

import java.util.EnumSet;
import java.util.Set;

public final class ColombiaDocumentFactory extends DocumentProcessorFactory {

    public ColombiaDocumentFactory() {
        super(Country.COLOMBIA,
                EnumSet.of(DocumentFormat.PDF, DocumentFormat.TXT, DocumentFormat.CSV, DocumentFormat.XLSX),
                EnumSet.allOf(DocumentType.class));
    }

    @Override
    protected DocumentProcessor createProcessorByType(DocumentType type, DocumentFormat format) {
        return switch (type) {
            case ELECTRONIC_INVOICE -> new ElectronicInvoiceProcessor(format, getCountry().getCode());
            case LEGAL_CONTRACT -> new LegalContractProcessor(format, getCountry().getCode());
            case FINANCIAL_REPORT -> new FinancialReportProcessor(format, getCountry().getCode());
            case DIGITAL_CERTIFICATE -> new DigitalCertificateProcessor(format, getCountry().getCode());
            case TAX_DECLARATION -> new TaxDeclarationProcessor(format, getCountry().getCode());
        };
    }
}
