/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package personalexpensetracker;

/**
 *
 * @author Spoorthi K P
 */

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class PersonalExpenseTracker extends JFrame {

    JTextField id, title, amount, category, date;
    JTextArea result;

    public PersonalExpenseTracker() {
        setTitle("Personal Expense Tracker");
        setSize(750, 600);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Expense ID:"));
        id = new JTextField(10);
        add(id);

        add(new JLabel("Title:"));
        title = new JTextField(12);
        add(title);

        add(new JLabel("Amount:"));
        amount = new JTextField(10);
        add(amount);

        add(new JLabel("Category:"));
        category = new JTextField(12);
        add(category);

        add(new JLabel("Date (YYYY-MM-DD):"));
        date = new JTextField(12);
        add(date);

        JButton addButton = new JButton("Insert Expense");
        JButton viewButton = new JButton("View Expenses");
        JButton updateButton = new JButton("Update Expense");
        JButton deleteButton = new JButton("Delete Expense");
        JButton resetButton = new JButton("Reset All Expenses");

        add(addButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);
        add(resetButton);

        result = new JTextArea(20, 85);
        result.setEditable(false);
        result.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(result));

        addButton.addActionListener(e -> addExpense());
        viewButton.addActionListener(e -> viewExpenses());
        updateButton.addActionListener(e -> updateExpense());
        deleteButton.addActionListener(e -> deleteExpense());
        resetButton.addActionListener(e -> resetExpenses());

        setLocationRelativeTo(null);
        setVisible(true);

        viewExpenses();
    }

    void addExpense() {
        String sql = "INSERT INTO expenses "
                + "(title, amount, category, expense_date) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title.getText().trim());
            ps.setDouble(2, Double.parseDouble(amount.getText().trim()));
            ps.setString(3, category.getText().trim());
            ps.setDate(4, java.sql.Date.valueOf(date.getText().trim()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Expense Inserted!");
            clearFields();
            viewExpenses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Insert Error: " + e.getMessage());
        }
    }

    void viewExpenses() {
        result.setText("");

        String sql = "SELECT * FROM expenses ORDER BY expense_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            result.append(String.format(
                    "%-5s %-15s %-12s %-15s %-12s%n",
                    "ID", "Title", "Amount", "Category", "Date"));

            result.append(
                    "---------------------------------------------------------------\n");

            while (rs.next()) {
                result.append(String.format(
                        "%-5d %-15s %-12.2f %-15s %-12s%n",
                        rs.getInt("expense_id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getDate("expense_date")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "View Error: " + e.getMessage());
        }
    }

    void updateExpense() {
        String sql = "UPDATE expenses SET title=?, amount=?, "
                + "category=?, expense_date=? WHERE expense_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title.getText().trim());
            ps.setDouble(2, Double.parseDouble(amount.getText().trim()));
            ps.setString(3, category.getText().trim());
            ps.setDate(4, java.sql.Date.valueOf(date.getText().trim()));
            ps.setInt(5, Integer.parseInt(id.getText().trim()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Expense Updated!");
                clearFields();
                viewExpenses();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Expense ID not found!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Update Error: " + e.getMessage());
        }
    }

    void deleteExpense() {
        try {
            int expenseId = Integer.parseInt(id.getText().trim());

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete expense ID " + expenseId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "DELETE FROM expenses WHERE expense_id=?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, expenseId);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Expense Deleted!");
                    clearFields();
                    viewExpenses();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Expense ID not found!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Delete Error: " + e.getMessage());
        }
    }

    void resetExpenses() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "This will permanently delete ALL expenses. Continue?",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate("TRUNCATE TABLE expenses");

            JOptionPane.showMessageDialog(this,
                    "All expenses deleted. The ID counter is reset.");

            clearFields();
            viewExpenses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Reset Error: " + e.getMessage());
        }
    }

    void clearFields() {
        id.setText("");
        title.setText("");
        amount.setText("");
        category.setText("");
        date.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PersonalExpenseTracker());
    }
}
