# GlobalDocs Document Processing System

Enterprise document processing system for **GlobalDocs Solutions** using the **Factory Method** design pattern.

## Overview

GlobalDocs Solutions is a multinational company (Colombia, Mexico, Argentina, Chile) processing 50,000+ documents daily. Each country has different regulations and supported document formats.

## Architecture — Factory Method

```
                    DocumentProcessorFactory (Creator abstracto)
                              |
         +---------+----------+----------+---------+
         |         |          |          |         |
    Colombia   Mexico    Argentina    Chile    (futuros)
    Factory    Factory    Factory    Factory
         |         |          |          |
         +----+----+----+-----+----+-----+
              |         |          |
         DocumentProcessor (Product)
              |
    +----+----+----+----+----+
    |    |    |    |    |    |
  Invoice Contract Report Cert Tax    (5 ConcreteProducts)
```

**Design Decision:** One ConcreteCreator per country (GoF "clásico"). Each country has its own factory that validates which formats and document types are allowed.

## Supported Document Types

| Type | Description |
|------|-------------|
| `ELECTRONIC_INVOICE` | Factura Electrónica |
| `LEGAL_CONTRACT` | Contrato Legal |
| `FINANCIAL_REPORT` | Reporte Financiero |
| `DIGITAL_CERTIFICATE` | Certificado Digital |
| `TAX_DECLARATION` | Declaración Tributaria |

## Supported Formats by Country

| Format | Colombia | Mexico | Argentina | Chile |
|--------|----------|--------|-----------|-------|
| `.pdf` | ✅ | ✅ | ✅ | ✅ |
| `.txt` | ✅ | ✅ | ❌ | ✅ |
| `.csv` | ✅ | ❌ | ✅ | ❌ |
| `.xlsx` | ✅ | ❌ | ✅ | ❌ |
| `.doc` | ❌ | ✅ | ❌ | ✅ |
| `.docx` | ❌ | ✅ | ❌ | ✅ |

## Build & Run

### Console

```bash
cd globaldocs
mvn clean compile
mvn test
```

### GUI (Java Swing)

```bash
cd globaldocs
mvn exec:java
```

La interfaz gráfica se abre en una ventana. Desde ahí puedes:
- Seleccionar país (Colombia, Mexico, Argentina, Chile)
- Elegir tipo de documento y formato
- Procesar documentos individuales
- Agregar documentos a una cola y procesarlos en lote

## Usage Example

```java
GlobalDocsClient client = new GlobalDocsClient();

// Single document
String result = client.processDocument(
    Country.COLOMBIA,
    DocumentType.ELECTRONIC_INVOICE,
    DocumentFormat.PDF,
    "invoice_001.pdf",
    "<xml>...</xml>"
);

// Batch processing
List<DocumentRequest> batch = List.of(
    new DocumentRequest(Country.COLOMBIA, DocumentType.ELECTRONIC_INVOICE, DocumentFormat.PDF, "inv.pdf", "data"),
    new DocumentRequest(Country.MEXICO, DocumentType.LEGAL_CONTRACT, DocumentFormat.DOCX, "contract.docx", "data")
);

BatchResult result = client.processBatch(batch);
System.out.println(result);
```

## Project Structure

```
globaldocs/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/globaldocs/
    │   ├── model/          (enums: Country, DocumentType, DocumentFormat)
    │   ├── processor/      (DocumentProcessor interface + 5 implementations)
    │   ├── factory/        (abstract factory + 4 country factories)
    │   ├── exception/      (custom exceptions)
    │   ├── client/         (GlobalDocsClient with batch processing)
    │   └── ui/             (Java Swing GUI)
    │       ├── GlobalDocsApp.java
    │       ├── CountryPanel.java
    │       ├── DocumentTypePanel.java
    │       ├── FormatPanel.java
    │       ├── InputPanel.java
    │       ├── ResultPanel.java
    │       └── BatchQueuePanel.java
    └── test/java/com/globaldocs/
        └── factory/        (JUnit 5 tests)
```
