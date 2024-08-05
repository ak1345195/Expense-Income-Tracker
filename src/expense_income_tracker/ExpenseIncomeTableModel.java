package expense_income_tracker;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ExpenseIncomeTableModel extends AbstractTableModel {
    private final List<ExpenseIncomeEntry> entries;
    private final String[] columnNames = {"Date", "Description", "Amount", "Type"};
    private static final String FILE_PATH = "entries.txt";

    public ExpenseIncomeTableModel() {
        entries = new ArrayList<>();
        loadEntries();
    }

    public void addEntry(ExpenseIncomeEntry entry) {
        entries.add(entry);
        fireTableRowsInserted(entries.size() - 1, entries.size() - 1);
        saveEntries();
    }

    public void removeEntry(int rowIndex) {
        entries.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
        saveEntries();
    }

    public ExpenseIncomeEntry getEntry(int rowIndex) {
        return entries.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ExpenseIncomeEntry entry = entries.get(rowIndex);

        switch (columnIndex) {
            case 0: return entry.getDate();
            case 1: return entry.getDescription();
            case 2: return entry.getAmount();
            case 3: return entry.getType();
            default: return null;
        }
    }

    private void loadEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entries.add(ExpenseIncomeEntry.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEntries() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ExpenseIncomeEntry entry : entries) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
