# GlobalDocs - Sistema de Procesamiento de Documentos

Sistema empresarial de procesamiento de documentos para **GlobalDocs Solutions** utilizando el patrón de diseño **Factory Method**.

## Descripción General

GlobalDocs Solutions es una empresa multinational (Colombia, México, Argentina, Chile) que procesa más de 50,000 documentos diarios. Cada país tiene regulaciones diferentes y formatos de documento soportados.

## Arquitectura — Factory Method

```
                    DocumentProcessorFactory (Creator abstracto)
                              |
         +---------+----------+----------+---------+
         |         |          |          |         |
    Colombia   México    Argentina    Chile    (futuros)
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

**Decisión de Diseño:** Un ConcreteCreator por país (GoF "clásico"). Cada país tiene su propia fábrica que valida qué formatos y tipos de documento están permitidos.

## Tipos de Documento Soportados

| Tipo | Descripción |
|------|-------------|
| `ELECTRONIC_INVOICE` | Factura Electrónica |
| `LEGAL_CONTRACT` | Contrato Legal |
| `FINANCIAL_REPORT` | Reporte Financiero |
| `DIGITAL_CERTIFICATE` | Certificado Digital |
| `TAX_DECLARATION` | Declaración Tributaria |

## Formatos Soportados por País

| Formato | Colombia | México | Argentina | Chile |
|---------|----------|--------|-----------|-------|
| `.pdf`  | ✅ | ✅ | ✅ | ✅ |
| `.txt`  | ✅ | ✅ | ❌ | ✅ |
| `.csv`  | ✅ | ❌ | ✅ | ❌ |
| `.xlsx` | ✅ | ❌ | ✅ | ❌ |
| `.doc`  | ❌ | ✅ | ❌ | ✅ |
| `.docx` | ❌ | ✅ | ❌ | ✅ |
| `.md`   | ✅ | ✅ | ✅ | ✅ |

## Requisitos

- Java 17 o superior
- Maven 3.8+

## Compilación y Ejecución

### Compilar

```bash
cd "CLASE No. 4 'TALLER FABRIC-METHOD'/globaldocs"
mvn clean compile
```

### Ejecutar pruebas

```bash
mvn test
```

### Generar JAR ejecutable

```bash
mvn clean package
```

El JAR fat se genera en `target/globaldocs-document-processor-1.0.0.jar` (incluye todas las dependencias).

### Ejecutar la interfaz gráfica (GUI)

```bash
java -jar target/globaldocs-document-processor-1.0.0.jar
```

O directamente con Maven:

```bash
mvn exec:java
```

## Uso de la Interfaz Gráfica

Al ejecutar la aplicación, se abre una ventana Swing con las siguientes opciones:

1. **Seleccionar País:** Elija entre Colombia, México, Argentina o Chile
2. **Tipo de Documento:** Seleccione el tipo de documento a procesar
3. **Formato de Salida:** Elija el formato de exportación (PDF, DOCX, CSV, etc.)
4. **Nombre del Archivo:** Ingrese el nombre del archivo
5. **Contenido:** Ingrese el contenido del documento
6. **Procesar:** Haga clic en "Procesar" para generar el documento

### Exportar Documentos

Después de procesar un documento, aparece un botón **"Exportar"** que le permite:

1. Hacer clic en el botón "Exportar"
2. Seleccionar la ubicación de guardado en el cuadro de diálogo del sistema
3. El documento se guarda en el formato seleccionado

**Formatos de exportación disponibles:**

| Formato | Extensión | Biblioteca |
|---------|-----------|------------|
| PDF | `.pdf` | OpenPDF |
| DOCX | `.docx` | Apache POI |
| DOC | `.doc` | RTF nativo |
| XLSX | `.xlsx` | Apache POI |
| CSV | `.csv` | Java nativo |
| TXT | `.txt` | Java nativo |
| Markdown | `.md` | Java nativo |

### Procesamiento en Lote

La interfaz también soporta procesamiento en lote:

1. Agregue documentos a la cola usando "Agregar a Cola"
2. Haga clic en "Procesar Lote" para procesar todos los documentos de la cola
3. Los resultados se muestran en el panel de resultados

## Ejemplo de Uso (API Java)

```java
GlobalDocsClient client = new GlobalDocsClient();

// Documento individual
String result = client.processDocument(
    Country.COLOMBIA,
    DocumentType.ELECTRONIC_INVOICE,
    DocumentFormat.PDF,
    "invoice_001.pdf",
    "<xml>...</xml>"
);

// Exportar a archivo
client.exportDocument(DocumentFormat.PDF, result, "/path/to/save/invoice.pdf");

// Procesamiento en lote
List<DocumentRequest> batch = List.of(
    new DocumentRequest(Country.COLOMBIA, DocumentType.ELECTRONIC_INVOICE, DocumentFormat.PDF, "inv.pdf", "data"),
    new DocumentRequest(Country.MEXICO, DocumentType.LEGAL_CONTRACT, DocumentFormat.DOCX, "contract.docx", "data")
);

BatchResult batchResult = client.processBatch(batch);
System.out.println(batchResult);
```

## Distribución

La aplicación incluye scripts de ejecución precompilados:

### macOS

```bash
# Ejecutar directamente
java -jar distribuitables/mac/globaldocs-document-processor-1.0.0.jar

# O usar el script .command
open distribuitables/mac/GlobalDocs.command
```

### Windows

```bash
# Ejecutar el batch file
distribuitables\windows\GlobalDocs.bat
```

## Estructura del Proyecto

```
globaldocs/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/globaldocs/
    │   ├── model/          (enums: Country, DocumentType, DocumentFormat)
    │   ├── processor/      (interfaz DocumentProcessor + 5 implementaciones)
    │   ├── factory/        (fábrica abstracta + 4 fábricas por país)
    │   ├── exporter/       (interfaz DocumentExporter + 7 exportadores)
    │   ├── exception/      (excepciones personalizadas)
    │   ├── client/         (GlobalDocsClient con procesamiento en lote)
    │   └── ui/             (interfaz gráfica Java Swing)
    │       ├── GlobalDocsApp.java
    │       ├── CountryPanel.java
    │       ├── DocumentTypePanel.java
    │       ├── FormatPanel.java
    │       ├── InputPanel.java
    │       ├── ResultPanel.java
    │       └── BatchQueuePanel.java
    └── test/java/com/globaldocs/
        ├── factory/        (pruebas JUnit 5 de fábricas)
        ├── client/         (pruebas JUnit 5 del cliente)
        └── exporter/       (pruebas JUnit 5 de exportadores)
```

## Dependencias

| Dependencia | Versión | Propósito |
|-------------|---------|-----------|
| OpenPDF | 3.0.5 | Exportación a PDF |
| Apache POI | 5.2.5 | Exportación a DOCX y XLSX |
| JUnit 5 | 5.10.2 | Pruebas unitarias |

## Patrón de Diseño: Factory Method

Este proyecto implementa el patrón **Factory Method** del Gang of Four:

- **Product (Producto):** `DocumentProcessor` — interfaz para procesar documentos
- **ConcreteProduct (Producto Concreto):** Implementaciones específicas por tipo de documento (ElectronicInvoiceProcessor, LegalContractProcessor, etc.)
- **Creator (Creador):** `DocumentProcessorFactory` — fábrica abstracta
- **ConcreteCreator (Creador Concreto):** Fábricas por país (ColombiaDocumentProcessorFactory, MexicoDocumentProcessorFactory, etc.)

**Beneficios del patrón:**
- Separación de la creación de objetos de su uso
- Fácil extensión para nuevos países o tipos de documento
- Cada fábrica encapsula las reglas de validación de su país
- Código cliente no necesita conocer las implementaciones concretas
