package main.modelPackage;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(String[] columnNames, int i) {
        super(columnNames, i);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
