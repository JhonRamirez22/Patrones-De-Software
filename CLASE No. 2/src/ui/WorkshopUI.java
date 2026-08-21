package ui;

import model.*;
import builder.*;
import prototype.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Set;

public class WorkshopUI extends JFrame {
    private ConcreteRepairOrderBuilder builder;
    private RepairOrderCatalog catalog;
    private RepairOrder currentOrder;

    // Form components
    private JTextField txtClientName, txtClientId, txtClientPhone, txtClientAddress;
    private JTextField txtVehicleBrand, txtVehicleModel, txtVehicleYear, txtVehiclePlate;
    private JTextField txtVehicleColor, txtVehicleMileage, txtVehicleEngine;
    private JTextField txtMechanicName, txtMechanicSpecialty;
    private JComboBox<String> cbMechanicLevel;
    private JTextField txtEntryDate, txtDeliveryDate, txtDiagnosis;
    private JTextArea txtNotes;
    private JList<String> partsList, servicesList;
    private DefaultListModel<String> partsModel, servicesModel;
    private JTextArea txtSummary;

    // Temporary lists for builder
    private java.util.List<Part> partsTemp;
    private java.util.List<Service> servicesTemp;

    public WorkshopUI() {
        builder = new ConcreteRepairOrderBuilder();
        catalog = new RepairOrderCatalog();
        partsTemp = new java.util.ArrayList<>();
        servicesTemp = new java.util.ArrayList<>();

        setTitle("Mechanical Workshop Management System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createLeftPanel(), BorderLayout.WEST);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), " TEMPLATES (Prototypes) ",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 13), new Color(0, 102, 153)));
        panel.setPreferredSize(new Dimension(220, 0));

        DefaultListModel<String> model = new DefaultListModel<>();
        Set<String> names = catalog.getPrototypeNames();
        for (String name : names) {
            model.addElement(name);
        }

        JList<String> prototypeList = new JList<>(model);
        prototypeList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        prototypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prototypeList.setBackground(new Color(245, 248, 250));

        JButton btnClone = new JButton("Clone Template");
        btnClone.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnClone.setBackground(new Color(0, 153, 76));
        btnClone.setForeground(Color.WHITE);
        btnClone.addActionListener(e -> {
            String selected = prototypeList.getSelectedValue();
            if (selected != null) {
                clonePrototype(selected);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Select a template first",
                    "Notice", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(new JScrollPane(prototypeList), BorderLayout.CENTER);
        panel.add(btnClone, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), " NEW REPAIR ORDER ",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 13), new Color(153, 51, 0)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Client Section
        addSection(form, gbc, row++, " CLIENT ", true);
        txtClientName = addField(form, gbc, row++, "Name:");
        txtClientId = addField(form, gbc, row++, "ID:");
        txtClientPhone = addField(form, gbc, row++, "Phone:");
        txtClientAddress = addField(form, gbc, row++, "Address:");

        // Vehicle Section
        addSection(form, gbc, row++, " VEHICLE ", true);
        txtVehicleBrand = addField(form, gbc, row++, "Brand:");
        txtVehicleModel = addField(form, gbc, row++, "Model:");
        txtVehicleYear = addField(form, gbc, row++, "Year:");
        txtVehiclePlate = addField(form, gbc, row++, "Plate:");
        txtVehicleColor = addField(form, gbc, row++, "Color:");
        txtVehicleMileage = addField(form, gbc, row++, "Mileage:");
        txtVehicleEngine = addField(form, gbc, row++, "Engine Type:");

        // Mechanic Section
        addSection(form, gbc, row++, " MECHANIC ", true);
        txtMechanicName = addField(form, gbc, row++, "Name:");
        txtMechanicSpecialty = addField(form, gbc, row++, "Specialty:");
        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Level:"), gbc);
        gbc.gridx = 1;
        cbMechanicLevel = new JComboBox<>(new String[]{"Junior", "Senior", "Master"});
        cbMechanicLevel.setPreferredSize(new Dimension(200, 25));
        form.add(cbMechanicLevel, gbc);
        row++;

        // Order Section
        addSection(form, gbc, row++, " ORDER DATA ", true);
        txtEntryDate = addField(form, gbc, row++, "Entry Date:");
        txtDeliveryDate = addField(form, gbc, row++, "Est. Delivery:");
        txtDiagnosis = addField(form, gbc, row++, "Diagnosis:");

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        txtNotes = new JTextArea(3, 20);
        txtNotes.setLineWrap(true);
        form.add(new JScrollPane(txtNotes), gbc);

        panel.add(new JScrollPane(form));

        // Right panel: Parts, Services and Summary
        JPanel right = new JPanel(new BorderLayout(5, 5));
        right.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), " COMPONENTS & SUMMARY ",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 13), new Color(102, 0, 153)));

        JPanel componentsPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Parts
        JPanel partsPanel = new JPanel(new BorderLayout());
        partsPanel.setBorder(BorderFactory.createTitledBorder("Parts"));
        partsModel = new DefaultListModel<>();
        partsList = new JList<>(partsModel);
        partsPanel.add(new JScrollPane(partsList), BorderLayout.CENTER);

        JPanel partsBtns = new JPanel(new FlowLayout());
        JButton btnAddPart = new JButton("+ Add");
        btnAddPart.addActionListener(e -> addPart());
        JButton btnRemPart = new JButton("- Remove");
        btnRemPart.addActionListener(e -> removePart());
        partsBtns.add(btnAddPart);
        partsBtns.add(btnRemPart);
        partsPanel.add(partsBtns, BorderLayout.SOUTH);

        // Services
        JPanel servicesPanel = new JPanel(new BorderLayout());
        servicesPanel.setBorder(BorderFactory.createTitledBorder("Services"));
        servicesModel = new DefaultListModel<>();
        servicesList = new JList<>(servicesModel);
        servicesPanel.add(new JScrollPane(servicesList), BorderLayout.CENTER);

        JPanel servicesBtns = new JPanel(new FlowLayout());
        JButton btnAddService = new JButton("+ Add");
        btnAddService.addActionListener(e -> addService());
        JButton btnRemService = new JButton("- Remove");
        btnRemService.addActionListener(e -> removeService());
        servicesBtns.add(btnAddService);
        servicesBtns.add(btnRemService);
        servicesPanel.add(servicesBtns, BorderLayout.SOUTH);

        componentsPanel.add(partsPanel);
        componentsPanel.add(servicesPanel);

        // Summary
        txtSummary = new JTextArea();
        txtSummary.setEditable(false);
        txtSummary.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtSummary.setBackground(new Color(250, 250, 245));

        right.add(componentsPanel, BorderLayout.CENTER);
        right.add(new JScrollPane(txtSummary), BorderLayout.SOUTH);

        panel.add(right);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnCreate = new JButton("Create Order");
        btnCreate.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnCreate.setBackground(new Color(0, 102, 204));
        btnCreate.setForeground(Color.WHITE);
        btnCreate.setPreferredSize(new Dimension(150, 35));
        btnCreate.addActionListener(e -> createOrder());

        JButton btnClear = new JButton("Clear All");
        btnClear.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnClear.setBackground(new Color(180, 60, 60));
        btnClear.setForeground(Color.WHITE);
        btnClear.setPreferredSize(new Dimension(150, 35));
        btnClear.addActionListener(e -> clearForm());

        JButton btnViewSummary = new JButton("View Summary");
        btnViewSummary.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnViewSummary.setBackground(new Color(102, 153, 0));
        btnViewSummary.setForeground(Color.WHITE);
        btnViewSummary.setPreferredSize(new Dimension(150, 35));
        btnViewSummary.addActionListener(e -> viewSummary());

        panel.add(btnCreate);
        panel.add(btnClear);
        panel.add(btnViewSummary);
        return panel;
    }

    private void addSection(JPanel panel, GridBagConstraints gbc, int row, String title, boolean isLabel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(new Color(102, 102, 102));
        panel.add(lbl, gbc);
        gbc.gridwidth = 1;
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        JTextField field = new JTextField(18);
        gbc.gridx = 1;
        panel.add(field, gbc);
        return field;
    }

    // === Builder: Build order step by step ===
    private void buildOrder() {
        builder = new ConcreteRepairOrderBuilder();
        builder.setClient(new Client(
            txtClientName.getText(), txtClientId.getText(),
            txtClientPhone.getText(), "", txtClientAddress.getText()));
        builder.setVehicle(new Vehicle(
            txtVehicleBrand.getText(), txtVehicleModel.getText(),
            Integer.parseInt(txtVehicleYear.getText().isEmpty() ? "0" : txtVehicleYear.getText()),
            txtVehiclePlate.getText(), txtVehicleColor.getText(),
            Integer.parseInt(txtVehicleMileage.getText().isEmpty() ? "0" : txtVehicleMileage.getText()),
            txtVehicleEngine.getText()));
        builder.setMechanic(new Mechanic(
            txtMechanicName.getText(), "", txtClientPhone.getText(), "",
            txtMechanicSpecialty.getText(),
            (String) cbMechanicLevel.getSelectedItem(), 5));
        builder.setEntryDate(txtEntryDate.getText());
        builder.setEstimatedDeliveryDate(txtDeliveryDate.getText());
        builder.setDiagnosis(txtDiagnosis.getText());
        builder.setNotes(txtNotes.getText());

        for (Part p : partsTemp) builder.addPart(p);
        for (Service s : servicesTemp) builder.addService(s);
    }

    private void createOrder() {
        try {
            buildOrder();
            currentOrder = builder.build();
            txtSummary.setText(currentOrder.getSummary());
            JOptionPane.showMessageDialog(this,
                "Order #" + currentOrder.getId() + " created successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error creating order: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === Prototype: Clone template ===
    private void clonePrototype(String name) {
        RepairOrder clone = catalog.clone(name);
        if (clone != null) {
            currentOrder = clone;
            txtDiagnosis.setText(clone.getDiagnosis());
            txtNotes.setText(clone.getNotes());

            partsModel.clear();
            partsTemp.clear();
            for (Part p : clone.getParts()) {
                partsModel.addElement(p.getDescription());
                partsTemp.add(p);
            }

            servicesModel.clear();
            servicesTemp.clear();
            for (Service s : clone.getServices()) {
                servicesModel.addElement(s.getDescription());
                servicesTemp.add(s);
            }

            txtSummary.setText("Template \"" + name + "\" cloned.\n" +
                "Modify fields and press \"Create Order\".");
        }
    }

    private void addPart() {
        JTextField nameField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField warrantyField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Brand:", brandField,
            "Price:", priceField,
            "Quantity:", quantityField,
            "Warranty (months):", warrantyField
        };

        int option = JOptionPane.showConfirmDialog(this, message,
            "Add Part", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Part p = new Part(
                nameField.getText(), brandField.getText(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(quantityField.getText()),
                Integer.parseInt(warrantyField.getText()));
            partsTemp.add(p);
            partsModel.addElement(p.getDescription());
        }
    }

    private void removePart() {
        int idx = partsList.getSelectedIndex();
        if (idx >= 0) {
            partsTemp.remove(idx);
            partsModel.remove(idx);
        }
    }

    private void addService() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JTextField costField = new JTextField();
        JTextField hoursField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Description:", descField,
            "Labor Cost:", costField,
            "Duration (hours):", hoursField
        };

        int option = JOptionPane.showConfirmDialog(this, message,
            "Add Service", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Service s = new Service(
                nameField.getText(), descField.getText(),
                Double.parseDouble(costField.getText()),
                Double.parseDouble(hoursField.getText()));
            servicesTemp.add(s);
            servicesModel.addElement(s.getDescription());
        }
    }

    private void removeService() {
        int idx = servicesList.getSelectedIndex();
        if (idx >= 0) {
            servicesTemp.remove(idx);
            servicesModel.remove(idx);
        }
    }

    private void clearForm() {
        txtClientName.setText("");
        txtClientId.setText("");
        txtClientPhone.setText("");
        txtClientAddress.setText("");
        txtVehicleBrand.setText("");
        txtVehicleModel.setText("");
        txtVehicleYear.setText("");
        txtVehiclePlate.setText("");
        txtVehicleColor.setText("");
        txtVehicleMileage.setText("");
        txtVehicleEngine.setText("");
        txtMechanicName.setText("");
        txtMechanicSpecialty.setText("");
        cbMechanicLevel.setSelectedIndex(0);
        txtEntryDate.setText("");
        txtDeliveryDate.setText("");
        txtDiagnosis.setText("");
        txtNotes.setText("");
        partsModel.clear();
        servicesModel.clear();
        partsTemp.clear();
        servicesTemp.clear();
        txtSummary.setText("");
    }

    private void viewSummary() {
        if (currentOrder != null) {
            txtSummary.setText(currentOrder.getSummary());
        } else {
            txtSummary.setText("No order to display.\n" +
                "Create a new order or clone a template.");
        }
    }
}