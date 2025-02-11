package library1234;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class book extends JFrame {

    private JFrame f;

    public book() {
        f = new JFrame("New Book Entry");
        f.setSize(600, 800); // Adjusted size for additional field
        f.setResizable(false);

        // Book ID (changed to String)
        JLabel idLabel = new JLabel("Book ID:");
        idLabel.setBounds(10, 10, 100, 50);
        JTextField idField = new JTextField();
        idField.setBounds(110, 10, 200, 40);

        // Book Name
        JLabel nameLabel = new JLabel("Book Name:");
        nameLabel.setBounds(10, 80, 100, 50);
        JTextField nameField = new JTextField();
        nameField.setBounds(110, 80, 200, 40);

        // Author Name
        JLabel authorLabel = new JLabel("Author Name:");
        authorLabel.setBounds(10, 150, 100, 50);
        JTextField authorField = new JTextField();
        authorField.setBounds(110, 150, 200, 40);

        // Publisher
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setBounds(10, 220, 100, 50);
        JTextField publisherField = new JTextField();
        publisherField.setBounds(110, 220, 200, 40);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 290, 100, 50);
        JTextField priceField = new JTextField();
        priceField.setBounds(110, 290, 200, 40);

        // Publication Year
        JLabel yearLabel = new JLabel("Publication Year:");
        yearLabel.setBounds(10, 360, 100, 50);
        JTextField yearField = new JTextField();
        yearField.setBounds(110, 360, 200, 40);

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(110, 430, 200, 40);

        // Adding components to the frame
        f.add(idLabel);
        f.add(idField);
        f.add(nameLabel);
        f.add(nameField);
        f.add(authorLabel);
        f.add(authorField);
        f.add(publisherLabel);
        f.add(publisherField);
        f.add(priceLabel);
        f.add(priceField);
        f.add(yearLabel);
        f.add(yearField);
        f.add(saveButton);

        // Save button action listener
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate input
                    if (idField.getText().isEmpty() || nameField.getText().isEmpty() || authorField.getText().isEmpty() ||
                        publisherField.getText().isEmpty() || priceField.getText().isEmpty() || yearField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(f, "All fields must be filled out!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit method if any field is empty
                    }

                    String bookId = idField.getText(); // Book ID treated as a string
                    String bookName = nameField.getText();
                    String authorName = authorField.getText();
                    String publisher = publisherField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int publicationYear = Integer.parseInt(yearField.getText());

                    // Check for valid publication year
                    if (publicationYear < 1000 || publicationYear > 9999) {
                        JOptionPane.showMessageDialog(f, "Please enter a valid publication year (4 digits).", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    saveBookData(bookId, bookName, authorName, publisher, price, publicationYear);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(f, "Please enter valid numeric values for Price and Publication Year.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }

    // Method to save book data
    private void saveBookData(String bookId, String bookName, String authorName, String publisher, double price, int publicationYear) {
        String query = "INSERT INTO bookdata (book_id, book_name, author, publisher, price, publication_year) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setString(1, bookId); // Book ID is a String
                stmt.setString(2, bookName);
                stmt.setString(3, authorName);
                stmt.setString(4, publisher);
                stmt.setDouble(5, price);
                stmt.setInt(6, publicationYear);

                int i = stmt.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(f, "Book data saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(f, "Failed to save book data.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(f, "Database driver not found: " + ex.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(f, "Database error occurred: " + ee.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
