package com.globaldocs.factory;

import com.globaldocs.exception.UnsupportedDocumentTypeException;
import com.globaldocs.exception.UnsupportedFormatException;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;
import com.globaldocs.processor.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentProcessorFactoryTest {

    // === Colombia ===

    @Test
    void colombiaFactoryCreatesElectronicInvoiceProcessor() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.ELECTRONIC_INVOICE, DocumentFormat.PDF);
        assertInstanceOf(ElectronicInvoiceProcessor.class, processor);
        assertTrue(processor.process("test.pdf", "data").contains("[CO:"));
    }

    @Test
    void colombiaFactoryCreatesLegalContractProcessor() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.LEGAL_CONTRACT, DocumentFormat.TXT);
        assertInstanceOf(LegalContractProcessor.class, processor);
    }

    @Test
    void colombiaFactoryCreatesFinancialReportProcessor() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.FINANCIAL_REPORT, DocumentFormat.CSV);
        assertInstanceOf(FinancialReportProcessor.class, processor);
    }

    @Test
    void colombiaFactoryCreatesDigitalCertificateProcessor() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.DIGITAL_CERTIFICATE, DocumentFormat.PDF);
        assertInstanceOf(DigitalCertificateProcessor.class, processor);
    }

    @Test
    void colombiaFactoryCreatesTaxDeclarationProcessor() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.TAX_DECLARATION, DocumentFormat.XLSX);
        assertInstanceOf(TaxDeclarationProcessor.class, processor);
    }

    @Test
    void colombiaFactoryRejectsUnsupportedFormat() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        assertThrows(UnsupportedFormatException.class, () ->
                factory.createProcessor(DocumentType.ELECTRONIC_INVOICE, DocumentFormat.DOC));
    }

    @Test
    void colombiaFactoryRejectsUnsupportedFormatDocx() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        assertThrows(UnsupportedFormatException.class, () ->
                factory.createProcessor(DocumentType.LEGAL_CONTRACT, DocumentFormat.DOCX));
    }

    // === Mexico ===

    @Test
    void mexicoFactoryCreatesElectronicInvoiceProcessor() {
        DocumentProcessorFactory factory = new MexicoDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.ELECTRONIC_INVOICE, DocumentFormat.PDF);
        assertInstanceOf(ElectronicInvoiceProcessor.class, processor);
        assertTrue(processor.process("factura.pdf", "data").contains("[CO:MX]"));
    }

    @Test
    void mexicoFactoryCreatesLegalContractProcessor() {
        DocumentProcessorFactory factory = new MexicoDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.LEGAL_CONTRACT, DocumentFormat.DOCX);
        assertInstanceOf(LegalContractProcessor.class, processor);
    }

    @Test
    void mexicoFactoryRejectsUnsupportedFormatCsv() {
        DocumentProcessorFactory factory = new MexicoDocumentFactory();
        assertThrows(UnsupportedFormatException.class, () ->
                factory.createProcessor(DocumentType.FINANCIAL_REPORT, DocumentFormat.CSV));
    }

    // === Argentina ===

    @Test
    void argentinaFactoryCreatesFinancialReportProcessor() {
        DocumentProcessorFactory factory = new ArgentinaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.FINANCIAL_REPORT, DocumentFormat.XLSX);
        assertInstanceOf(FinancialReportProcessor.class, processor);
        assertTrue(processor.process("report.xlsx", "data").contains("[CO:AR]"));
    }

    @Test
    void argentinaFactoryCreatesTaxDeclarationProcessor() {
        DocumentProcessorFactory factory = new ArgentinaDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.TAX_DECLARATION, DocumentFormat.CSV);
        assertInstanceOf(TaxDeclarationProcessor.class, processor);
    }

    @Test
    void argentinaFactoryRejectsUnsupportedFormatDoc() {
        DocumentProcessorFactory factory = new ArgentinaDocumentFactory();
        assertThrows(UnsupportedFormatException.class, () ->
                factory.createProcessor(DocumentType.LEGAL_CONTRACT, DocumentFormat.DOC));
    }

    // === Chile ===

    @Test
    void chileFactoryCreatesElectronicInvoiceProcessor() {
        DocumentProcessorFactory factory = new ChileDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.ELECTRONIC_INVOICE, DocumentFormat.PDF);
        assertInstanceOf(ElectronicInvoiceProcessor.class, processor);
        assertTrue(processor.process("boleta.pdf", "data").contains("[CO:CL]"));
    }

    @Test
    void chileFactoryCreatesDigitalCertificateProcessor() {
        DocumentProcessorFactory factory = new ChileDocumentFactory();
        DocumentProcessor processor = factory.createProcessor(
                DocumentType.DIGITAL_CERTIFICATE, DocumentFormat.DOC);
        assertInstanceOf(DigitalCertificateProcessor.class, processor);
    }

    @Test
    void chileFactoryRejectsUnsupportedFormatXlsx() {
        DocumentProcessorFactory factory = new ChileDocumentFactory();
        assertThrows(UnsupportedFormatException.class, () ->
                factory.createProcessor(DocumentType.FINANCIAL_REPORT, DocumentFormat.XLSX));
    }

    // === Null handling ===

    @Test
    void factoryRejectsNullType() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        assertThrows(IllegalArgumentException.class, () ->
                factory.createProcessor(null, DocumentFormat.PDF));
    }

    @Test
    void factoryRejectsNullFormat() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        assertThrows(IllegalArgumentException.class, () ->
                factory.createProcessor(DocumentType.ELECTRONIC_INVOICE, null));
    }

    @Test
    void factoryRejectsBothNull() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        assertThrows(IllegalArgumentException.class, () ->
                factory.createProcessor(null, null));
    }

    // === Factory metadata ===

    @Test
    void colombiaFactoryReturnsCorrectCountry() {
        DocumentProcessorFactory factory = new ColombiaDocumentFactory();
        assertEquals(Country.COLOMBIA, factory.getCountry());
    }

    @Test
    void mexicoFactoryReturnsCorrectCountry() {
        DocumentProcessorFactory factory = new MexicoDocumentFactory();
        assertEquals(Country.MEXICO, factory.getCountry());
    }

    @Test
    void argentinaFactoryReturnsCorrectCountry() {
        DocumentProcessorFactory factory = new ArgentinaDocumentFactory();
        assertEquals(Country.ARGENTINA, factory.getCountry());
    }

    @Test
    void chileFactoryReturnsCorrectCountry() {
        DocumentProcessorFactory factory = new ChileDocumentFactory();
        assertEquals(Country.CHILE, factory.getCountry());
    }
}
