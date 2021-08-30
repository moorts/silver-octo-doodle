package ui;

import de.dhbwka.swe.utils.gui.SlideshowComponent;
import model.*;
import model.Event;
import ui.base.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.Flow;

public class EventDetailView extends View<EventDetailController> {
    public EventDetailView(Event event) {
        this.controller = new EventDetailController(this, event);
    }

    public SlideshowComponent slideshow;
    public JLabel headerLabel;
    public JLabel startDateLabel;
    public JLabel endDateLabel;
    public JButton backButton;
    public JLabel statusLabel;
    public JTextArea kontaktpersonenTextArea;
    public JTextArea beschreibungReadOnlyTextArea;

    public JTextField titelTextField;
    public JTextField kategorieTextField;
    public JTextArea beschreibungTextArea;
    public JButton saveEventDetailsButton;
    public JComboBox<Status> statusComboBox;
    public JTable kontakteTable;
    public JButton neuerKontaktButton;
    public JButton kontaktLoeschenButton;
    public JTextField startTextField;
    public JTextField endeTextField;
    public JButton bildHinzufuegenButton;
    public JButton bildLoeschenButton;

    public JTable zuweisungsTable;
    public JComboBox<Hilfsmittel> hilfsmittelComboBox;
    public JSpinner hilfsmittelZuweisungSpinner;
    public JButton zuweisenButton;
    public JButton zuweisungLoeschenButton;

    public JButton elementHinzufuegenButton;
    public JComboBox<ElementType> elementHinzufuegenCombobox;

    private JPanel elementPreviewPanel;

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

    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        slideshow = SlideshowComponent.builder("eventDetailSlideshow").build();
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

        labelPanel.add(startDateLabel = new JLabel(String.format(EventDetailController.LABEL_START_DATE, "")));
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(endDateLabel = new JLabel(String.format(EventDetailController.LABEL_END_DATE, "")));

        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(new JLabel("<html><u>Kontaktpersonen:</u></html>"));
        kontaktpersonenTextArea = new JTextArea("");
        kontaktpersonenTextArea.setEditable(false);
        var kontaktScrollPane = new JScrollPane(kontaktpersonenTextArea);
        kontaktScrollPane.setAlignmentX(0f);
        kontaktScrollPane.setAlignmentY(0.5f);
        labelPanel.add(kontaktScrollPane);

        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(new JLabel("<html><u>Beschreibung:</u></html>"));
        beschreibungReadOnlyTextArea = new JTextArea("");
        beschreibungReadOnlyTextArea.setEditable(false);
        beschreibungReadOnlyTextArea.setLineWrap(true);
        var beschreibungScrollPane = new JScrollPane(beschreibungReadOnlyTextArea);
        beschreibungScrollPane.setAlignmentX(0f);
        labelPanel.add(beschreibungScrollPane);

        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(statusLabel = new JLabel(String.format(EventDetailController.LABEL_STATUS, "")));

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Elemente", buildElementeTab());
        tabbedPane.addTab("Details", buildDetailsTab());
        tabbedPane.addTab("Hilfsmittel", buildHilfsmittelTab());
        panel.add(tabbedPane);
        return panel;
    }

    private JPanel buildHilfsmittelTab() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        zuweisungsTable = new JTable();
        panel.add(new JScrollPane(zuweisungsTable));

        JPanel zuweisungMenu = new JPanel();
        zuweisungMenu.setLayout(new BoxLayout(zuweisungMenu, BoxLayout.Y_AXIS));
        zuweisungMenu.setBorder(new EmptyBorder(5, 5, 5, 5));

        zuweisungMenu.add(new JLabel("Verfügbare Hilfsmittel:"));
        zuweisungMenu.add(hilfsmittelComboBox = new JComboBox<Hilfsmittel>());
        hilfsmittelComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, hilfsmittelComboBox.getPreferredSize().height));
        hilfsmittelComboBox.setAlignmentX(0f);

        zuweisungMenu.add(Box.createVerticalStrut(10));

        zuweisungMenu.add(new JLabel("Wie viele sollen zugewiesen werden?"));
        zuweisungMenu.add(hilfsmittelZuweisungSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)));
        hilfsmittelZuweisungSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, hilfsmittelZuweisungSpinner.getPreferredSize().height));
        hilfsmittelZuweisungSpinner.setAlignmentX(0f);

        zuweisungMenu.add(Box.createVerticalStrut(10));

        zuweisungMenu.add(zuweisenButton = new JButton("Hilfsmittel zuweisen"));

        zuweisungMenu.add(Box.createVerticalStrut(10));

        zuweisungMenu.add(zuweisungLoeschenButton = new JButton("Ausgewählte Zuweisung löschen"));

        panel.add(zuweisungMenu);

        return panel;
    }

    private JPanel buildDetailsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        //JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //buttonBar.add(saveEventDetailsButton = new JButton("Änderungen speichern"));
        //panel.add(buttonBar, BorderLayout.SOUTH);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        fieldsPanel.add(new JLabel("Titel:"));
        titelTextField = new JTextField();
        titelTextField.setAlignmentX(0f);
        titelTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titelTextField.getPreferredSize().height));
        fieldsPanel.add(titelTextField);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Kategorie:"));
        kategorieTextField = new JTextField();
        kategorieTextField.setAlignmentX(0f);
        kategorieTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, kategorieTextField.getPreferredSize().height));
        fieldsPanel.add(kategorieTextField);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Beschreibung:"));
        beschreibungTextArea = new JTextArea();
        beschreibungTextArea.setAlignmentX(0f);
        beschreibungTextArea.setLineWrap(true);
        var scrollPane = new JScrollPane(beschreibungTextArea);
        scrollPane.setAlignmentX(0f);
        fieldsPanel.add(scrollPane);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Status:"));
        fieldsPanel.add(statusComboBox = new JComboBox<Status>(Status.values()));
        statusComboBox.setAlignmentX(0f);
        statusComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusComboBox.getPreferredSize().height));

        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(new JLabel("Kontaktinformationen:"));
        JScrollPane tableScrollPane;
        fieldsPanel.add(tableScrollPane = new JScrollPane(kontakteTable = new JTable()));
        tableScrollPane.setAlignmentX(0f);
        tableScrollPane.setPreferredSize(new Dimension(tableScrollPane.getPreferredSize().width, 50));
        fieldsPanel.add(Box.createVerticalStrut(5));
        JPanel kontaktButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        kontaktButtonsPanel.add(neuerKontaktButton = new JButton("Neuen Kontakt hinzufügen"));
        kontaktButtonsPanel.add(kontaktLoeschenButton = new JButton("Ausgewählten Kontakt löschen"));
        kontaktButtonsPanel.setAlignmentX(0f);
        kontaktButtonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, kontaktButtonsPanel.getPreferredSize().height));
        fieldsPanel.add(kontaktButtonsPanel);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Startzeitpunkt (YYYY-MM-DD HH:mm):"));
        startTextField = new JTextField();
        startTextField.setAlignmentX(0f);
        startTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, startTextField.getPreferredSize().height));
        fieldsPanel.add(startTextField);

        fieldsPanel.add(Box.createVerticalStrut(10));

        fieldsPanel.add(new JLabel("Endzeitpunkt (YYYY-MM-DD HH:mm):"));
        endeTextField = new JTextField();
        endeTextField.setAlignmentX(0f);
        endeTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, endeTextField.getPreferredSize().height));
        fieldsPanel.add(endeTextField);

        fieldsPanel.add(Box.createVerticalStrut(10));

        var imageButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        imageButtonPanel.add(bildHinzufuegenButton = new JButton("Bild hinzufügen..."));
        imageButtonPanel.add(bildLoeschenButton = new JButton("Ausgewähltes Bild löschen"));

        imageButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, imageButtonPanel.getPreferredSize().height));
        imageButtonPanel.setAlignmentX(0f);
        fieldsPanel.add(imageButtonPanel);

        fieldsPanel.add(Box.createGlue());

        fieldsPanel.add(saveEventDetailsButton = new JButton("Änderungen speichern"));

        panel.add(new JScrollPane(fieldsPanel));

        return panel;
    }

    private JPanel buildElementeTab() {
        JPanel panel = new JPanel(new BorderLayout());

        elementPreviewPanel = new JPanel();
        elementPreviewPanel.setLayout(new BoxLayout(elementPreviewPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane;
        panel.add(scrollPane = new JScrollPane(elementPreviewPanel));

        JPanel addElementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addElementPanel.add(elementHinzufuegenCombobox = new JComboBox<ElementType>(ElementType.values()));
        addElementPanel.add(elementHinzufuegenButton = new JButton("Element hinzufügen"));
        panel.add(addElementPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildButtonBar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new JScrollPane().getBorder());

        panel.add(backButton = new JButton("Zurück"));

        return panel;
    }

    public void addEventElementDetailPanel(TeilEvent teilEvent) {
        elementPreviewPanel.add(teilEvent.getPreviewPanel());
        elementPreviewPanel.updateUI();
    }

    public void removeAllEventElementDetailPanels() {
        elementPreviewPanel.removeAll();
        elementPreviewPanel.updateUI();
    }
}
