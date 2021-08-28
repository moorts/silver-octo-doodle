package model.ui;

import model.Event;
import model.Kontaktinformationen;
import model.Status;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KontaktinformationenTableModel implements TableModel {

    private static final String[] columns = new String[] { "Name", "Email", "Tel. Nr" };
    private static final Class<?>[] columnTypes = new Class<?>[] { String.class, String.class, String.class };

    public List<Kontaktinformationen> kontakte;

    private List<TableModelListener> listeners = new ArrayList<>();

    public KontaktinformationenTableModel(Collection<Kontaktinformationen> kontakte) {
        if (kontakte != null)
            this.kontakte = new ArrayList<>(kontakte);
        else
            this.kontakte = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return kontakte.size();
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
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return kontakte.get(rowIndex).getName();
            case 1: return kontakte.get(rowIndex).getEmail();
            case 2: return kontakte.get(rowIndex).getTelnr();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: kontakte.get(rowIndex).setName((String)aValue); break;
            case 1: kontakte.get(rowIndex).setEmail((String)aValue); break;
            case 2: kontakte.get(rowIndex).setTelnr((String)aValue); break;
            default:
                return;
        }
        for (var l : listeners) {
            l.tableChanged(new TableModelEvent(this, rowIndex));
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public Kontaktinformationen getRowAt(int rowIndex) {
        return kontakte.get(rowIndex);
    }

    public Collection<Kontaktinformationen> getAllRows() {
        return kontakte;
    }

    public void addNewRow() {
        kontakte.add(new Kontaktinformationen("Name", "Email", "Telefon"));
        for (var l : listeners) {
            l.tableChanged(new TableModelEvent(this, kontakte.size() - 1));
        }
    }

    public void removeRow(int rowIndex) {
        kontakte.remove(rowIndex);
        for (var l : listeners) {
            l.tableChanged(new TableModelEvent(this, rowIndex));
        }
    }
}
