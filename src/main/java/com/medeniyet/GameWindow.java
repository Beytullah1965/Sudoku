package com.medeniyet;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameWindow extends JFrame implements ActionListener {

    JButton backButton;
    MenuWindow menuWindow;

    GameWindow(MenuWindow previousWindow,int level){

        super();
        this.setTitle("Medeniyet Sudoku");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.menuWindow = previousWindow;


        this.setLayout(new MigLayout("wrap,fill,insets 20 20 20 20","[][][][][]","[][][][][][][]"));

        JLabel title = new JLabel(getLevel(level)+" seviye");
        title.setFont(new Font("Arial", Font.PLAIN, 27));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel sudoku = new JPanel(new MigLayout("wrap,insets 0, gapx 5, gapy 5",
                "[][][]",
                "[][][]"));
        sudoku.setBackground(Color.BLACK);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 5);

        // Set the black border for the panel
        sudoku.setBorder(blackBorder);

        for (int i = 0;i<9;i++){

            JPanel sector = new JPanel(new MigLayout("wrap,fill,insets 0, gapx 2,gapy 2",
                    "[][][]",
                    "[][][]"));

            sudoku.add(sector,"grow, push, align center");
            sector.setBackground(Color.BLACK);

            for (int j = 0; j<9; j++){

                Cell celda = new Cell();

                sector.add(celda,"grow, push");

            }

        }


        //Top-left area

        JPanel subPanel = new JPanel(new MigLayout("wrap, insets 30 10 30 10 ,fill",
                "[][][]",
                "[][]"));

        ImageIcon eraser = new ImageIcon("images/silgi.png");
        Image scaledImage = eraser.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        eraser = new ImageIcon(scaledImage);
        JButton eraseButton = new JButton(eraser);

        JLabel mistakeCount = new JLabel("Hatalar 0/3");
        mistakeCount.setFont(new Font("Arial", Font.PLAIN, 21));
        mistakeCount.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timeCount = new JLabel("5:00");
        timeCount.setFont(new Font("Arial", Font.PLAIN, 21));
        timeCount.setAlignmentX(Component.LEFT_ALIGNMENT);

        subPanel.add(eraseButton,"span 1 2, width 55!,height 55!,align center");
        subPanel.add(mistakeCount, "span 2 1");
        subPanel.add(timeCount,"span 2 1");

        //Numbers keyboard
        JPanel keyboard = new JPanel(new MigLayout("wrap,insets 0, gapx 10, gapy 10",
                "[][][]",
                "[][][]"));

        //Adding the numbers one by one
        for (int i = 1;i<=9;i++){

            JButton numberButton = new JButton(Integer.toString(i));
            numberButton.setBackground(new Color(34,34,34));
            numberButton.setForeground(Color.WHITE);
            numberButton.setFont(new Font("Arial",Font.BOLD,16));
            keyboard.add(numberButton,"grow, push, align center");

        }



        //Vazgeç butonu
        JButton backButton = new JButton("Vazgeç");
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        Border padding = new EmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createCompoundBorder(backButton.getBorder(),padding);
        backButton.setBorder(border);
        backButton.setFont(new Font("Arial",Font.PLAIN,14));
        backButton.addActionListener(this);
        this.backButton = backButton;

                //column row width height
        this.add(title,"cell 0 0 3 1,gapleft 30px");
        this.add(subPanel,"growx,height 120::,cell 3 0 2 2");
        this.add(sudoku,"width 460px,height 460px,cell 0 1 3 6, align center");
        this.add(keyboard,"width 250!,height 250!,cell 3 2 2 4, align center");
        this.add(backButton,"align right,cell 3 6 2 1,gapright 20px");



    }

    public String getLevel(int level){

        return switch (level) {
            case 2 -> "Orta";
            case 3 -> "Zor";
            default -> "Kolay";
        };


    }

    public void display(){

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == this.backButton){

            menuWindow.display();
            this.dispose();

        }



    }

}

class Cell extends JTextField{

    ArrayList<Cell> column;
    ArrayList<Cell> row;
    ArrayList<Cell> box;
    int value = 0;

    Cell(){

        super();
        this.setInputValidation();
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setAlignmentY(JTextField.CENTER);
        this.setBorder(null);

    }

    private void setInputValidation(){

        AbstractDocument document = (AbstractDocument) this.getDocument();

        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                // If the string is a single digit from 1 to 9 and the document is empty or the replacement would occur at the current offset
                if (string.matches("[1-9]") && (fb.getDocument().getLength() == 0 || offset == 0)) {
                    super.insertString(fb, offset, string, attr); // Insert the character
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // If the replacement would occur at the current offset
                if (offset == 0 && text.matches("[1-9]")) {
                    super.replace(fb, offset, length, text, attrs); // Replace the character
                }
            }
        });




    }

    public int getValue(){

        return Integer.parseInt(this.getText());

    }

    public void setValue(int value){

        this.value = value;
        this.setText(String.valueOf(value));

    }

}
