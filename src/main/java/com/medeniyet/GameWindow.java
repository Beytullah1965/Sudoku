package com.medeniyet;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


public class GameWindow extends JFrame implements ActionListener {

    JButton backButton;
    MenuWindow menuWindow;
    String difficulty;
    int[] sudokuArray = new int[81];
    ArrayList<Cell> cells = new ArrayList<Cell>();
    int mistakes = 0;
    JLabel mistakeLabel;
    JLabel timeLabel;
    Cell focusedCell;
    JButton eraseButton;
    Timer timer;

    GameWindow(MenuWindow previousWindow,int level){

        super();
        getLevel(level);
        this.setTitle("Medeniyet Sudoku");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.menuWindow = previousWindow;


        this.setLayout(new MigLayout("wrap,fill,insets 20 20 20 20",
                "[][][][][]",
                "[][][][][][]push[]"));

        JLabel title = new JLabel(getLevel(level)+" seviye");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Sudoku grid

        JPanel sudoku = new JPanel(new MigLayout("wrap,insets 0, gapx 5, gapy 5",
                "[][][]",
                "[][][]"));
        sudoku.setBackground(Color.BLACK);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
        // Set the black border for the panel
        sudoku.setBorder(blackBorder);

        ArrayList<JPanel> sectors = new ArrayList<JPanel>();

        for (int i = 0;i<9;i++){

            JPanel sector = new JPanel(new MigLayout("wrap,fill,insets 0, gapx 2,gapy 2",
                    "[][][]",
                    "[][][]"));

            sudoku.add(sector,"grow, push, align center");
            sector.setBackground(Color.BLACK);
            sectors.add(sector);

        }

        int counter = 0, xsectorCounter = 0, accumulator = 0, ySectorCounter = 0;

        while(counter<81){

            Cell cellObj = new Cell(this);
            cells.add(cellObj);

            sectors.get(xsectorCounter +(ySectorCounter*3) ).add(cellObj,"grow, push");

            counter++;
            accumulator++;

            if (accumulator>2){

                accumulator = 0;
                xsectorCounter++;

                if (xsectorCounter >2){

                    xsectorCounter -=3;

                }

                if(counter == 27 || counter == 54){

                    ySectorCounter++;

                }

            }

        }


        //Top-right area

        JPanel subPanel = new JPanel(new MigLayout("wrap, insets 0 ,fill ",
                "[][][]",
                "[][][]"));

        ImageIcon eraser = new ImageIcon("images/silgi.png");
        Image scaledImage = eraser.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        eraser = new ImageIcon(scaledImage);
        JButton eraseButton = new JButton(eraser);
        eraseButton.setBackground(new Color(164,194,212));
        eraseButton.setFocusable(false);
        eraseButton.addActionListener(this);
        this.eraseButton = eraseButton;

        JLabel mistakeCount = new JLabel("Hatalar 0/3");
        mistakeCount.setFont(new Font("Arial", Font.PLAIN, 22));
        mistakeCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.mistakeLabel = mistakeCount;

        JLabel timeCount = new JLabel(String.valueOf(this.getTimeLimit())+":00");
        timeCount.setFont(new Font("Arial", Font.PLAIN, 22));
        timeCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.timeLabel = timeCount;

        subPanel.add(title,"span 3 0,gapbottom 20px,gaptop 30px");
        subPanel.add(eraseButton,"span 1 2, width 55!,height 55!,align center");
        subPanel.add(mistakeCount, "span 2 1");
        subPanel.add(timeCount,"span 2 1");

        //Numbers keyboard
        JPanel keyboard = new JPanel(new MigLayout("wrap,insets 0, gapx 7, gapy 7",
                "[][][]",
                "[][][]"));

        //Adding the numbers one by one
        for (int i = 1;i<=9;i++){

            JButton numberButton = new JButton(Integer.toString(i));
            numberButton.setBackground(new Color(34,34,34));
            numberButton.setForeground(Color.WHITE);
            numberButton.setFont(new Font("Arial",Font.BOLD,16));
            numberButton.setFocusable(false);
            numberButton.addActionListener(this);
            keyboard.add(numberButton,"grow, push, align center");

        }



        //Vazgeç butonu
        JButton backButton = new JButton("Vazgeç");
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        Border padding = new EmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createCompoundBorder(backButton.getBorder(),padding);
        backButton.setBorder(border);
        backButton.setFont(new Font("Arial",Font.PLAIN,14));
        backButton.setBackground(new Color(17,33,59));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);

        backButton.addActionListener(this);
        this.backButton = backButton;

                //column row width height
        this.add(subPanel,"growx,height 120::,cell 3 0 2 3");
        this.add(sudoku,"width 460px,height 460px,cell 0 1 3 6, align center");
        this.add(keyboard,"width 220!,height 220!,cell 3 3 2 3, align center,gaptop 35px, gapright 35px");
        this.add(backButton,"align right,cell 3 6 2 1,gapright 20px,gapbottom 20px");


        SudokuFetcher fetcher = new SudokuFetcher(this.difficulty,this);

        fetcher.execute();

    }

    public String getLevel(int level){

        switch (level) {
            case 2:

                this.difficulty = "Medium";
                return "Orta";
            case 3:
                this.difficulty = "Hard";
                return "Zor";

            default:
                this.difficulty = "Easy";
                return "Kolay";

        }


    }

    public void display(){

        this.setVisible(true);

    }

    public void checkSudoku(int[] solution){

        for (int i = 0; i < 81; i++) {

            if(this.cells.get(i).getValue() == solution[i]){

                this.cells.get(i).blockCell();

            }

        }

    }

    public void addMistake(){

        this.mistakes++;
        this.mistakeLabel.setText("Hatalar "+this.mistakes+"/3");

        if (this.mistakes > 3){

            this.gameOver("Hata sınırını aştınız");

        }


    }

    public void gameOver(String reason) {

        CustomDialog dialog = new CustomDialog(this, reason, "Tamam", new MyCallback() {

            @Override
            public void run() {

                menuWindow.display();
                GameWindow.this.timer.stop();
                GameWindow.this.dispose();

            }


        });

        dialog.setVisible(true);

    }

    public int getTimeLimit(){

        switch(this.difficulty){

            case "Easy":

                return 12;

            case "Medium":

                return 1;


            case "Hard":

                return 8;


            default:
                return 10;
        }

    }

    public void startTimer(int minutes){

        Timer timer = new Timer(1000,new ActionListener(){

            int remainingSeconds = minutes*60;

        @Override
            public void actionPerformed(ActionEvent e){

            remainingSeconds--;

            String min = String.valueOf(remainingSeconds/60);
            String sec = String.valueOf(remainingSeconds%60);

            if (sec.length() < 2){

                sec = "0"+sec;

            }

            GameWindow.this.timeLabel.setText(min+":"+sec);

            if (remainingSeconds == 0){

                GameWindow.this.gameOver("Süre doldu");

            }

        }

        });

        timer.start();
        this.timer = timer;

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == this.backButton){

            menuWindow.display();
            this.timer.stop();
            this.dispose();

        }else if (e.getSource() == this.eraseButton){

            this.focusedCell.setText("");


        }else{

            JButton button = (JButton) e.getSource();

            this.focusedCell.setText(button.getText());

        }


    }

}
