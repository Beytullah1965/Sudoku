package com.medeniyet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

//Swing worker class allows us to execute the API calls in a different worker thread
//This way the Event Dispatcher thread where the GUI is running is not blocked
public class SudokuFetcher extends SwingWorker<Void,Void> {

    String difficulty;
    Map<String,Object> sudokuData;
    int[][] sudoku;
    int[][] solution;
    int[] sudokuArray;
    int[] solutionArray;
    GameWindow window;

    SudokuFetcher(String difficulty, GameWindow window){

        this.difficulty = difficulty;
        this.window = window;

    }

    @Override
    protected Void doInBackground(){

        getSudoku();

        return null;
    }

    @Override
    protected  void done(){

        for (int i = 0; i<81;i++){

            window.cells.get(i).setValue(this.sudokuArray[i]);
            window.cells.get(i).setCorrectValue(this.solutionArray[i]);
            window.cells.get(i).listen();


        }

        window.checkSudoku(this.solutionArray);
        window.solutionArray = this.solutionArray;
        window.startTimer(window.getTimeLimit());

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

                Map<String,Object> newboard = (Map<String, Object>) data.get("newboard");

                ArrayList<Object> array = (ArrayList<Object>) newboard.get("grids");

                Map<String,Object> sudokuData = (Map<String, Object>) array.getFirst();

                System.out.println(sudokuData.get("difficulty"));

                if (!sudokuData.get("difficulty").equals(this.difficulty)){

                    getSudoku();

                }else{

                    this.sudokuData = sudokuData;
                    getSudokuArray("value");
                    getSudokuArray("solution");

                }

            }else {

                throw new IOException("The request was not successful");

            }

        }catch (IOException e){

            System.out.println("There was an error while requesting a new sudoku from the API");

        }

    }

    private void getSudokuArray(String name){

        //Gson interprets every array inside the json string as ArrayList and every number as Double
        //This code is needed to obtain a normal int[][] matrix to work with

        // Define the type of array you want to deserialize into
        //There is another class called Type in the dependencies so its necessary to specify
        //The constructor of TypeToken is protected, so we use a child anonymous class
        java.lang.reflect.Type arrayType = new TypeToken<int[][]>(){}.getType();

        // Convert ArrayList to JSON string
        Gson gson = new Gson();
        String json = gson.toJson(this.sudokuData.get(name));

        // Deserialize JSON string into a Java multidimensional array
        int[][] originalArray = gson.fromJson(json, arrayType);


        int [] simpleArray = new int[81];
        int k = 0;

        for(int i=0; i<9;i++){

            for (int j=0; j<9; j++){

                simpleArray[k] = originalArray[i][j];
                k++;
            }

        }

        if(name.equals("value")){

            this.sudoku = originalArray;
            this.sudokuArray = simpleArray;

        } else if (name.equals("solution")) {

            this.solution = originalArray;
            this.solutionArray = simpleArray;

        }

    }

}
