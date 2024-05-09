package com.medeniyet;

import javax.swing.*;

public class App{
    public static void main( String[] args ){

        //GUI ile ilgili islemler Event Dispatch Thread uzerinde calistir
        //Receives an instance of a class implementing runnable interface (Anonymous here)
        SwingUtilities.invokeLater(new Runnable() {
           
            @Override
            public void run(){

                MainWindow pencere = new MainWindow();
                pencere.display();

            }

        });

    }
}
