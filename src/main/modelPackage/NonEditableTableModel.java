package main.modelPackage;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(String[] columnNames, int i) {
        super(columnNames, i);
    }

    // overload pour le listing des user dans CRUD
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
