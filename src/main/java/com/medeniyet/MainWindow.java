package com.medeniyet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener{

    private JButton logButton;
    private JTextField nameBox;

    MainWindow(){

        super();
        this.setTitle("Medeniyet Sudoku");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        //Title of the app
        JLabel title = new JLabel("Sudoku");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Text field for the name of the user
        JTextField nameBox = new JTextField();
        nameBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameBox.setMaximumSize(new Dimension(300,60)); 
        Border padding = new EmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createCompoundBorder(nameBox.getBorder(),padding);
        nameBox.setBorder(border);
        nameBox.setFont(new Font("Arial",Font.PLAIN,17));
        nameBox.setHorizontalAlignment(SwingConstants.CENTER);
        this.nameBox = nameBox;

        //Giriş butonu
        JButton logButton = new JButton("Giriş");
        logButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logButton.setBorder(border);
        logButton.setFont(new Font("Arial",Font.PLAIN,18));
        logButton.addActionListener(this);
        this.logButton = logButton;


        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalStrut(40)); 
        this.add(nameBox);
        this.add(Box.createVerticalStrut(20)); 
        this.add(logButton);
        this.add(Box.createVerticalGlue());






    }
    
    public void display(){

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == this.logButton){

            String name = this.nameBox.getText();

            if(!name.isEmpty()){

                MenuWindow pencere2 = new MenuWindow(name);
                pencere2.display();
                this.dispose();

            }else{

                JOptionPane.showMessageDialog(null,"Geçerli bir isim giriniz","",JOptionPane.WARNING_MESSAGE);
            }

        }


    }

}
