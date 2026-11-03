/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalappointmentsystem;

/**
 *
 * @author Spoorthi K P
 */
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    JTextField username;
    JPasswordField password;

    Color cream = new Color(247, 240, 232);
    Color mocha = new Color(139, 111, 92);
    Color darkBrown = new Color(112, 87, 71);
    Color beige = new Color(229, 213, 197);

    public LoginFrame() {

        setTitle("Hospital Appointment System - Login");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(mocha);
        leftPanel.setLayout(new GridBagLayout());

        JPanel leftContent = new JPanel();
        leftContent.setOpaque(false);
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("HOSPITAL");
        hospital.setFont(new Font("Segoe UI", Font.BOLD, 30));
        hospital.setForeground(Color.WHITE);

        JLabel system = new JLabel("APPOINTMENT SYSTEM");
        system.setFont(new Font("Segoe UI", Font.BOLD, 19));
        system.setForeground(Color.WHITE);

        JLabel message = new JLabel(
                "<html>Your Health,<br>Our Priority.</html>");
        message.setFont(new Font("Segoe UI", Font.BOLD, 25));
        message.setForeground(cream);

        JLabel description = new JLabel(
                "<html>Manage your hospital appointments<br>"
                + "easily and efficiently.</html>");
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(cream);

        leftContent.add(hospital);
        leftContent.add(Box.createVerticalStrut(5));
        leftContent.add(system);
        leftContent.add(Box.createVerticalStrut(65));
        leftContent.add(message);
        leftContent.add(Box.createVerticalStrut(15));
        leftContent.add(description);

        leftPanel.add(leftContent);

        // RIGHT PANEL
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(cream);

        JPanel form = new JPanel();
        form.setBackground(cream);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(darkBrown);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Login to your account");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(darkBrown);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(darkBrown);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        username = new JTextField(18);
        styleField(username);

        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(darkBrown);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        password = new JPasswordField(18);
        styleField(password);

        JButton loginButton = new JButton("SIGN IN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setBackground(mocha);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(300, 45));

        form.add(welcome);
        form.add(Box.createVerticalStrut(8));
        form.add(subtitle);
        form.add(Box.createVerticalStrut(35));
        form.add(userLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(username);
        form.add(Box.createVerticalStrut(20));
        form.add(passLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(password);
        form.add(Box.createVerticalStrut(30));
        form.add(loginButton);

        rightPanel.add(form);

        add(leftPanel);
        add(rightPanel);

        loginButton.addActionListener(e -> login());

        password.addActionListener(e -> login());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(Color.WHITE);
        field.setForeground(darkBrown);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(beige, 2),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setMaximumSize(new Dimension(300, 42));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    void login() {

        String user = username.getText().trim();
        String pass = new String(password.getPassword());

        // Demo login credentials
        if (user.equals("admin") && pass.equals("hospital123")) {

            JOptionPane.showMessageDialog(this,
                    "Login Successful!");

            dispose();

            new HospitalAppointmentSystem();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Invalid Username or Password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}