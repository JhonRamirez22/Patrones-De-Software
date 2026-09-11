package com.globaldocs.ui;

import com.globaldocs.model.Country;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class CountryPanel extends JPanel {

    private Country selectedCountry;
    private final JButton[] countryButtons = new JButton[Country.values().length];
    private final Consumer<Country> onCountrySelected;

    private static final Color BG_DEFAULT = new Color(232, 236, 240);
    private static final Color BG_SELECTED = new Color(26, 39, 68);
    private static final Color FG_DEFAULT = new Color(26, 39, 68);
    private static final Color FG_SELECTED = new Color(255, 255, 255);
    private static final Color GOLD = new Color(201, 168, 76);

    private static final String[] FLAGS = {"\uD83C\uDDE8\uD83C\uDDF4", "\uD83C\uDDF2\uD83C\uDDFD", "\uD83C\uDDE6\uD83C\uDDF7", "\uD83C\uDDE8\uD83C\uDDF1"};

    public CountryPanel(Consumer<Country> onCountrySelected) {
        this.onCountrySelected = onCountrySelected;
        setLayout(new GridLayout(1, 4, 12, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        setBackground(Color.WHITE);

        Country[] countries = Country.values();
        for (int i = 0; i < countries.length; i++) {
            Country c = countries[i];
            JButton btn = new JButton(FLAGS[i] + " " + c.getName());
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
            btn.setBackground(BG_DEFAULT);
            btn.setForeground(FG_DEFAULT);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(0, 55));

            btn.addActionListener(e -> selectCountry(c));
            countryButtons[i] = btn;
            add(btn);
        }
    }

    private void selectCountry(Country country) {
        this.selectedCountry = country;
        Country[] countries = Country.values();
        for (int i = 0; i < countries.length; i++) {
            boolean isSelected = countries[i] == country;
            countryButtons[i].setBackground(isSelected ? BG_SELECTED : BG_DEFAULT);
            countryButtons[i].setForeground(isSelected ? FG_SELECTED : FG_DEFAULT);
            if (isSelected) {
                countryButtons[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, GOLD));
            } else {
                countryButtons[i].setBorder(BorderFactory.createEmptyBorder());
            }
        }
        onCountrySelected.accept(country);
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void clearSelection() {
        selectedCountry = null;
        Country[] countries = Country.values();
        for (int i = 0; i < countries.length; i++) {
            countryButtons[i].setBackground(BG_DEFAULT);
            countryButtons[i].setForeground(FG_DEFAULT);
            countryButtons[i].setBorder(BorderFactory.createEmptyBorder());
        }
    }
}
