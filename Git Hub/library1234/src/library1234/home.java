package library1234;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class home extends JFrame {
    JFrame f;

    public home() {
        f = new JFrame("Home Page");

        
        f.getContentPane().setBackground(new Color(255, 255,255)); 

        
        f.setSize(2000, 900);

        
        JButton s = new JButton("New Student");
        s.setBounds(20, 20, 150, 50);
        s.setBackground(new Color(128, 255, 10)); 
        s.setForeground(Color.WHITE);
        s.setFont(new Font("Arial", Font.BOLD, 14));

        JButton b = new JButton("New Book");
        b.setBounds(200, 20, 150, 50);
        b.setBackground(new Color(20, 240, 255)); 
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 14));

        JButton d = new JButton("Details");
        d.setBounds(380, 20, 150, 50);
        d.setBackground(new Color(255, 0, 50)); 
        d.setForeground(Color.WHITE);
        d.setFont(new Font("Arial", Font.BOLD, 14));

        JButton i = new JButton("Issue Book");
        i.setBounds(560, 20, 150, 50);
        i.setBackground(new Color(135, 0, 235)); 
        i.setForeground(Color.WHITE);
        i.setFont(new Font("Arial", Font.BOLD, 14));

        JButton r = new JButton("Return Book");
        r.setBounds(740, 20, 150, 50);
        r.setBackground(new Color(255, 80, 0)); 
        r.setForeground(Color.WHITE);
        r.setFont(new Font("Arial", Font.BOLD, 14));

        JButton m = new JButton("Modify Data");
        m.setBounds(920, 20, 150, 50);
        m.setBackground(new Color(255, 182, 193)); 
        m.setForeground(Color.WHITE);
        m.setFont(new Font("Arial", Font.BOLD, 14));

        JButton del = new JButton("Delete Data");
        del.setBounds(1100, 20, 150, 50);
        del.setBackground(new Color(139, 65, 9)); 
        del.setForeground(Color.WHITE);
        del.setFont(new Font("Arial", Font.BOLD, 14));

        
        f.add(s);
        f.add(b);
        f.add(d);
        f.add(i);
        f.add(r);
        f.add(m);
        f.add(del);

        
        s.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new student();
            }
        });

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new book();
            }
        });

        d.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new detail();
            }
        });

        i.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new issue();
            }
        });

        r.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new returnbook();
            }
        });

        m.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new modifydata();
            }
        });

        del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new deletedata();
            }
        });

        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }
}
