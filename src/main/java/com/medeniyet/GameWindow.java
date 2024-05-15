package com.medeniyet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.miginfocom.swing.MigLayout;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class GameWindow extends JFrame implements ActionListener {

    JButton backButton;
    MenuWindow menuWindow;
    Map<String,Object> sudokuData;
    String difficulty;
    int[] sudokuArray = new int[81];

    GameWindow(MenuWindow previousWindow,int level){

        super();
        getLevel(level);
        getSudoku();
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

        int k = 0;

        for (int i = 0;i<9;i++){

            JPanel sector = new JPanel(new MigLayout("wrap,fill,insets 0, gapx 2,gapy 2",
                    "[][][]",
                    "[][][]"));

            sudoku.add(sector,"grow, push, align center");
            sector.setBackground(Color.BLACK);

            for (int j = 0; j<9; j++){

                Cell celda = new Cell();
                celda.setValue(this.sudokuArray[k]);
                k++;
                sector.add(celda,"grow, push");



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

        JLabel mistakeCount = new JLabel("Hatalar 0/3");
        mistakeCount.setFont(new Font("Arial", Font.PLAIN, 22));
        mistakeCount.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timeCount = new JLabel("5:00");
        timeCount.setFont(new Font("Arial", Font.PLAIN, 22));
        timeCount.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        backButton.addActionListener(this);
        this.backButton = backButton;

                //column row width height
        this.add(subPanel,"growx,height 120::,cell 3 0 2 3");
        this.add(sudoku,"width 460px,height 460px,cell 0 1 3 6, align center");
        this.add(keyboard,"width 220!,height 220!,cell 3 3 2 3, align center,gaptop 35px, gapright 35px");
        this.add(backButton,"align right,cell 3 6 2 1,gapright 20px,gapbottom 20px");

    }

    //GET request to Dosuku API

    private void getSudoku() {

        try {
            URI uri = URI.create("https://sudoku-api.vercel.app/api/dosuku");
            URL url = uri.toURL();
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if(responseCode == HttpsURLConnection.HTTP_OK){

                Scanner input = new Scanner(connection.getInputStream());
                StringBuilder builder = new StringBuilder();

                while (input.hasNext()){

                    builder.append(input.nextLine());

                }

                Gson gson = new Gson();

                Map<String,Object> data = gson.fromJson(builder.toString(), Map.class);

                Map<String,Object> data2 = (Map<String, Object>) data.get("newboard");

                ArrayList<Object> array = (ArrayList<Object>) data2.get("grids");

                Map<String,Object> sudokuData = (Map<String, Object>) array.getFirst();

                System.out.println(sudokuData.get("difficulty"));

                if (!sudokuData.get("difficulty").equals(this.difficulty)){

                    getSudoku();

                }else{

                    this.sudokuData = sudokuData;
                    getSudokuArray();

                }

            }else {

                throw new IOException("The request was not successful");

            }

        }catch (IOException e){

            System.out.println("There was an error while requesting a new sudoku from the API");

        }

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

    private void getSudokuArray(){

        //Gson interprets every array inside the json string as ArrayList and every number as Double
        //This code is needed to obtain a normal int[][] matrix to work with

        // Define the type of array you want to deserialize into
        //There is another class called Type in the dependencies so its necessary to specify
        java.lang.reflect.Type arrayType = new TypeToken<int[][]>(){}.getType();

        // Convert ArrayList to JSON string
        Gson gson = new Gson();
        String json = gson.toJson(this.sudokuData.get("value"));

        // Deserialize JSON string into a Java multidimensional array
        int[][] originalArray = gson.fromJson(json, arrayType);

        int [] fullArray = new int[81];
        int k = 0;

        for(int i=0; i<9;i++){

            for (int j=0; j<9; j++){

                fullArray[k] = originalArray[i][j];
                k++;
            }

        }

        this.sudokuArray = fullArray;

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
        this.setPreferredSize(new Dimension(60,60));
        this.setFont(new Font("Arial",Font.BOLD,16));

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
