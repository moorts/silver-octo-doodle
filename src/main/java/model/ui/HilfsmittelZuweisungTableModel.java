package model.ui;

import model.Hilfsmittel;
import model.Zuweisung;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HilfsmittelZuweisungTableModel implements TableModel {

    private static final String[] columns = new String[] { "Hilfsmittel", "Zugewiesen" };
    private static final Class<?>[] columnTypes = new Class<?>[] { String.class, Integer.class };

    public List<Zuweisung> zuweisungen;

    public HilfsmittelZuweisungTableModel(Collection<Zuweisung> zuweisungen) {
        this.zuweisungen = new ArrayList<>(zuweisungen);
    }

    @Override
    public int getRowCount() {
        return this.zuweisungen.size();
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
            case 0: return zuweisungen.get(rowIndex).getHilfsmittel().getName();
            case 1: return zuweisungen.get(rowIndex).getMenge();
        }

        return null;
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
}
