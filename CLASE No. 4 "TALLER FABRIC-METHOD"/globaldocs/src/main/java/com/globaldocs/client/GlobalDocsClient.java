package com.globaldocs.client;

import com.globaldocs.exception.DocumentProcessingException;
import com.globaldocs.exporter.DocumentExporter;
import com.globaldocs.exporter.DocumentExporterFactory;
import com.globaldocs.factory.*;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;
import com.globaldocs.processor.DocumentProcessor;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalDocsClient {

    private final Map<Country, DocumentProcessorFactory> factories = new EnumMap<>(Country.class);

    public GlobalDocsClient() {
        factories.put(Country.COLOMBIA, new ColombiaDocumentFactory());
        factories.put(Country.MEXICO, new MexicoDocumentFactory());
        factories.put(Country.ARGENTINA, new ArgentinaDocumentFactory());
        factories.put(Country.CHILE, new ChileDocumentFactory());
    }

    public String processDocument(Country country, DocumentType type, DocumentFormat format,
                                   String fileName, String content) {
        DocumentProcessorFactory factory = factories.get(country);
        if (factory == null) {
            throw new IllegalArgumentException("No factory registered for country: " + country);
        }
        DocumentProcessor processor = factory.createProcessor(type, format);
        return processor.process(fileName, content);
    }

    public void exportDocument(DocumentFormat format, String content, String outputPath) throws IOException {
        DocumentExporter exporter = DocumentExporterFactory.createExporter(format);
        exporter.export(content, outputPath);
    }

    public BatchResult processBatch(List<DocumentRequest> requests) {
        Objects.requireNonNull(requests, "requests must not be null");

        BatchResult result = new BatchResult();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (DocumentRequest request : requests) {
            try {
                String output = processDocument(
                        request.country(), request.type(), request.format(),
                        request.fileName(), request.content());
                result.addSuccess(request.fileName(), output);
                successCount.incrementAndGet();
            } catch (DocumentProcessingException e) {
                result.addError(request.fileName(), e.getMessage());
                errorCount.incrementAndGet();
            } catch (Exception e) {
                result.addError(request.fileName(), "Unexpected error: " + e.getMessage());
                errorCount.incrementAndGet();
            }
        }

        result.setSummary(successCount.get(), errorCount.get(), requests.size());
        return result;
    }

    public record DocumentRequest(Country country, DocumentType type, DocumentFormat format,
                                   String fileName, String content) {}

    public static class BatchResult {
        private final List<String> successEntries = new ArrayList<>();
        private final List<String> errorEntries = new ArrayList<>();
        private int totalProcessed;
        private int successCount;
        private int errorCount;

        public void addSuccess(String fileName, String output) {
            successEntries.add(String.format("OK    [%s] %s", fileName, output));
        }

        public void addError(String fileName, String errorMessage) {
            errorEntries.add(String.format("ERROR [%s] %s", fileName, errorMessage));
        }

        public void setSummary(int successCount, int errorCount, int total) {
            this.successCount = successCount;
            this.errorCount = errorCount;
            this.totalProcessed = total;
        }

        public List<String> getSuccessEntries() { return Collections.unmodifiableList(successEntries); }
        public List<String> getErrorEntries() { return Collections.unmodifiableList(errorEntries); }
        public int getTotalProcessed() { return totalProcessed; }
        public int getSuccessCount() { return successCount; }
        public int getErrorCount() { return errorCount; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Batch Processing Result ===\n");
            successEntries.forEach(e -> sb.append(e).append("\n"));
            errorEntries.forEach(e -> sb.append(e).append("\n"));
            sb.append("------------------------------\n");
            sb.append(String.format("Total: %d | Success: %d | Errors: %d%n",
                    totalProcessed, successCount, errorCount));
            return sb.toString();
        }
    }
}
