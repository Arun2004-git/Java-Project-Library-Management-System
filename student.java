package library1234;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class student extends JFrame 
{
    JFrame f;

    public student() 
    {
        f = new JFrame("New Library Member");
        f.setSize(600, 850);
        f.setResizable(false);
        
        JLabel id = new JLabel("Student ID:");
        id.setBounds(10, 10, 100, 50);
        JTextField idf = new JTextField();
        idf.setBounds(110, 10, 200, 40);
        
        JLabel n = new JLabel("Student Name:");
        n.setBounds(10, 80, 100, 50);
        JTextField nf = new JTextField();
        nf.setBounds(110, 80, 200, 40);
        
        JLabel b = new JLabel("Branch:");
        b.setBounds(10, 150, 100, 50);
        String[] branch = {"Bcs[Ecs]", "BA", "B.com", "BE", "B.Tech"};
        JComboBox<String> bc = new JComboBox<>(branch);
        bc.setBounds(110, 150, 200, 40);
        
        JLabel s = new JLabel("Semester:");
        s.setBounds(10, 220, 100, 50);
        String[] semester = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        JComboBox<String> sc = new JComboBox<>(semester);
        sc.setBounds(110, 220, 200, 40);
        
        JLabel r = new JLabel("Roll No:");
        r.setBounds(10, 290, 100, 40);
        JTextField rf = new JTextField();
        rf.setBounds(110, 290, 200, 40);
        
        JLabel d = new JLabel("Division:");
        d.setBounds(10, 360, 100, 50);
        String[] div = {"A", "B", "C", "D", "F"};
        JComboBox<String> dc = new JComboBox<>(div);
        dc.setBounds(110, 360, 200, 40);
        
        JLabel a = new JLabel("Address:");
        a.setBounds(10, 430, 100, 50);
        JTextField af = new JTextField();
        af.setBounds(110, 430, 200, 40);
        
        JLabel g = new JLabel("Gender:");
        g.setBounds(10, 500, 100, 50);
        String[] gen = {"Male", "Female", "Other"};
        JComboBox<String> gc = new JComboBox<>(gen);
        gc.setBounds(110, 500, 200, 40);
        
        JButton sa = new JButton("Save");
        sa.setBounds(110, 570, 200, 40);
        
        f.add(id);
        f.add(idf);
        f.add(n);
        f.add(nf);
        f.add(b);
        f.add(bc);
        f.add(s);
        f.add(sc);
        f.add(r);
        f.add(rf);
        f.add(d);
        f.add(dc);
        f.add(a);
        f.add(af);
        f.add(g);
        f.add(gc);
        f.add(sa);
        
        sa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.parseInt(idf.getText());
                    String name = nf.getText();
                    String branch = (String) bc.getSelectedItem();
                    String semester = (String) sc.getSelectedItem();
                    int rollNo = Integer.parseInt(rf.getText());
                    String division = (String) dc.getSelectedItem();
                    String address = af.getText();
                    String gender = (String) gc.getSelectedItem();
                    
                    insertStudentRecord(studentId, name, branch, semester, rollNo, division, address, gender);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(f, "Please enter valid numeric values for ID and Roll No.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }

    private void insertStudentRecord(int studentId, String name, String branch, String semester, int rollNo, String division, String address, String gender) 
    {
        String query = "INSERT INTO studentdata (student_id, name, branch, semester, roll_no, division, address, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
                 PreparedStatement stmt = con.prepareStatement(query))
            {
                stmt.setInt(1, studentId);
                stmt.setString(2, name);
                stmt.setString(3, branch);
                stmt.setString(4, semester);
                stmt.setInt(5, rollNo);
                stmt.setString(6, division);
                stmt.setString(7, address);
                stmt.setString(8, gender);
                
                int i = stmt.executeUpdate();
                if (i > 0)
                {
                    JOptionPane.showMessageDialog(f, "Student data saved successfully!");
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(f, "Database driver not found: " + ex.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException ee)
        {
            JOptionPane.showMessageDialog(f, "Database error occurred: " + ee.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
