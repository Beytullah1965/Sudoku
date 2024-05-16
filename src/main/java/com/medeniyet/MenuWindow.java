package com.medeniyet;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame implements ActionListener {

    JButton backButton;
    JButton easyButton;
    JButton mediumButton;
    JButton hardButton;

    MenuWindow(String name){

        super();
        this.setTitle("Medeniyet Sudoku");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        this.setLayout(new MigLayout("wrap,fill, insets 30 50 30 50",
                "[grow]",
                "[][][][][][]"));

        JLabel welcome = new JLabel("Hoş geldin, "+name);
        welcome.setFont(new Font("Arial", Font.PLAIN, 24));
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Menu title
        JLabel title = new JLabel("Zorluk seviyenizi seçiniz");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Kolay button
        JButton easyButton = new JButton("Kolay");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Border padding = new EmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createCompoundBorder(easyButton.getBorder(),padding);
        easyButton.setBorder(border);
        easyButton.setFont(new Font("Arial",Font.PLAIN,18));
        easyButton.setFocusable(false);

        easyButton.addActionListener(this);
        this.easyButton = easyButton;

        //Orta button
        JButton mediumButton = new JButton("Orta");
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.setBorder(border);
        mediumButton.setFont(new Font("Arial",Font.PLAIN,18));
        mediumButton.setFocusable(false);

        mediumButton.addActionListener(this);
        this.mediumButton = mediumButton;

        //Zor button
        JButton hardButton = new JButton("Zor");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.setBorder(border);
        hardButton.setFont(new Font("Arial",Font.PLAIN,18));
        hardButton.setFocusable(false);

        hardButton.addActionListener(this);
        this.hardButton = hardButton;

        //Geri donmek
        JButton backButton = new JButton("Geri dön");
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        backButton.setBorder(border);
        backButton.setFont(new Font("Arial",Font.PLAIN,14));
        backButton.setFocusable(false);

        backButton.addActionListener(this);
        this.backButton = backButton;


        this.add(welcome,"align left");
        this.add(title,"align center");
        this.add(easyButton,"align center, width 20%");
        this.add(mediumButton,"align center,width 20%");
        this.add(hardButton,"align center,width 20%");
        this.add(backButton,"align right");



    }
    
    public void display(){

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == this.backButton){

            MainWindow pencere = new MainWindow();
            pencere.display();
            this.dispose();
            
        } else if (e.getSource() == this.easyButton) {

            GameWindow pencere = new GameWindow(this,1);
            pencere.display();
            this.setVisible(false);

        }else if (e.getSource() == this.mediumButton) {

            GameWindow pencere = new GameWindow(this,2);
            pencere.display();
            this.setVisible(false);

        }else if (e.getSource() == this.hardButton) {

            GameWindow pencere = new GameWindow(this,3);
            pencere.display();
            this.setVisible(false);

        }


    }

}
