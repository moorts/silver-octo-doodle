package ui;

import de.dhbwka.swe.utils.gui.SlideshowComponent;
import model.Hilfsmittel;
import ui.base.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Field;

public class HilfsmittelDetailView extends View {
    public HilfsmittelDetailView(Hilfsmittel hilfsmittel) {
        this.controller = new HilfsmittelDetailController(this, hilfsmittel);
    }

    public SlideshowComponent slideshow;
    public JButton backButton;

    public JLabel headerLabel;
    public JLabel anzahlInsgesamtLabel;
    public JLabel anzahlAktuellLabel;

    public JTextField nameTextField;
    public JTextArea beschreibungTextArea;
    public JTextArea beschreibungReadOnlyTextArea;

    public JSpinner anzahlInsgesamtSpinner;

    public JButton saveHilfsmittelDetailsButton;
    public JButton bildHinzufuegenButton;
    public JButton bildLoeschenButton;

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel leftrightPanel = new JPanel();
        leftrightPanel.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = buildLeftPanel();
        JPanel rightPanel = buildRightPanel();

        leftrightPanel.add(leftPanel);
        leftrightPanel.add(rightPanel);
        panel.add(leftrightPanel, BorderLayout.CENTER);

        JPanel buttonBar = buildButtonBar();
        panel.add(buttonBar, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        slideshow = SlideshowComponent.builder("hilfsmittelDetailSlideshow").build();
        // Fix slideshow button color for dark theme
        try {
            Field btnField = slideshow.getClass().getDeclaredField("btnImage");
            btnField.setAccessible(true);
            JButton btn = (JButton)btnField.get(slideshow);
            btn.setBackground(new JButton().getBackground());
        } catch (Exception e) {
            e.printStackTrace();
        }
            panel.add(slideshow);

        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        JScrollPane textScrollPane = new JScrollPane(labelPanel);
        panel.add(textScrollPane);

        labelPanel.add(headerLabel = new JLabel(String.format(EventDetailController.LABEL_HEADER, "")));
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        labelPanel.add(Box.createVerticalStrut(10));

        labelPanel.add(anzahlAktuellLabel = new JLabel(String.format(HilfsmittelDetailController.LABEL_CURRENT, "")));
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(anzahlInsgesamtLabel = new JLabel(String.format(HilfsmittelDetailController.LABEL_ALL, "")));

        labelPanel.add(Box.createVerticalStrut(5));

        //JSpinner spinner = new JSpinner();
        //labelPanel.add(spinner);

        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(new JLabel("<html><u>Beschreibung:</u></html>"));
        beschreibungReadOnlyTextArea = new JTextArea("");
        beschreibungReadOnlyTextArea.setEditable(false);
        beschreibungReadOnlyTextArea.setLineWrap(true);
        var beschreibungScrollPane = new JScrollPane(beschreibungReadOnlyTextArea);
        beschreibungScrollPane.setAlignmentX(0f);
        labelPanel.add(beschreibungScrollPane);

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        //JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //buttonBar.add(saveEventDetailsButton = new JButton("Änderungen speichern"));
        //panel.add(buttonBar, BorderLayout.SOUTH);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        fieldsPanel.add(new JLabel("Titel:"));
        nameTextField = new JTextField();
        nameTextField.setAlignmentX(0f);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        fieldsPanel.add(nameTextField);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Gesamtanzahl"));
        anzahlInsgesamtSpinner = new JSpinner();
        anzahlInsgesamtSpinner.setAlignmentX(0f);
        anzahlInsgesamtSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, anzahlInsgesamtSpinner.getPreferredSize().height));
        fieldsPanel.add(anzahlInsgesamtSpinner);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Beschreibung:"));
        beschreibungTextArea = new JTextArea();
        beschreibungTextArea.setAlignmentX(0f);
        beschreibungTextArea.setLineWrap(true);
        var scrollPane = new JScrollPane(beschreibungTextArea);
        scrollPane.setAlignmentX(0f);
        fieldsPanel.add(scrollPane);

        fieldsPanel.add(Box.createVerticalStrut(10));

        var imageButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        imageButtonPanel.add(bildHinzufuegenButton = new JButton("Bild hinzufügen..."));
        imageButtonPanel.add(bildLoeschenButton = new JButton("Ausgewähltes Bild löschen"));

        imageButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, imageButtonPanel.getPreferredSize().height));
        imageButtonPanel.setAlignmentX(0f);
        fieldsPanel.add(imageButtonPanel);

        fieldsPanel.add(Box.createGlue());

        fieldsPanel.add(saveHilfsmittelDetailsButton = new JButton("Änderungen speichern"));

        fieldsPanel.add(Box.createVerticalStrut(350));

        panel.add(fieldsPanel);

        return panel;
    }

    private JPanel buildButtonBar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(backButton = new JButton("Zurück"));

        return panel;
    }
}
