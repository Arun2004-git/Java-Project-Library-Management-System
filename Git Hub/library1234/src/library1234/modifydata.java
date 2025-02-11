package library1234;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class modifydata extends JFrame {
    private JTable studentTable;
    private JTable bookTable;
    private DefaultTableModel studentModel;
    private DefaultTableModel bookModel;

    public modifydata() {
        setTitle("Modify Student and Book Data");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Table for Student Data
        studentTable = new JTable();
        studentModel = new DefaultTableModel(new String[]{
                "Student ID", "Name", "Branch", "Semester", "Roll No", "Division", "Address", "Gender"}, 0);
        studentTable.setModel(studentModel);
        loadStudentData();
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        tabbedPane.addTab("Student Data", studentScrollPane);

        // Table for Book Data
        bookTable = new JTable();
        bookModel = new DefaultTableModel(new String[]{
                "Book ID", "Book Name", "Author", "Publisher", "Price", "Publication Year"}, 0);
        bookTable.setModel(bookModel);
        loadBookData();
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        tabbedPane.addTab("Book Data", bookScrollPane);

        // Button to Save Changes
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new SaveChangesListener());

        add(tabbedPane, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Load data from studentdata table
    private void loadStudentData() {
        String query = "SELECT student_id, name, branch, semester, roll_no, division, address, gender FROM studentdata";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getString("semester"),
                        rs.getInt("roll_no"),
                        rs.getString("division"),
                        rs.getString("address"),
                        rs.getString("gender")
                    };
                    studentModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + e.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load data from bookdata table
    private void loadBookData() {
        String query = "SELECT book_id, book_name, author, publisher, price, publication_year FROM bookdata";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Object[] row = {
                        rs.getString("book_id"),
                        rs.getString("book_name"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getDouble("price"),
                        rs.getInt("publication_year")
                    };
                    bookModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading book data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + e.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class SaveChangesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirmation = JOptionPane.showConfirmDialog(modifydata.this, "Do you want to save changes?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean studentUpdated = updateStudentData();
                boolean bookUpdated = updateBookData();
                if (studentUpdated || bookUpdated) {
                    JOptionPane.showMessageDialog(modifydata.this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(modifydata.this, "No changes made.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private boolean updateStudentData() {
            boolean updated = false;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password")) {

                    String query = "UPDATE studentdata SET name=?, branch=?, semester=?, roll_no=?, division=?, address=?, gender=? WHERE student_id=?";
                    try (PreparedStatement pstmt = con.prepareStatement(query)) {

                        for (int row = 0; row < studentModel.getRowCount(); row++) {
                            int studentId = Integer.parseInt(studentModel.getValueAt(row, 0).toString());
                            String name = (String) studentModel.getValueAt(row, 1);
                            String branch = (String) studentModel.getValueAt(row, 2);
                            String semester = (String) studentModel.getValueAt(row, 3);
                            int rollNo = Integer.parseInt(studentModel.getValueAt(row, 4).toString());
                            String division = (String) studentModel.getValueAt(row, 5);
                            String address = (String) studentModel.getValueAt(row, 6);
                            String gender = (String) studentModel.getValueAt(row, 7);

                            pstmt.setString(1, name);
                            pstmt.setString(2, branch);
                            pstmt.setString(3, semester);
                            pstmt.setInt(4, rollNo);
                            pstmt.setString(5, division);
                            pstmt.setString(6, address);
                            pstmt.setString(7, gender);
                            pstmt.setInt(8, studentId);

                            int rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                updated = true;
                            }
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(modifydata.this, "Error updating student data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            return updated;
        }

        private boolean updateBookData() {
            boolean updated = false;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                     PreparedStatement pstmt = con.prepareStatement(
                        "UPDATE bookdata SET book_name=?, author=?, publisher=?, price=?, publication_year=? WHERE book_id=?")) {

                    for (int row = 0; row < bookModel.getRowCount(); row++) {
                        String bookId = (String) bookModel.getValueAt(row, 0);
                        String bookName = (String) bookModel.getValueAt(row, 1);
                        String author = (String) bookModel.getValueAt(row, 2);
                        String publisher = (String) bookModel.getValueAt(row, 3);
                        double price = Double.parseDouble(bookModel.getValueAt(row, 4).toString());
                        int publicationYear = Integer.parseInt(bookModel.getValueAt(row, 5).toString());

                        pstmt.setString(1, bookName);
                        pstmt.setString(2, author);
                        pstmt.setString(3, publisher);
                        pstmt.setDouble(4, price);
                        pstmt.setInt(5, publicationYear);
                        pstmt.setString(6, bookId);

                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            updated = true;
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(modifydata.this, "Error updating book data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            return updated;
        }
    }
}
