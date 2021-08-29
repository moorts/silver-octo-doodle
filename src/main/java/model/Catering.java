package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Catering extends EventElement {
    private String speiseangebot;
    private String menuBeschreibung;

    private transient JTextArea speiseangebotTextArea;
    private transient JTextArea menuBeschreibungTextArea;

    @Override
    public ElementType getType() { return ElementType.CATERING; }

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        panel.add(new JLabel("Speiseangebot:"));
        speiseangebotTextArea = new JTextArea(speiseangebot);
        var speiseangebotScrollPane = new JScrollPane(speiseangebotTextArea);
        speiseangebotScrollPane.setAlignmentX(0f);
        panel.add(speiseangebotScrollPane);

        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Menü Beschreibung:"));
        menuBeschreibungTextArea = new JTextArea(menuBeschreibung);
        var menuBeschreibungScrollPane = new JScrollPane(menuBeschreibungTextArea);
        menuBeschreibungScrollPane.setAlignmentX(0f);
        panel.add(menuBeschreibungScrollPane);

        return panel;
    }

    @Override
    public void saveChanges() {
        speiseangebot = speiseangebotTextArea.getText();
        menuBeschreibung = menuBeschreibungTextArea.getText();
    }
}
