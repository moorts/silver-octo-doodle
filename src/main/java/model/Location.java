package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;

public class Location extends EventElement {
    private String adresse;
    private int groesse;

    private transient JTextArea adresseTextArea;
    private transient JSpinner groesseSpinner;

    @Override
    public ElementType getType() {
        return ElementType.LOCATION;
    }

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        panel.add(new JLabel("Adresse:"));
        adresseTextArea = new JTextArea(adresse);
        var adresseScrollPane = new JScrollPane(adresseTextArea);
        adresseScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        adresseScrollPane.setAlignmentX(0f);
        panel.add(adresseScrollPane);

        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Größe (m²):"));
        groesseSpinner = new JSpinner(new SpinnerNumberModel(groesse, 0, Integer.MAX_VALUE, 1));
        groesseSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, groesseSpinner.getPreferredSize().height));
        groesseSpinner.setAlignmentX(0f);
        panel.add(groesseSpinner);

        return panel;
    }

    @Override
    public void saveChanges() {
        try {
            groesseSpinner.commitEdit();
            adresse = adresseTextArea.getText();
            groesse = (int)groesseSpinner.getValue();
        } catch (ParseException e) {
            System.out.println("Falscher Wert eingegeben.");
        }
    }
}
