package library1234;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class l1 {

    public static void main(String[] args) {
        demo d = new demo();
    }

}

class demo extends JFrame {
    JFrame f;

    public demo() {

        f = new JFrame("Library Management");
        f.setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 140, 0), getWidth(), getHeight(), new Color(255, 220, 180));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel l1 = new JLabel("User Name");
        l1.setFont(new Font("Arial", Font.BOLD, 14));
        l1.setForeground(Color.WHITE);  // White color for labels
        l1.setBounds(60, 60, 150, 30);

        JLabel l2 = new JLabel("Password");
        l2.setFont(new Font("Arial", Font.BOLD, 14));
        l2.setForeground(Color.WHITE);
        l2.setBounds(60, 100, 150, 30);

        JTextField t1 = new JTextField();
        t1.setFont(new Font("Arial", Font.PLAIN, 14));
        t1.setBackground(new Color(255, 250, 240)); 
        t1.setBounds(250, 60, 180, 30);

        JPasswordField t2 = new JPasswordField();
        t2.setFont(new Font("Arial", Font.PLAIN, 14));
        t2.setBackground(new Color(255, 250, 240));  
        t2.setBounds(250, 100, 180, 30);

        JButton b1 = new JButton("Submit");
        b1.setFont(new Font("Arial", Font.BOLD, 14));
        b1.setForeground(Color.WHITE);  // White text for button
        b1.setBackground(new Color(255, 140, 0));  // Bright orange background for the button
        b1.setBounds(250, 150, 150, 50);

        panel.add(l1);
        panel.add(l2);
        panel.add(t1);
        panel.add(t2);
        panel.add(b1);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String u = "arun";
                String pa = "1234";
                String n = t1.getText();
                String p = new String(t2.getPassword());  
                if (n.equals(u) && p.equals(pa)) {
                    f.dispose(); 
                    new home();  
                } else {
                    JOptionPane.showMessageDialog(null, "Error! Enter correct username or password");
                }
            }
        });

       
        f.add(panel);
        f.setSize(500, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null); 
        f.setVisible(true);
    }
}
