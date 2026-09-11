package com.globaldocs.ui;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    private final JTextField fileNameField;
    private final JTextArea contentArea;

    public InputPanel() {
        setLayout(new BorderLayout(0, 8));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        fieldsPanel.setBackground(Color.WHITE);

        JLabel fileNameLabel = createLabel("Nombre archivo:");
        fileNameField = createTextField("ejemplo.pdf");
        JLabel contentLabel = createLabel("Contenido:");
        contentArea = createTextArea();

        fieldsPanel.add(fileNameLabel);
        fieldsPanel.add(fileNameField);

        JPanel contentPanel = new JPanel(new BorderLayout(4, 4));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        contentPanel.setPreferredSize(new Dimension(0, 80));

        add(fieldsPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        label.setForeground(new Color(26, 39, 68));
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        field.setPreferredSize(new Dimension(0, 34));
        return field;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea(2, 20);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public String getFileName() {
        return fileNameField.getText().trim();
    }

    public String getContent() {
        return contentArea.getText().trim();
    }

    public void clear() {
        fileNameField.setText("");
        contentArea.setText("");
    }

    public boolean validateInputs() {
        if (getFileName().isEmpty()) {
            showError("Ingrese el nombre del archivo.");
            fileNameField.requestFocus();
            return false;
        }
        if (getContent().isEmpty()) {
            showError("Ingrese el contenido del documento.");
            contentArea.requestFocus();
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Campo requerido", JOptionPane.WARNING_MESSAGE);
    }
}
