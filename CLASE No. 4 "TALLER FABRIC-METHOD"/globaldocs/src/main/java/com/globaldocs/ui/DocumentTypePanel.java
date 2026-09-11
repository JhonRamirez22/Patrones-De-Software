package com.globaldocs.ui;

import com.globaldocs.model.DocumentType;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DocumentTypePanel extends JPanel {

    private DocumentType selectedType;
    private final JButton[] typeButtons;
    private final Consumer<DocumentType> onTypeSelected;

    private static final Color BG_DEFAULT = new Color(232, 236, 240);
    private static final Color BG_SELECTED = new Color(26, 39, 68);
    private static final Color BG_DISABLED = new Color(245, 245, 245);
    private static final Color FG_DEFAULT = new Color(26, 39, 68);
    private static final Color FG_SELECTED = new Color(255, 255, 255);
    private static final Color FG_DISABLED = new Color(180, 180, 180);
    private static final Color GOLD = new Color(201, 168, 76);

    private static final String[] SHORT_NAMES = {
            "Factura", "Contrato", "Reporte", "Certificado", "Declaración"
    };

    public DocumentTypePanel(Consumer<DocumentType> onTypeSelected) {
        this.onTypeSelected = onTypeSelected;
        DocumentType[] types = DocumentType.values();
        typeButtons = new JButton[types.length];

        setLayout(new GridLayout(1, types.length, 8, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        setBackground(Color.WHITE);

        for (int i = 0; i < types.length; i++) {
            JButton btn = new JButton(SHORT_NAMES[i]);
            btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            btn.setBackground(BG_DEFAULT);
            btn.setForeground(FG_DEFAULT);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(0, 40));

            final int index = i;
            btn.addActionListener(e -> {
                if (btn.isEnabled()) {
                    selectType(types[index]);
                }
            });
            typeButtons[i] = btn;
            add(btn);
        }
    }

    private void selectType(DocumentType type) {
        this.selectedType = type;
        DocumentType[] types = DocumentType.values();
        for (int i = 0; i < types.length; i++) {
            boolean isSelected = types[i] == type;
            typeButtons[i].setBackground(isSelected ? BG_SELECTED : BG_DEFAULT);
            typeButtons[i].setForeground(isSelected ? FG_SELECTED : FG_DEFAULT);
            if (isSelected) {
                typeButtons[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, GOLD));
            } else {
                typeButtons[i].setBorder(BorderFactory.createEmptyBorder());
            }
        }
        onTypeSelected.accept(type);
    }

    public void resetSelection() {
        selectedType = null;
        DocumentType[] types = DocumentType.values();
        for (int i = 0; i < types.length; i++) {
            typeButtons[i].setBackground(BG_DEFAULT);
            typeButtons[i].setForeground(FG_DEFAULT);
            typeButtons[i].setBorder(BorderFactory.createEmptyBorder());
            typeButtons[i].setEnabled(true);
        }
    }

    public DocumentType getSelectedType() {
        return selectedType;
    }
}
