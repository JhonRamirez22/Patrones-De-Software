package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;
import com.globaldocs.processor.*;

import java.util.EnumSet;

public final class MexicoDocumentFactory extends DocumentProcessorFactory {

    public MexicoDocumentFactory() {
        super(Country.MEXICO,
                EnumSet.of(DocumentFormat.PDF, DocumentFormat.DOC, DocumentFormat.DOCX, DocumentFormat.TXT),
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
