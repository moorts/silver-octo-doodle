package ui;

import model.Event;
import model.ui.EventTableModel;
import ui.base.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuView extends View<MainMenuController> {
    public MainMenuView() {
        this.controller = new MainMenuController(this);
    }

    public JTable eventTable;
    public JTable hilfsmittelTable;
    public JButton eventErstellenButton;

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane(SwingConstants.TOP);
        tabPane.addTab("Events", getEventListe());
        tabPane.addTab("Hilfsmittel", getHilfsmittelListe());
        panel.add(tabPane);

        return panel;
    }

    private JPanel getEventListe() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        eventTable = new JTable();
        panel.add(new JScrollPane(eventTable));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(eventErstellenButton = new JButton("Neues Event erstellen"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getHilfsmittelListe() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        hilfsmittelTable = new JTable();
        panel.add(new JScrollPane(hilfsmittelTable));

        return panel;
    }
}
