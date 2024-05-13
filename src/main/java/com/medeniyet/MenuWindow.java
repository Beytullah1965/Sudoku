package com.medeniyet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuWindow extends JFrame{

    MenuWindow(String name){

        super();
        this.setTitle("Medeniyet Sudoku");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        //Menu başlığı
        JLabel title = new JLabel("Zorluk seviyenizi seçiniz");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Kolay butonu
        JButton easyButton = new JButton("Kolay");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Border padding = new EmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createCompoundBorder(easyButton.getBorder(),padding);
        easyButton.setBorder(border);
        easyButton.setFont(new Font("Arial",Font.PLAIN,18));

        //Orta butonu
        JButton mediumButton = new JButton("Orta");
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.setBorder(border);
        mediumButton.setFont(new Font("Arial",Font.PLAIN,18));

        //Zor butonu
        JButton hardButton = new JButton("Zor");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.setBorder(border);
        hardButton.setFont(new Font("Arial",Font.PLAIN,18));



        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalStrut(20)); 
        this.add(easyButton);
        this.add(Box.createVerticalStrut(20)); 
        this.add(mediumButton);
        this.add(Box.createVerticalStrut(20)); 
        this.add(hardButton);
        this.add(Box.createVerticalGlue());






    }
    
    public void display(){

        this.setVisible(true);

    }

}
