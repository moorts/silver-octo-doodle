package ui;

import model.Event;
import model.Hilfsmittel;
import model.factory.EventFactory;
import model.factory.HilfsmittelFactory;
import model.ui.EventTableModel;
import model.ui.HilfsmittelTableModel;
import ui.base.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainMenuController extends Controller<MainMenuView> {

    public MainMenuController(MainMenuView view) {
        super(view);
    }

    @Override
    public void init() {
        updateEventTableModel();
        updateHilfsmittelTableModel();

        view.eventTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() == row && row != -1) {
                    Event clickedEvent = ((EventTableModel)table.getModel()).getRowAt(row);
                    application.setView(new EventDetailView(clickedEvent));
                }
            }
        });

        view.hilfsmittelTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() == row && row != -1) {
                    Hilfsmittel clickedHilfsmittel = ((HilfsmittelTableModel)table.getModel()).getRowAt(row);
                    application.setView(new HilfsmittelDetailView(clickedHilfsmittel));
                }
            }
        });

        view.eventErstellenButton.addActionListener(e -> {
            String titel = JOptionPane.showInputDialog("Wie soll das neue Event heißen?");
            if (!titel.isBlank()) {
                Event neuesEvent = new EventFactory().createEvent(titel);
                try {
                    application.getEventEntityManager().persist(neuesEvent);
                    application.getEventEntityManager().saveToJson();
                    application.setView(new EventDetailView(neuesEvent));
                } catch (Exception exception) {

                }
            }
        });

        view.eventLoeschenButton.addActionListener(e -> {
            int selectedRow = view.eventTable.getSelectedRow();

            if (selectedRow == -1) return;

            if (JOptionPane.showConfirmDialog(null, "Möchten Sie das Event wirklich löschen?", "Sind Sie sicher?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.application.getEventEntityManager().remove(((EventTableModel)view.eventTable.getModel()).getRowAt(selectedRow));
                try {
                    this.application.getEventEntityManager().saveToJson();
                    updateEventTableModel();
                } catch (IOException ioException) {
                }
            }
        });

        view.hilfsmittelErstellenButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Wie soll das neue Hilfsmittel heißen?");
            if (!name.isBlank()) {
                Hilfsmittel neuesHilfsmittel = new HilfsmittelFactory().createHilfsmittel(name);
                try {
                    application.getHilfsmittelEntityManager().persist(neuesHilfsmittel);
                    application.getHilfsmittelEntityManager().saveToJson();
                    application.setView(new HilfsmittelDetailView(neuesHilfsmittel));
                } catch (Exception exception) {

                }
            }
        });

        view.hilfsmittelLoeschenButton.addActionListener(e -> {
            int selectedRow = view.hilfsmittelTable.getSelectedRow();

            if (selectedRow == -1) return;

            if (JOptionPane.showConfirmDialog(null, "Möchten Sie das Hilfsmittel wirklich löschen?", "Sind Sie sicher?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.application.getHilfsmittelEntityManager().remove(((HilfsmittelTableModel)view.hilfsmittelTable.getModel()).getRowAt(selectedRow));
                try {
                    this.application.getHilfsmittelEntityManager().saveToJson();
                    updateHilfsmittelTableModel();
                } catch (IOException ioException) {
                }
            }
        });
    }

    private void updateEventTableModel() {
        view.eventTable.setModel(new EventTableModel(this.application.getEventEntityManager().getAll()));
        view.eventTable.updateUI();
    }

    private void updateHilfsmittelTableModel() {
        view.hilfsmittelTable.setModel(new HilfsmittelTableModel(this.application.getHilfsmittelEntityManager().getAll()));
        view.hilfsmittelTable.updateUI();
    }
}
