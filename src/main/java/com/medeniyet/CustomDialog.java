package com.medeniyet;

import javax.security.auth.callback.Callback;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class CustomDialog extends JDialog {

    JButton button;
     MyCallback callbackObj;


    CustomDialog(JFrame parent,String message,String buttonText,MyCallback obj){
        super(parent,"",true);

        this.callbackObj = obj;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(25,55,25,55));

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN,18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton(buttonText);
        Border padding = new EmptyBorder(7, 7, 7, 7);
        Border border = BorderFactory.createCompoundBorder(button.getBorder(),padding);
        button.setBorder(border);
        button.setFont(new Font("Arial",Font.BOLD,14));
        button.setBackground(new Color(17,33,59));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusable(false);

        this.button = button;

        button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){

                if (e.getSource() == CustomDialog.this.button){

                    CustomDialog.this.dispose();
                    CustomDialog.this.callbackObj.run();

                }


            }


        });

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0,15)));
        panel.add(button);
        this.add(panel);

        pack();
        setLocationRelativeTo(parent);


    }

}
interface MyCallback{

    public void run();

}