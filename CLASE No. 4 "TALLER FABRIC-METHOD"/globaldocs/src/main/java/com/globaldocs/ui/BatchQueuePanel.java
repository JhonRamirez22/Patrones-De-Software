package com.globaldocs.ui;

import com.globaldocs.client.GlobalDocsClient;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BatchQueuePanel extends JPanel {

    private final List<GlobalDocsClient.DocumentRequest> queue = new ArrayList<>();
    private final DefaultListModel<String> listModel;
    private final JList<String> queueList;
    private final JLabel countLabel;
    private final JButton processBtn;
    private final JButton clearBtn;
    private final GlobalDocsClient client;

    private static final Color GOLD = new Color(201, 168, 76);
    private static final Color NAVY = new Color(26, 39, 68);

    public BatchQueuePanel(GlobalDocsClient client, BatchResultCallback callback) {
        this.client = client;
        setLayout(new BorderLayout(0, 8));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Cola de lote");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        title.setForeground(NAVY);

        listModel = new DefaultListModel<>();
        queueList = new JList<>(listModel);
        queueList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        queueList.setBackground(new Color(250, 250, 252));
        queueList.setSelectionBackground(GOLD);
        queueList.setSelectionForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(queueList);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scroll.setPreferredSize(new Dimension(0, 100));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setBackground(Color.WHITE);

        processBtn = new JButton("Procesar lote (0)");
        processBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        processBtn.setBackground(NAVY);
        processBtn.setForeground(Color.WHITE);
        processBtn.setFocusPainted(false);
        processBtn.setBorderPainted(false);
        processBtn.setOpaque(true);
        processBtn.setEnabled(false);
        processBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        clearBtn = new JButton("Limpiar");
        clearBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        clearBtn.setBackground(new Color(232, 236, 240));
        clearBtn.setForeground(NAVY);
        clearBtn.setFocusPainted(false);
        clearBtn.setBorderPainted(false);
        clearBtn.setOpaque(true);
        clearBtn.setEnabled(false);
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        countLabel = new JLabel("0 documentos en cola");
        countLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
        countLabel.setForeground(new Color(120, 120, 120));

        buttonPanel.add(processBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(countLabel);

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        clearBtn.addActionListener(e -> {
            queue.clear();
            listModel.clear();
            updateState();
        });

        processBtn.addActionListener(e -> {
            if (!queue.isEmpty()) {
                GlobalDocsClient.BatchResult result = client.processBatch(new ArrayList<>(queue));
                callback.onBatchResult(result);
                queue.clear();
                listModel.clear();
                updateState();
            }
        });
    }

    public void addToQueue(Country country, DocumentType type, DocumentFormat format,
                           String fileName, String content) {
        queue.add(new GlobalDocsClient.DocumentRequest(country, type, format, fileName, content));
        listModel.addElement("[" + country.getCode() + "] " + type.getEnglishName()
                + " | " + format.getExtension() + " | " + fileName);
        updateState();
    }

    private void updateState() {
        int count = queue.size();
        processBtn.setEnabled(count > 0);
        clearBtn.setEnabled(count > 0);
        processBtn.setText("Procesar lote (" + count + ")");
        countLabel.setText(count + " documento" + (count != 1 ? "s" : "") + " en cola");
    }

    public interface BatchResultCallback {
        void onBatchResult(GlobalDocsClient.BatchResult result);
    }
}
