package library1234;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class deletedata extends JFrame {
    private JTable studentTable;
    private JTable bookTable;
    private DefaultTableModel studentModel;
    private DefaultTableModel bookModel;

    public deletedata() {
        // Set the window title and basic properties
        setTitle("Delete Student and Book Data");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Set the main background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Set font for all components
        Font font = new Font("Arial", Font.BOLD, 14);

        // Set up student table
        studentTable = new JTable();
        studentModel = new DefaultTableModel(new String[]{
                "Student ID", "Name", "Branch", "Semester", "Roll No", "Division", "Address", "Gender"}, 0);
        studentTable.setModel(studentModel);
        studentTable.setFont(font);
        studentTable.setBackground(Color.WHITE);
        studentTable.setSelectionBackground(Color.CYAN);
        studentTable.setSelectionForeground(Color.BLACK);
        loadStudentData();
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        tabbedPane.addTab("Student Data", studentScrollPane);

        // Set up book table
        bookTable = new JTable();
        bookModel = new DefaultTableModel(new String[]{
                "Book ID", "Book Name", "Author", "Publisher", "Price", "Publication Year"}, 0);
        bookTable.setModel(bookModel);
        bookTable.setFont(font);
        bookTable.setBackground(Color.WHITE);
        bookTable.setSelectionBackground(Color.CYAN);
        bookTable.setSelectionForeground(Color.BLACK);
        loadBookData();
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        tabbedPane.addTab("Book Data", bookScrollPane);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setFont(font);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new DeleteSelectedListener());

        buttonPanel.add(deleteButton);

        // Add tabbed pane and button panel to the frame
        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Method to load student data from the database
    private void loadStudentData() {
        String query = "SELECT student_id, name, branch, semester, roll_no, division, address, gender FROM studentdata";
        try {
            // Load JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                // Clear existing data in the table
                studentModel.setRowCount(0);

                // Add data to the table
                while (rs.next()) {
                    int studentId = rs.getInt("student_id");
                    String name = rs.getString("name");
                    String branch = rs.getString("branch");
                    String semester = rs.getString("semester");
                    int rollNo = rs.getInt("roll_no");
                    String division = rs.getString("division");
                    String address = rs.getString("address");
                    String gender = rs.getString("gender");

                    studentModel.addRow(new Object[]{studentId, name, branch, semester, rollNo, division, address, gender});
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load book data from the database
    private void loadBookData() {
        String query = "SELECT book_id, book_name, author, publisher, price, publication_year FROM bookdata";
        try {
            // Load JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                // Clear existing data in the table
                bookModel.setRowCount(0);

                // Add data to the table
                while (rs.next()) {
                    String bookId = rs.getString("book_id");
                    String bookName = rs.getString("book_name");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    double price = rs.getDouble("price");
                    int publicationYear = rs.getInt("publication_year");

                    bookModel.addRow(new Object[]{bookId, bookName, author, publisher, price, publicationYear});
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading book data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inner class for deleting selected data
    private class DeleteSelectedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirmation = JOptionPane.showConfirmDialog(deletedata.this, "Do you want to delete the selected data?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean studentDeleted = deleteStudentData();
                boolean bookDeleted = deleteBookData();
                if (studentDeleted || bookDeleted) {
                    // After deletion, reload the data to refresh the table
                    loadStudentData();
                    loadBookData();
                    JOptionPane.showMessageDialog(deletedata.this, "Data deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(deletedata.this, "No data selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Delete selected student data from database
        private boolean deleteStudentData() {
            boolean deleted = false;
            int[] selectedRows = studentTable.getSelectedRows();
            if (selectedRows.length > 0) {
                try {
                    // Load JDBC driver
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password")) {
                        String query = "DELETE FROM studentdata WHERE student_id=?";
                        try (PreparedStatement pstmt = con.prepareStatement(query)) {
                            // Delete each selected student by student_id
                            for (int i = selectedRows.length - 1; i >= 0; i--) {
                                int studentId = (Integer) studentModel.getValueAt(selectedRows[i], 0);
                                pstmt.setInt(1, studentId);
                                int rowsAffected = pstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    deleted = true;
                                    studentModel.removeRow(selectedRows[i]);
                                }
                            }
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(deletedata.this, "Error deleting student data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return deleted;
        }

        // Delete selected book data from database
        private boolean deleteBookData() {
            boolean deleted = false;
            int[] selectedRows = bookTable.getSelectedRows();
            if (selectedRows.length > 0) {
                try {
                    // Load JDBC driver
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password")) {
                        String query = "DELETE FROM bookdata WHERE book_id=?";
                        try (PreparedStatement pstmt = con.prepareStatement(query)) {
                            // Delete each selected book by book_id
                            for (int i = selectedRows.length - 1; i >= 0; i--) {
                                String bookId = (String) bookModel.getValueAt(selectedRows[i], 0);
                                pstmt.setString(1, bookId);
                                int rowsAffected = pstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    deleted = true;
                                    bookModel.removeRow(selectedRows[i]);
                                }
                            }
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(deletedata.this, "Error deleting book data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return deleted;
        }
    }

}
