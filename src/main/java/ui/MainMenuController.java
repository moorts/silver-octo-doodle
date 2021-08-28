package ui;

import model.Event;
import model.Hilfsmittel;
import model.ui.EventTableModel;
import model.ui.HilfsmittelTableModel;
import ui.base.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuController extends Controller<MainMenuView> {

    public MainMenuController(MainMenuView view) {
        super(view);
    }

    @Override
    public void init() {
        view.eventTable.setModel(new EventTableModel(this.application.getEventEntityManager().getAll()));
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

        view.hilfsmittelTable.setModel(new HilfsmittelTableModel(this.application.getHilfsmittelEntityManager().getAll()));
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
    }

}
