package library1234;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class issue extends JFrame {

    private JFrame f;

    public issue() {
        f = new JFrame("Issue Book");
        f.setSize(600, 500);
        f.setResizable(false);
        
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(10, 10, 100, 50);
        JTextField bookIdField = new JTextField();
        bookIdField.setBounds(110, 10, 200, 40);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(10, 80, 100, 50);
        JTextField studentIdField = new JTextField();
        studentIdField.setBounds(110, 80, 200, 40);

        JLabel issueDateLabel = new JLabel("Issue Date (yyyy-mm-dd):");
        issueDateLabel.setBounds(10, 150, 150, 50);
        JTextField issueDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())); 
        issueDateField.setBounds(160, 150, 150, 40);

        JLabel dueDateLabel = new JLabel("Due Date (yyyy-mm-dd):");
        dueDateLabel.setBounds(10, 220, 150, 50);
        JTextField dueDateField = new JTextField();
        dueDateField.setBounds(160, 220, 150, 40);

        JButton issueButton = new JButton("Issue Book");
        issueButton.setBounds(110, 300, 200, 40);

        f.add(bookIdLabel);
        f.add(bookIdField);
        f.add(studentIdLabel);
        f.add(studentIdField);
        f.add(issueDateLabel);
        f.add(issueDateField);
        f.add(dueDateLabel);
        f.add(dueDateField);
        f.add(issueButton);

        issueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String bookId = bookIdField.getText().trim(); // Make book_id a String
                    int studentId = Integer.parseInt(studentIdField.getText());
                    String issueDate = issueDateField.getText();
                    String dueDate = dueDateField.getText();

                    if (bookId.isEmpty() || dueDate.isEmpty()) {
                        JOptionPane.showMessageDialog(f, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        issueBook(bookId, studentId, issueDate, dueDate);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(f, "Please enter valid numeric values for Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }

    private void issueBook(String bookId, int studentId, String issueDate, String dueDate) {
        String query = "INSERT INTO issue (book_id, student_id, issue_date, due_date) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'))";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setString(1, bookId); // Set bookId as a String
                stmt.setInt(2, studentId); 
                stmt.setString(3, issueDate); 
                stmt.setString(4, dueDate);

                int i = stmt.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(f, "Book issued successfully!");
                }
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(f, "Database driver not found: " + ex.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(f, "Database error occurred: " + ee.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
