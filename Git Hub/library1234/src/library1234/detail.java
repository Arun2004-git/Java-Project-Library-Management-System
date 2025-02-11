package library1234;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class detail extends JFrame {

    public detail() {
        setTitle("Student and Book Data");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Table for Student Data
        JTable studentTable = new JTable();
        loadStudentData(studentTable);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        tabbedPane.addTab("Student Data", studentScrollPane);

        // Table for Book Data
        JTable bookTable = new JTable();
        loadBookData(bookTable);
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        tabbedPane.addTab("Book Data", bookScrollPane);

        // Table for Currently Issued Books
        JTable issuedBookTable = new JTable();
        loadIssuedBooks(issuedBookTable);
        JScrollPane issuedBookScrollPane = new JScrollPane(issuedBookTable);
        tabbedPane.addTab("Currently Issued Books", issuedBookScrollPane);

        add(tabbedPane);
        setVisible(true);
    }

    // Load data from studentdata table
    private void loadStudentData(JTable table) {
        String[] columnNames = {"Student ID", "Name", "Branch", "Semester", "Roll No", "Division", "Address", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

        String query = "SELECT student_id, name, branch, semester, roll_no, division, address, gender FROM studentdata";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int studentId = rs.getInt("student_id");
                    String name = rs.getString("name");
                    String branch = rs.getString("branch");
                    String semester = rs.getString("semester");
                    int rollNo = rs.getInt("roll_no");
                    String division = rs.getString("division");
                    String address = rs.getString("address");
                    String gender = rs.getString("gender");

                    model.addRow(new Object[]{studentId, name, branch, semester, rollNo, division, address, gender});
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load data from bookdata table, including publication year
    private void loadBookData(JTable table) {
        String[] columnNames = {"Book ID", "Book Name", "Author", "Publisher", "Price", "Publication Year"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

        String query = "SELECT book_id, book_name, author, publisher, price, publication_year FROM bookdata";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String bookId = rs.getString("book_id");  // Book ID treated as String
                    String bookName = rs.getString("book_name");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    double price = rs.getDouble("price");
                    int publicationYear = rs.getInt("publication_year");

                    model.addRow(new Object[]{bookId, bookName, author, publisher, price, publicationYear});
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading book data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load data from currently issued books
    private void loadIssuedBooks(JTable table) {
        String[] columnNames = {"Book ID", "Student ID", "Issue Date", "Due Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);

        String query = "SELECT book_id, student_id, issue_date, due_date FROM issue WHERE return_date IS NULL"; // Only current issues
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String bookId = rs.getString("book_id");  // Book ID treated as String
                    int studentId = rs.getInt("student_id");
                    Date issueDate = rs.getDate("issue_date");
                    Date dueDate = rs.getDate("due_date");

                    model.addRow(new Object[]{bookId, studentId, issueDate, dueDate});
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading issued book data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
