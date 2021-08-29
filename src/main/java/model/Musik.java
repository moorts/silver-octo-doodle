package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Musik extends EventElement {
    private String kuenstler;
    private String programm;

    private transient JTextArea kuenstlerTextArea;
    private transient JTextArea programmTextArea;

    @Override
    public ElementType getType() {
        return ElementType.MUSIK;
    }

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        panel.add(new JLabel("Künstler:"));
        kuenstlerTextArea = new JTextArea(kuenstler);
        var kuenstlerScrollPane = new JScrollPane(kuenstlerTextArea);
        kuenstlerScrollPane.setAlignmentX(0f);
        panel.add(kuenstlerScrollPane);

        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Programm:"));
        programmTextArea = new JTextArea(programm);
        var programmScrollPane = new JScrollPane(programmTextArea);
        programmScrollPane.setAlignmentX(0f);
        panel.add(programmScrollPane);

        return panel;
    }

    @Override
    public void saveChanges() {
        kuenstler = kuenstlerTextArea.getText();
        programm = programmTextArea.getText();
    }
}
