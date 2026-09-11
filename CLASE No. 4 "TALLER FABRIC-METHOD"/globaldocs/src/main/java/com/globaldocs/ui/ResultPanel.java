package com.globaldocs.ui;

import com.globaldocs.model.DocumentFormat;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.BiConsumer;

public class ResultPanel extends JPanel {

    private final JTextArea resultArea;
    private final JLabel statsLabel;
    private final JButton exportButton;
    private String lastProcessedContent;
    private BiConsumer<DocumentFormat, String> exportAction;

    private static final Color SUCCESS_GREEN = new Color(45, 122, 58);
    private static final Color ERROR_RED = new Color(179, 58, 58);

    public ResultPanel() {
        setLayout(new BorderLayout(0, 8));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Resultado");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        title.setForeground(new Color(26, 39, 68));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultArea.setBackground(new Color(250, 250, 252));
        resultArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        statsLabel = new JLabel(" ");
        statsLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        statsLabel.setForeground(new Color(100, 100, 100));

        exportButton = new JButton("Exportar");
        exportButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        exportButton.setBackground(new Color(26, 39, 68));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setBorderPainted(false);
        exportButton.setOpaque(true);
        exportButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exportButton.setPreferredSize(new Dimension(120, 32));
        exportButton.setVisible(false);
        exportButton.addActionListener(e -> onExport());

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 4));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(statsLabel, BorderLayout.NORTH);
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonRow.setBackground(Color.WHITE);
        buttonRow.add(exportButton);
        bottomPanel.add(buttonRow, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scroll.setPreferredSize(new Dimension(0, 120));

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setExportAction(BiConsumer<DocumentFormat, String> action) {
        this.exportAction = action;
    }

    public void showSuccess(String message, DocumentFormat format) {
        resultArea.setForeground(SUCCESS_GREEN);
        resultArea.setText(message);
        statsLabel.setText("Procesado correctamente");
        statsLabel.setForeground(SUCCESS_GREEN);
        lastProcessedContent = message;
        exportButton.setVisible(format != null);
    }

    public void showError(String message) {
        resultArea.setForeground(ERROR_RED);
        resultArea.setText("ERROR: " + message);
        statsLabel.setText("Error en el procesamiento");
        statsLabel.setForeground(ERROR_RED);
        lastProcessedContent = null;
        exportButton.setVisible(false);
    }

    public void showBatchResult(int total, int success, int errors) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Resultado del lote ===\n");
        sb.append("Total procesados: ").append(total).append("\n");
        sb.append("Exitosos: ").append(success).append("\n");
        sb.append("Con errores: ").append(errors).append("\n");
        resultArea.setForeground(new Color(26, 39, 68));
        resultArea.setText(sb.toString());

        if (errors == 0) {
            statsLabel.setText("Todos los documentos procesados correctamente");
            statsLabel.setForeground(SUCCESS_GREEN);
        } else {
            statsLabel.setText(success + " exitosos, " + errors + " con errores");
            statsLabel.setForeground(ERROR_RED);
        }
        lastProcessedContent = null;
        exportButton.setVisible(false);
    }

    public void clear() {
        resultArea.setText("");
        statsLabel.setText(" ");
        lastProcessedContent = null;
        exportButton.setVisible(false);
    }

    private void onExport() {
        if (lastProcessedContent == null || exportAction == null) return;
        exportAction.accept(null, lastProcessedContent);
    }
}
