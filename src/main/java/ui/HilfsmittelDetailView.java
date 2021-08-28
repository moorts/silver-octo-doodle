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

    public JLabel statusLabel;

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel leftrightPanel = new JPanel();
        leftrightPanel.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = buildLeftPanel();
        JPanel rightPanel = buildRightPanel();

        leftrightPanel.add(leftPanel);
        //leftrightPanel.add(rightPanel);
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

        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(statusLabel = new JLabel(String.format(EventDetailController.LABEL_STATUS, "")));

        return panel;
    }

    public JPanel buildRightPanel() {
        return null;
    }

    private JPanel buildButtonBar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(backButton = new JButton("Zurück"));

        return panel;
    }
}
