package library1234;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class returnbook extends JFrame {

    private JFrame f;

    public returnbook() {
        f = new JFrame("Return Book");
        f.setSize(600, 500);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(144, 238, 144));  // Set background color of the frame

        // Labels
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(10, 10, 100, 50);
        bookIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bookIdLabel.setForeground(Color.DARK_GRAY);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(10, 80, 100, 50);
        studentIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        studentIdLabel.setForeground(Color.DARK_GRAY);

        JLabel issueDateLabel = new JLabel("Issue Date:");
        issueDateLabel.setBounds(10, 150, 100, 50);
        issueDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        issueDateLabel.setForeground(Color.DARK_GRAY);

        JLabel dueDateLabel = new JLabel("Due Date:");
        dueDateLabel.setBounds(10, 220, 100, 50);
        dueDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dueDateLabel.setForeground(Color.DARK_GRAY);

        JLabel returnDateLabel = new JLabel("Return Date (yyyy-mm-dd):");
        returnDateLabel.setBounds(10, 290, 200, 50);
        returnDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        returnDateLabel.setForeground(Color.DARK_GRAY);

        // Text Fields
        JTextField bookIdField = new JTextField();
        bookIdField.setBounds(110, 10, 200, 40);
        bookIdField.setFont(new Font("Arial", Font.BOLD, 18));
        bookIdField.setBackground(Color.PINK);

        JTextField studentIdField = new JTextField();
        studentIdField.setBounds(110, 80, 200, 40);
        studentIdField.setFont(new Font("Arial", Font.BOLD, 18));
        studentIdField.setBackground(Color.PINK);

        JTextField issueDateField = new JTextField();
        issueDateField.setBounds(110, 150, 200, 40);
        issueDateField.setEditable(false);
        issueDateField.setFont(new Font("Arial", Font.BOLD, 18));
        issueDateField.setBackground(Color.PINK);

        JTextField dueDateField = new JTextField();
        dueDateField.setBounds(110, 220, 200, 40);
        dueDateField.setEditable(false);
        dueDateField.setFont(new Font("Arial", Font.BOLD, 18));
        dueDateField.setBackground(Color.PINK);

        JTextField returnDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        returnDateField.setBounds(210, 290, 150, 40);
        returnDateField.setFont(new Font("Arial", Font.BOLD, 18));
        returnDateField.setBackground(Color.PINK);

        // Buttons
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(320, 10, 100, 40);
        searchButton.setBackground(Color.CYAN);
        searchButton.setFont(new Font("Arial", Font.BOLD, 18));

        JButton returnButton = new JButton("Return Book");
        returnButton.setBounds(110, 340, 200, 40);
        returnButton.setBackground(Color.BLUE);
        returnButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Add components to the frame
        f.add(bookIdLabel);
        f.add(bookIdField);
        f.add(studentIdLabel);
        f.add(studentIdField);
        f.add(issueDateLabel);
        f.add(issueDateField);
        f.add(dueDateLabel);
        f.add(dueDateField);
        f.add(returnDateLabel);
        f.add(returnDateField);
        f.add(searchButton);
        f.add(returnButton);

        // Search Button Action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String bookId = bookIdField.getText();
                    searchBookDetails(bookId, studentIdField, issueDateField, dueDateField);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Error: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Return Button Action
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String bookId = bookIdField.getText();
                    int studentId = Integer.parseInt(studentIdField.getText());
                    returnBook(bookId, studentId, bookIdField, studentIdField, issueDateField, dueDateField, returnDateField);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(f, "Please enter valid numeric values for Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        f.setVisible(true);
    }

    // Method to search book details by Book ID
    private void searchBookDetails(String bookId, JTextField studentIdField, JTextField issueDateField, JTextField dueDateField) {
        String query = "SELECT student_id, issue_date, due_date FROM issue WHERE book_id = ? AND return_date IS NULL";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setString(1, bookId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int studentId = rs.getInt("student_id");
                    Date issueDate = rs.getDate("issue_date");
                    Date dueDate = rs.getDate("due_date");

                    studentIdField.setText(String.valueOf(studentId));
                    issueDateField.setText(issueDate.toString());
                    dueDateField.setText(dueDate.toString());
                } else {
                    JOptionPane.showMessageDialog(f, "No records found for the provided Book ID.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(f, "Error searching for book: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to return the book and delete the record
    private void returnBook(String bookId, int studentId, JTextField bookIdField, JTextField studentIdField, JTextField issueDateField, JTextField dueDateField, JTextField returnDateField) {
        String query = "DELETE FROM issue WHERE book_id = ? AND student_id = ?";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setString(1, bookId);
                stmt.setInt(2, studentId);

                int rowsDeleted = stmt.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(f, "Book returned and record deleted successfully.", "Return Success", JOptionPane.INFORMATION_MESSAGE);
                    // Clear fields after returning the book
                    bookIdField.setText("");
                    studentIdField.setText("");
                    issueDateField.setText("");
                    dueDateField.setText("");
                    returnDateField.setText("");
                } else {
                    JOptionPane.showMessageDialog(f, "Failed to return the book. Please check the details.", "Return Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
