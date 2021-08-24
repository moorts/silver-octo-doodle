package ui;

import model.Event;
import model.ui.EventTableModel;
import ui.base.View;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuView extends View<MainMenuController> {
    public MainMenuView() {
        this.controller = new MainMenuController(this);
    }

    public JTable eventTable;

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

        return panel;
    }

    private JPanel getHilfsmittelListe() {
        return new JPanel();
    }
}