package expense_income_tracker;

public class ExpenseIncomeEntry {
    private String date;
    private String description;
    private double amount;
    private String type;

    public ExpenseIncomeEntry(String date, String description, double amount, String type) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return date + "," + description + "," + amount + "," + type;
    }

    public static ExpenseIncomeEntry fromString(String line) {
        String[] parts = line.split(",");
        return new ExpenseIncomeEntry(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
    }
}