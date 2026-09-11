package com.globaldocs.ui;

import com.globaldocs.model.DocumentFormat;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

public class FormatPanel extends JPanel {

    private DocumentFormat selectedFormat;
    private final JButton[] formatButtons;
    private final Consumer<DocumentFormat> onFormatSelected;

    private static final Color BG_DEFAULT = new Color(232, 236, 240);
    private static final Color BG_SELECTED = new Color(26, 39, 68);
    private static final Color BG_DISABLED = new Color(245, 245, 245);
    private static final Color FG_DEFAULT = new Color(26, 39, 68);
    private static final Color FG_SELECTED = new Color(255, 255, 255);
    private static final Color FG_DISABLED = new Color(180, 180, 180);
    private static final Color GOLD = new Color(201, 168, 76);

    public FormatPanel(Consumer<DocumentFormat> onFormatSelected) {
        this.onFormatSelected = onFormatSelected;
        DocumentFormat[] formats = DocumentFormat.values();
        formatButtons = new JButton[formats.length];

        setLayout(new GridLayout(1, formats.length, 6, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        setBackground(Color.WHITE);

        for (int i = 0; i < formats.length; i++) {
            JButton btn = new JButton(formats[i].getExtension());
            btn.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
            btn.setBackground(BG_DISABLED);
            btn.setForeground(FG_DISABLED);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setEnabled(false);
            btn.setPreferredSize(new Dimension(0, 36));

            final int index = i;
            btn.addActionListener(e -> {
                if (btn.isEnabled()) {
                    selectFormat(formats[index]);
                }
            });
            formatButtons[i] = btn;
            add(btn);
        }
    }

    public void updateSupportedFormats(Set<DocumentFormat> supportedFormats) {
        DocumentFormat[] formats = DocumentFormat.values();
        selectedFormat = null;
        for (int i = 0; i < formats.length; i++) {
            boolean supported = supportedFormats.contains(formats[i]);
            formatButtons[i].setEnabled(supported);
            formatButtons[i].setBackground(supported ? BG_DEFAULT : BG_DISABLED);
            formatButtons[i].setForeground(supported ? FG_DEFAULT : FG_DISABLED);
            formatButtons[i].setBorder(BorderFactory.createEmptyBorder());
        }
    }

    private void selectFormat(DocumentFormat format) {
        this.selectedFormat = format;
        DocumentFormat[] formats = DocumentFormat.values();
        for (int i = 0; i < formats.length; i++) {
            if (!formatButtons[i].isEnabled()) continue;
            boolean isSelected = formats[i] == format;
            formatButtons[i].setBackground(isSelected ? BG_SELECTED : BG_DEFAULT);
            formatButtons[i].setForeground(isSelected ? FG_SELECTED : FG_DEFAULT);
            if (isSelected) {
                formatButtons[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, GOLD));
            } else {
                formatButtons[i].setBorder(BorderFactory.createEmptyBorder());
            }
        }
        onFormatSelected.accept(format);
    }

    public void resetSelection() {
        selectedFormat = null;
        for (JButton btn : formatButtons) {
            btn.setBackground(BG_DISABLED);
            btn.setForeground(FG_DISABLED);
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setEnabled(false);
        }
    }

    public DocumentFormat getSelectedFormat() {
        return selectedFormat;
    }
}
