package hospitalappointmentsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HospitalAppointmentSystem extends JFrame {

    private final String URL =
            "jdbc:mysql://localhost:3306/hospital_appointment_db";
    private final String USER = "root";
    private final String PASSWORD = "spoorthi@123";

    private final Color cream = new Color(247, 240, 232);
    private final Color mocha = new Color(139, 111, 92);
    private final Color darkBrown = new Color(112, 87, 71);
    private final Color beige = new Color(229, 213, 197);

    private JTextField idField, patientField, ageField;
    private JTextField doctorField, dateField;
    private JTextField timeField, reasonField;
    private JComboBox<String> genderBox;

    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    public HospitalAppointmentSystem() {

        setTitle("Hospital Appointment Management System");
        setSize(1450, 980);
        setMinimumSize(new Dimension(1000, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(cream);

        // HEADER
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(mocha);
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel title = new JLabel(
                "HOSPITAL APPOINTMENT MANAGEMENT SYSTEM",
                SwingConstants.CENTER);

        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Your Health, Our Priority",
                SwingConstants.CENTER);

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(cream);

        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // MAIN CONTENT
        JPanel content = new JPanel(new BorderLayout(10, 15));
        content.setBackground(cream);
        content.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // APPOINTMENT FORM
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 12, 12));
        formPanel.setBackground(cream);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(beige, 2),
                "Appointment Details",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 18),
                darkBrown));

        idField = new JTextField();
        patientField = new JTextField();
        ageField = new JTextField();
        genderBox = new JComboBox<>(
                new String[]{"female", "male", "other"});
        doctorField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        reasonField = new JTextField();

        idField.setEditable(false);

        addFormField(formPanel, "Appointment ID:", idField);
        addFormField(formPanel, "Patient Name:", patientField);

        addFormField(formPanel, "Age:", ageField);
        addFormField(formPanel, "Gender:", genderBox);

        addFormField(formPanel, "Doctor Name:", doctorField);
        addFormField(formPanel, "Date (YYYY-MM-DD):", dateField);

        addFormField(formPanel, "Time:", timeField);
        addFormField(formPanel, "Reason:", reasonField);

        content.add(formPanel, BorderLayout.NORTH);

        // BUTTONS
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(cream);

        JButton addButton = createButton("Add Appointment");
        JButton viewButton = createButton("View Appointments");
        JButton updateButton = createButton("Update Appointment");
        JButton deleteButton = createButton("Delete Appointment");
        JButton resetButton = createButton("Reset All");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);

        content.add(buttonPanel, BorderLayout.CENTER);

        // TABLE
        String[] columns = {
            "ID", "Patient", "Age", "Gender",
            "Doctor", "Date", "Time", "Reason"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(38);
        appointmentTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 15));

        appointmentTable.setSelectionBackground(beige);
        appointmentTable.setSelectionForeground(darkBrown);

        appointmentTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 16));

        appointmentTable.getTableHeader().setBackground(mocha);
        appointmentTable.getTableHeader().setForeground(Color.WHITE);
        appointmentTable.getTableHeader().setPreferredSize(
                new Dimension(100, 42));

        JScrollPane scroll = new JScrollPane(appointmentTable);
        scroll.setBorder(BorderFactory.createLineBorder(beige, 2));

        content.add(scroll, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);

        // BUTTON ACTIONS
        addButton.addActionListener(e -> addAppointment());

        viewButton.addActionListener(e -> viewAppointments());

        updateButton.addActionListener(e -> updateAppointment());

        deleteButton.addActionListener(e -> deleteAppointment());

        resetButton.addActionListener(e -> resetFields());

        // TABLE ROW SELECTION
        appointmentTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                int row = appointmentTable.getSelectedRow();

                if (row != -1) {
                    idField.setText(
                            tableModel.getValueAt(row, 0).toString());

                    patientField.setText(
                            tableModel.getValueAt(row, 1).toString());

                    ageField.setText(
                            tableModel.getValueAt(row, 2).toString());

                    genderBox.setSelectedItem(
                            tableModel.getValueAt(row, 3).toString());

                    doctorField.setText(
                            tableModel.getValueAt(row, 4).toString());

                    dateField.setText(
                            tableModel.getValueAt(row, 5).toString());

                    timeField.setText(
                            tableModel.getValueAt(row, 6).toString());

                    reasonField.setText(
                            tableModel.getValueAt(row, 7).toString());
                }
            }
        });

        viewAppointments();
    }

    // DATABASE CONNECTION

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // FORM FIELDS

    private void addFormField(
            JPanel panel, String label, JComponent field) {

        JLabel text = new JLabel(label);

        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        text.setForeground(darkBrown);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(beige),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        panel.add(text);
        panel.add(field);
    }

    // BUTTON DESIGN

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(mocha);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(
                12, 18, 12, 18));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // VALIDATE INPUT

    private boolean validateFields() {

        if (patientField.getText().trim().isEmpty()
                || ageField.getText().trim().isEmpty()
                || doctorField.getText().trim().isEmpty()
                || dateField.getText().trim().isEmpty()
                || timeField.getText().trim().isEmpty()
                || reasonField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields.");

            return false;
        }

        try {

            int age = Integer.parseInt(ageField.getText().trim());

            if (age <= 0 || age > 120) {
                JOptionPane.showMessageDialog(this,
                        "Enter a valid age.");
                return false;
            }

            Date.valueOf(dateField.getText().trim());

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(this,
                    "Enter valid age and date (YYYY-MM-DD).");

            return false;
        }

        return true;
    }

    // ADD APPOINTMENT

    private void addAppointment() {

        if (!validateFields()) {
            return;
        }

        String sql = "INSERT INTO appointments "
                + "(patient_name, age, gender, doctor_name, "
                + "appointment_date, appointment_time, reason) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patientField.getText().trim());
            ps.setInt(2, Integer.parseInt(ageField.getText().trim()));
            ps.setString(3, genderBox.getSelectedItem().toString());
            ps.setString(4, doctorField.getText().trim());
            ps.setDate(5,
                    Date.valueOf(dateField.getText().trim()));
            ps.setString(6, timeField.getText().trim());
            ps.setString(7, reasonField.getText().trim());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Appointment Added Successfully!");

            resetFields();
            viewAppointments();

        } catch (SQLException e) {
            showError(e);
        }
    }

    // VIEW APPOINTMENTS

    private void viewAppointments() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT * FROM appointments ORDER BY appointment_id";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                tableModel.addRow(new Object[]{
                    rs.getInt("appointment_id"),
                    rs.getString("patient_name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("doctor_name"),
                    rs.getDate("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("reason")
                });
            }

        } catch (SQLException e) {
            showError(e);
        }
    }

    // UPDATE APPOINTMENT

    private void updateAppointment() {

        if (idField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please select an appointment from the table.");

            return;
        }

        if (!validateFields()) {
            return;
        }

        String sql = "UPDATE appointments SET "
                + "patient_name=?, age=?, gender=?, doctor_name=?, "
                + "appointment_date=?, appointment_time=?, reason=? "
                + "WHERE appointment_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patientField.getText().trim());
            ps.setInt(2, Integer.parseInt(ageField.getText().trim()));
            ps.setString(3, genderBox.getSelectedItem().toString());
            ps.setString(4, doctorField.getText().trim());
            ps.setDate(5,
                    Date.valueOf(dateField.getText().trim()));
            ps.setString(6, timeField.getText().trim());
            ps.setString(7, reasonField.getText().trim());
            ps.setInt(8, Integer.parseInt(idField.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Appointment Updated Successfully!");

            resetFields();
            viewAppointments();

        } catch (SQLException e) {
            showError(e);
        }
    }

    // DELETE APPOINTMENT

    private void deleteAppointment() {

        if (idField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please select an appointment from the table.");

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this appointment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM appointments WHERE appointment_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(idField.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Appointment Deleted Successfully!");

            resetFields();
            viewAppointments();

        } catch (SQLException e) {
            showError(e);
        }
    }

    // RESET FIELDS

    private void resetFields() {

        idField.setText("");
        patientField.setText("");
        ageField.setText("");
        genderBox.setSelectedIndex(0);
        doctorField.setText("");
        dateField.setText("");
        timeField.setText("");
        reasonField.setText("");

        appointmentTable.clearSelection();
    }

    // ERROR MESSAGE

    private void showError(Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage(),
                "System Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // MAIN METHOD

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new HospitalAppointmentSystem().setVisible(true);

        });
    }
}