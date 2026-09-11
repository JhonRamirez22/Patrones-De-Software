package com.globaldocs.ui;

import com.globaldocs.client.GlobalDocsClient;
import com.globaldocs.exception.DocumentProcessingException;
import com.globaldocs.factory.*;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class GlobalDocsApp extends JFrame {

    private final GlobalDocsClient client;
    private final Map<Country, DocumentProcessorFactory> factories;
    private final CountryPanel countryPanel;
    private final DocumentTypePanel documentTypePanel;
    private final FormatPanel formatPanel;
    private final InputPanel inputPanel;
    private final ResultPanel resultPanel;
    private final BatchQueuePanel batchQueuePanel;
    private final JButton processButton;
    private final JButton addToBatchButton;

    private Country selectedCountry;
    private DocumentType selectedType;
    private DocumentFormat selectedFormat;

    private static final Color NAVY = new Color(26, 39, 68);
    private static final Color GOLD = new Color(201, 168, 76);
    private static final Color BG = new Color(245, 247, 250);

    public GlobalDocsApp() {
        client = new GlobalDocsClient();
        factories = new EnumMap<>(Country.class);
        factories.put(Country.COLOMBIA, new ColombiaDocumentFactory());
        factories.put(Country.MEXICO, new MexicoDocumentFactory());
        factories.put(Country.ARGENTINA, new ArgentinaDocumentFactory());
        factories.put(Country.CHILE, new ChileDocumentFactory());

        setTitle("GlobalDocs Document Processor");
        setSize(900, 680);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        // --- Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel titleLabel = new JLabel("GlobalDocs Document Processor");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Factory Method Pattern - Enterprise Document Processing");
        subtitleLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(180, 190, 210));

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(titleLabel);
        titleBox.add(subtitleLabel);

        JLabel flagLabel = new JLabel("\uD83C\uDF0D ");
        flagLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
        flagLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(titleBox, BorderLayout.CENTER);
        header.add(flagLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- Main content ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Section: País
        mainPanel.add(createSectionLabel("Seleccionar País"));
        countryPanel = new CountryPanel(this::onCountryChanged);
        mainPanel.add(countryPanel);

        // Section: Tipo de documento
        mainPanel.add(createSectionLabel("Tipo de Documento"));
        documentTypePanel = new DocumentTypePanel(this::onTypeChanged);
        mainPanel.add(documentTypePanel);

        // Section: Formato
        mainPanel.add(createSectionLabel("Formato (según país)"));
        formatPanel = new FormatPanel(this::onFormatChanged);
        mainPanel.add(formatPanel);

        // Section: Datos de entrada
        mainPanel.add(createSectionLabel("Datos del Documento"));
        inputPanel = new InputPanel();
        mainPanel.add(inputPanel);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        actionPanel.setBackground(BG);

        processButton = createActionButton("Procesar Documento", NAVY, Color.WHITE);
        processButton.setEnabled(false);
        processButton.addActionListener(e -> processDocument());

        addToBatchButton = createActionButton("Agregar a lote", GOLD, Color.WHITE);
        addToBatchButton.setEnabled(false);
        addToBatchButton.addActionListener(e -> addToBatch());

        actionPanel.add(processButton);
        actionPanel.add(addToBatchButton);
        mainPanel.add(actionPanel);
        mainPanel.add(Box.createVerticalStrut(8));

        // Section: Resultado
        mainPanel.add(createSectionLabel("Resultado"));
        resultPanel = new ResultPanel();
        resultPanel.setExportAction((format, content) -> exportDocument(content));
        mainPanel.add(resultPanel);

        // Section: Cola de lote
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(createSectionLabel("Cola de Lote"));
        batchQueuePanel = new BatchQueuePanel(client, this::onBatchResult);
        mainPanel.add(batchQueuePanel);

        JScrollPane mainScroll = new JScrollPane(mainPanel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);

        // --- Footer ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(NAVY);
        footer.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        JLabel footerLabel = new JLabel("GlobalDocs Solutions - Multinational Document Processing");
        footerLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        footerLabel.setForeground(new Color(160, 170, 190));
        footer.add(footerLabel);
        add(footer, BorderLayout.SOUTH);
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        label.setForeground(NAVY);
        label.setBorder(BorderFactory.createEmptyBorder(4, 0, 6, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createActionButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 36));
        return btn;
    }

    private void onCountryChanged(Country country) {
        this.selectedCountry = country;
        this.selectedFormat = null;
        DocumentProcessorFactory factory = factories.get(country);
        if (factory != null) {
            formatPanel.updateSupportedFormats(factory.getSupportedFormats());
        }
        updateProcessButton();
    }

    private void onTypeChanged(DocumentType type) {
        this.selectedType = type;
        updateProcessButton();
    }

    private void onFormatChanged(DocumentFormat format) {
        this.selectedFormat = format;
        updateProcessButton();
    }

    private void updateProcessButton() {
        boolean ready = selectedCountry != null && selectedType != null && selectedFormat != null;
        processButton.setEnabled(ready);
        addToBatchButton.setEnabled(ready);
    }

    private void processDocument() {
        if (!inputPanel.validateInputs()) return;

        try {
            String result = client.processDocument(
                    selectedCountry, selectedType, selectedFormat,
                    inputPanel.getFileName(), inputPanel.getContent());
            resultPanel.showSuccess(result, selectedFormat);
        } catch (DocumentProcessingException e) {
            resultPanel.showError(e.getMessage());
        }
    }

    private void exportDocument(String content) {
        if (selectedFormat == null) return;

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exportar documento");
        String ext = selectedFormat.getExtension();
        chooser.setSelectedFile(new File("documento." + ext));

        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String outputPath = chooser.getSelectedFile().getAbsolutePath();
            if (!outputPath.endsWith("." + ext)) {
                outputPath += "." + ext;
            }
            try {
                client.exportDocument(selectedFormat, content, outputPath);
                JOptionPane.showMessageDialog(this,
                        "Documento exportado a:\n" + outputPath,
                        "Exportacion exitosa", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al exportar: " + e.getMessage(),
                        "Error de exportacion", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addToBatch() {
        if (!inputPanel.validateInputs()) return;

        batchQueuePanel.addToQueue(
                selectedCountry, selectedType, selectedFormat,
                inputPanel.getFileName(), inputPanel.getContent());
        resultPanel.clear();
        inputPanel.clear();
    }

    private void onBatchResult(GlobalDocsClient.BatchResult result) {
        int errors = result.getErrorCount();
        int success = result.getSuccessCount();
        resultPanel.showBatchResult(result.getTotalProcessed(), success, errors);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            GlobalDocsApp app = new GlobalDocsApp();
            app.setVisible(true);
        });
    }
}
