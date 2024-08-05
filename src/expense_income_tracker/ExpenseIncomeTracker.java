package expense_income_tracker;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseIncomeTracker extends JFrame {
    private final ExpenseIncomeTableModel tableModel;
    private final JTable table;
    private final JTextField dateField;
    private final JTextField descriptionField;
    private final JTextField amountField;
    private final JComboBox<String> typeCombobox;
    private final JButton addButton;
    private final JButton removeButton;
    private final JLabel balanceLabel;
    private double balance;

    public ExpenseIncomeTracker() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to Set FlatDarkLaf LookAndFeel");
        }

        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.DARK_GRAY);
        UIManager.put("TextField.caretForeground", Color.RED);
        UIManager.put("ComboBox.foreground", Color.YELLOW);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.selectionBackground", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", Color.ORANGE);
        UIManager.put("Label.foreground", Color.WHITE);

        balance = 0.0;
        tableModel = new ExpenseIncomeTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        dateField = new JTextField(10);
        descriptionField = new JTextField(20);
        amountField = new JTextField(10);
        typeCombobox = new JComboBox<>(new String[]{"Expense", "Income"});

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        balanceLabel = new JLabel("Balance: ₹" + balance);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Description"));
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Type"));
        inputPanel.add(typeCombobox);

        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(balanceLabel);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Expenses And Income Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText();
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String type = (String) typeCombobox.getSelectedItem();

                ExpenseIncomeEntry entry = new ExpenseIncomeEntry(date, description, amount, type);
                tableModel.addEntry(entry);
                updateBalance(entry);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    ExpenseIncomeEntry entry = tableModel.getEntry(selectedRow);
                    tableModel.removeEntry(selectedRow);
                    updateBalanceOnRemoval(entry);
                }
            }
        });
    }

    private void updateBalance(ExpenseIncomeEntry entry) {
        if (entry.getType().equals("Income")) {
            balance += entry.getAmount();
        } else {
            balance -= entry.getAmount();
        }
        balanceLabel.setText("Balance: ₹" + balance);
    }

    private void updateBalanceOnRemoval(ExpenseIncomeEntry entry) {
        if (entry.getType().equals("Income")) {
            balance -= entry.getAmount();
        } else {
            balance += entry.getAmount();
        }
        balanceLabel.setText("Balance: ₹" + balance);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseIncomeTracker::new);
    }
}
