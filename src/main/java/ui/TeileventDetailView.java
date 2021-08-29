package ui;

import de.dhbwka.swe.utils.gui.SlideshowComponent;
import model.Event;
import model.Status;
import model.TeilEvent;
import ui.base.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Field;

public class TeileventDetailView extends View<TeileventDetailController> {

    public JButton zurueckButton;
    public JButton speichernButton;
    public SlideshowComponent slideshow;
    public JButton addBildButton;
    public JButton removeBildButton;
    public JTextArea beschreibungTextArea;
    public JComboBox<Status> statusComboBox;
    public JTextField startTextField;
    public JTextField endeTextField;
    public JTextField nameTextField;

    private Event event;
    private TeilEvent teilevent;

    public TeileventDetailView(Event event, TeilEvent teilevent) {
        this.event = event;
        this.teilevent = teilevent;
        this.controller = new TeileventDetailController(this, event, teilevent);
    }

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Details", buildDetailsTab());
        panel.add(tabbedPane);

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonBar.setBorder(new JScrollPane().getBorder());
        buttonBar.add(zurueckButton = new JButton("Zurück"));
        buttonBar.add(speichernButton = new JButton("Änderungen speichern"));
        panel.add(buttonBar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildDetailsTab() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(buildLeftPanel());
        panel.add(buildRightPanel());
        return panel;
    }

    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        slideshow = SlideshowComponent.builder("teileventSlideshow").build();
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

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel imageButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imageButtonPanel.setAlignmentX(0f);
        imageButtonPanel.add(addBildButton = new JButton("Bild hinzufügen..."));
        imageButtonPanel.add(removeBildButton = new JButton("Ausgewähltes Bild löschen"));
        imageButtonPanel.setMaximumSize(imageButtonPanel.getPreferredSize());
        bottomPanel.add(imageButtonPanel);

        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        nameTextField.setAlignmentX(0f);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        bottomPanel.add(nameTextField);

        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(new JLabel("Beschreibung:"));
        beschreibungTextArea = new JTextArea();
        beschreibungTextArea.setAlignmentX(0f);
        beschreibungTextArea.setLineWrap(true);
        var textAreaScrollPane = new JScrollPane(beschreibungTextArea);
        textAreaScrollPane.setAlignmentX(0f);
        bottomPanel.add(textAreaScrollPane);

        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(new JLabel("Startzeitpunkt (YYYY-MM-DD HH:mm):"));
        startTextField = new JTextField();
        startTextField.setAlignmentX(0f);
        startTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, startTextField.getPreferredSize().height));
        bottomPanel.add(startTextField);

        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(new JLabel("Endzeitpunkt (YYYY-MM-DD HH:mm):"));
        endeTextField = new JTextField();
        endeTextField.setAlignmentX(0f);
        endeTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, endeTextField.getPreferredSize().height));
        bottomPanel.add(endeTextField);

        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<Status>(Status.values());
        statusComboBox.setAlignmentX(0f);
        statusComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusComboBox.getPreferredSize().height));
        bottomPanel.add(statusComboBox);

        panel.add(bottomPanel);


        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel();

        return panel;
    }

}
