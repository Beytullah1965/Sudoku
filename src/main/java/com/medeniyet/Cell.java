package com.medeniyet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;

public class Cell extends JTextField {

    ArrayList<Cell> column;
    ArrayList<Cell> row;
    ArrayList<Cell> box;
    private int correctValue;

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
                if (string.matches("[1-9]") && (fb.getDocument().getLength() == 0 || offset == 0)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (offset == 0 && text.matches("[1-9]")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });




    }

    public int getValue(){

        try{

            return Integer.parseInt(this.getText());

        }catch (NumberFormatException e){

            return 0;

        }


    }

    public void setValue(int value){

        this.setText(String.valueOf(value));

    }

    public void blockCell(){

        this.setEditable(false);
        this.setBackground(new Color(164,194,212));
        this.setFocusable(false);

    }

    public void setCorrectValue(int value){

        this.correctValue = value;

    }

    public void checkCell(){

        if (this.getValue() == this.correctValue){

            this.blockCell();

        }

    }

    public void listen(){

        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                Cell.this.checkCell();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                Cell.this.checkCell();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //Not necessary in this case
            }
        });

    }

}