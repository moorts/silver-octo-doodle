package model.ui;

import model.Event;
import model.Status;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class EventTableModel implements TableModel {

    private static final String[] columns = new String[] { "Name", "Kategorie", "Status" };
    private static final Class<?>[] columnTypes = new Class<?>[] { String.class, String.class, Status.class };

    public List<Event> events;

    public EventTableModel(Collection<Event> events) {
        this.events = new ArrayList<>(events);
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return events.get(rowIndex).getTitel();
            case 1: return events.get(rowIndex).getKategorie();
            case 2: return events.get(rowIndex).getStatus();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public Event getRowAt(int rowIndex) {
        return events.get(rowIndex);
    }
}
