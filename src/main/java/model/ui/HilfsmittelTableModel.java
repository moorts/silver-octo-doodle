package model.ui;

        import model.Event;
        import model.Hilfsmittel;

        import javax.swing.event.TableModelListener;
        import javax.swing.table.TableModel;
        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.Date;
        import java.util.List;

public class HilfsmittelTableModel implements TableModel {

    private static final String[] columns = new String[] { "Name", "Aktuell verfügbar", "Insgesamt verfügbar" };
    private static final Class<?>[] columnTypes = new Class<?>[] { String.class, Integer.class, Integer.class };

    public List<Hilfsmittel> hilfsmittel;

    public HilfsmittelTableModel(List<Hilfsmittel> hilfsmittel) {
        this.hilfsmittel = hilfsmittel;
    }

    public HilfsmittelTableModel(Collection<Hilfsmittel> hilfsmittel) {
        this.hilfsmittel = new ArrayList<>(hilfsmittel);
    }

    @Override
    public int getRowCount() {
        return hilfsmittel.size();
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
            case 0: return hilfsmittel.get(rowIndex).getName();
            case 1: return hilfsmittel.get(rowIndex).getAktuellVerfuegbar();
            case 2: return hilfsmittel.get(rowIndex).getInsgesamtVerfuegbar();
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

    public Hilfsmittel getRowAt(int rowIndex) {
        return hilfsmittel.get(rowIndex);
    }
}
