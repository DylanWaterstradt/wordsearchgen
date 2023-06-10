//Name: Dylan Waterstradt
//Class: CS 145
//Date: May 25 2023
//Class Information:
//Basically this class manages all word search related
//data.  Depending on the users input, we can either
//generate a new word search puzzle
//print the word search puzzle
//show the solutions to the word search puzzle
//save the puzzle and solutions
//clear the current puzzle
//-----------------------------------
//Please note:
//The current grid is restricted to 25 characters.
//To modify this you can change nCols, nRows, and maxChars
//to whichever size you wish.
//Final note:
//This current version allows numbers in the words it adds.
//This program does not overlap words either.

import java.util.*;
import java.io.*;

public class WordSearchManager {
    
    //number of columns in the word search grid
   private static final int nCols = 25;
    //number of rows in the word search grid
   private static final int nRows = 25;
   
    //maximum number of characters allowed.
   private static final int maxChars = 25;
    
    //The amount of words to find
   private int wordsAmount = 0;
    
    //our grid of characters
   private char[][] grid = new char[nCols][nRows];
   
   //our solutions grid
   private char[][] sGrid = new char[nCols][nRows];
    
    //A list of words
   private List<String> words = new ArrayList<String>();

    /**
      * Method type: char
      * returns: key (the user provides the key)
      * Definition:
      * loads a menu and asks the user to choose an option
      *
      */
   public char chooseSelection() {
      System.out.println("Please select an option:");
      System.out.println("Generate a new word search (g)");
      System.out.println("Print out your word search (p)");
      System.out.println("Show the solution to your word search (s)");
      System.out.println("Save puzzle and solution to file (f)");
      System.out.println("Clear the word search (c)");
      System.out.println("Quit the program (q)");
      System.out.print("Enter a key: ");
        
      Scanner input = new Scanner(System.in);
      String keyEntered = input.next();
      keyEntered = keyEntered.toLowerCase();
        
      char key = keyEntered.charAt(0);
      return key;
   }
    
    /**
      * Method type: void
      * returns: N/A
      * Definition:
      * calls methods depending on the key entered.
      */
   public void handleSelection(char key) {
      switch(key) {
         case 'g':
            generate();
            break;
         case 'p':
            print();
            break;
         case 's':
            solution();
            break;
         case 'c':
            clear();
            break;
         case 'f':
            saveToFile();
            break;
         case 'q':
            break;
         default:
            System.out.println("\nInvalid key enter.");
            System.out.println("Please try again.\n");
            break;
      }    
   }
   
   /*
    * Method Type: void
    * Returns: N/A
    * Definition:
    * Asks the user how many words they would like to add
    * to the search, then proceeds to generate a word
    * search puzzle for them.
    */ 
   public void generate() {
      
      //First let's clear any current generated word searches
      clear();
   
      //Create a object type of Random
      Random random = new Random();
      
      //ask the user how many words they would like to add  
      System.out.print("How many words would you like to add to the search?: ");
      
      //create a new scanner object for input
      Scanner input = new Scanner(System.in);
      
      //create a temp string variable that we will convert to integer
      String temp = "";
      
      //create a while loop that prompts the user to enter a valid number,
      //until they do.
      while(!input.hasNextInt()) {
         System.out.println("Please enter a number.");
         System.out.print("How many words would you like to add to the search?: ");
         input.next();
      }
      
      //create a new variable that stores the amount of words,
      //they would like to add.  
      int wordsAmount = input.nextInt();
      
      //Notify the user of word restrictions
      System.out.print("Please note that only a maximum of " + maxChars + " characters are ");
      System.out.println("allowed.  Any words longer will be ignored in generating.");
      
      //run a for loop for each word storing it in an
      //ArrayList object.  For loop keeps going until
      //we hit the amount the specified.  
      for(int i = 0; i < wordsAmount; i++) {
         System.out.print("Enter word " + (i+1) + ": ");
         words.add(input.next().toLowerCase());
      }
      
      //run a for loop to check words that have more
      //than 25 characters.  If so, remove them from
      //the list.
      for(int i = 0; i < words.size(); i++) {
          if(words.get(i).length() > maxChars) {
             words.remove(i);
          }
      }
      
      //declare the default number of attempts which is 0.  
      int numAttempts = 0;
      
      //set our wordCount to the amount of words in the ArrayList.
      int wordsCount = words.size();
      
      //Set currentWord to 0 and we will use this as our starting index.
      int currentWord = 0;
      
      //run a while loop until we've added all the words, or
      //have tried 100 times.  
      while(numAttempts < 100 && currentWord < wordsCount) {
         
         //set i as the index of our arrayList.
         int i = currentWord;
         
         //produce a random X coordinate   
         int factorX = random.nextInt((nCols - 1));
         
         //produce a random Y coordinate
         int factorY = random.nextInt((nRows - 1));
            
          //Generates a random number from 0 to 7  
         int randDir = random.nextInt(7);
          
          //stores the size of the word  
         int size = words.get(i).length();
          
          
          //The following section handles the placement
          //of words in the puzzle. This part is a bit
          //hard to explain.
          
          //0 = left to right        
         if(randDir == 0 && (factorX+size) < nCols) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX+j][factorY] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX+j][factorY] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
         
          //1 = right to left
         if(randDir == 1 && (factorX-size) > 0) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX-j][factorY] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX-j][factorY] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
         
          //2 = up to down              
         if(randDir == 2 && (factorY+size) < nRows) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX][factorY+j] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX][factorY+j] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
          
          //3 = down to up  
         if(randDir == 3 && (factorY-size) > 0) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX][factorY-j] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX][factorY-j] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
         
          //4 = upper left to lower right
         if(randDir == 4 && (factorX+size) < nCols && (factorY+size) < nRows) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX+j][factorY+j] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX+j][factorY+j] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
          
          //5 = lower right to upper left
         if(randDir == 5 && (factorX-size) > 0 && (factorY-size) > 0) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX-j][factorY-j] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX-j][factorY-j] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
         
          //6 = upper right to lower left
         if(randDir == 6 && (factorX-size) > 0 && (factorY+size) < nRows) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX-j][factorY+j] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX-j][factorY+j] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
         
         //7 = lower left to upper right   
         if(randDir == 7 && (factorX+size) < nCols && (factorY-size) > 0) {
            boolean hasWord = false;
            for(int j = 0; j < size; j++) {
               if(grid[factorX+j][factorY-j] != '\u0000') {
                  hasWord = true;
               }
            }
            if(hasWord == false) {
               for(int j = 0; j < size; j++) {
                  grid[factorX+j][factorY-j] = words.get(i).charAt(j);
               }
               currentWord++;
            }
         }
         numAttempts+=1;
      }
      
      //we fill all empty char arrays with a dash
      for(int i = 0; i < nCols; i++) {
         for(int j = 0; j < nRows; j++) {
            if(grid[i][j] == '\u0000') {
               grid[i][j] = '-';
            }
         }
      }
      
      //we set our solutions grid to the current grid
      for(int i = 0; i < nCols; i++) {
         for(int j = 0; j < nRows; j++) {
            sGrid[i][j] = grid[i][j];
         }
      }
      
      //we replace - with random chars in our grid
      for(int i = 0; i < nCols; i++) {
         for(int j = 0; j < nRows; j++) {
            if(grid[i][j] == ('-')) {
               grid[i][j] = randomChar();
            }
         }
      }
   }
    
    /*
     * Method type: void
     * Returns: N/A
     * Definition:
     * Prints the word search puzzle
     * to command prompt.
     */
   public void print() {     
      for(int i = 0; i < nCols; i++) {
         for(int j = 0; j < nRows; j++) {
            System.out.print(grid[i][j] + "   ");
         }
         System.out.println();
      }
        
        //here we add the list of words for the user to find
      System.out.println("\n\nWords to find:");
        
      for(int i = 0; i < words.size(); i++) {
         System.out.print(words.get(i) + "   ");
      }
        
      System.out.println("\n");
   }
   
   /*
    * Method type: void
    * returns: N/A
    * Definition:
    * Prints the solution to our word search.
    * -----------------------------------
    * By default our word search grid is
    * set to the solution. When we print the
    * the grid, we modify '-' values to random chars.
    */ 
   public void solution() {
      //we create a nested for loop
      //to go through each row and column
      //and print the grid as a solution.
      for(int i = 0; i < nCols; i++) {
         for(int j = 0; j < nRows; j++) {
            System.out.print(sGrid[i][j] + "   ");
         }
         System.out.println();
      }
   }
   
    /*
     * Method type: void
     * returns: N/A
     * Definition:
     * Prompts the user for a filename to save the puzzle
     * and solutions to.  Lets the user know if it successfully
     * creates the file or if it already exists, or if an error
     * has occurred.
     */
   public void saveToFile() {
   
      //prompt user for file name
      System.out.print("Enter a filename for the word search puzzle: ");
      
      //create scanner object for input
      Scanner input = new Scanner(System.in);
      
      //set puzzlename to input
      String puzzleName = input.next();
      
       //add .txt to end of file name
      puzzleName += ".txt";
      
       //prompt user for second file name
      System.out.print("Enter a filename for the solutions: ");
      
       //set solutionsName to input
      String solutionsName = input.next();
      
       //add .txt to end of file name
      solutionsName += ".txt";
      
      try {
          //create new file object based on puzzleName
         File puzzle = new File(puzzleName);
         
          //if we successfully created the file
         if(puzzle.createNewFile()) {
         
             //create new print stream
            PrintStream file = new PrintStream(puzzle);
            
             //add the puzzle to the file
            for(int i = 0; i < nCols; i++) {
               for(int j = 0; j < nRows; j++) {
                  file.print(grid[i][j] + "   ");
               }
               file.println();
            }
            
             //let user know file has been created
            System.out.println(puzzleName + " has been created.");
         } else {
            System.out.println(puzzleName + "File already exists.");
         }
      } catch(Exception e) {
         System.out.println("an error has occured");
      }
      
      try {
          //create new file object based on solutionsNamme
         File solutions = new File(solutionsName);
         
          //if file has been successfully created
         if(solutions.createNewFile()) {
             //create new printstream
            PrintStream file = new PrintStream(solutions);
            
             //add solutions grid to file
            for(int i = 0; i < nCols; i++) {
               for(int j = 0; j < nRows; j++) {
                  file.print(sGrid[i][j] + "   ");
               }
               file.println();
            }
            
             //let user know we created the file
            System.out.println(solutionsName + " has been created.");
         } else {
            System.out.println(solutionsName + " already exists.");
         }
      } catch(Exception e) {
         System.out.println("an error has occured");
      }
      System.out.println();
   }
   
   /*
    * Method type: void
    * returns: N/A
    * Definition:
    * Simply clears the grid of characters, so
    * the user can generate a new word search.
    */
   public void clear() {
      for(int i = 0; i < nCols; i++) {
         for(int j = 0; j < nRows; j++) {
            grid[i][j] = '\u0000';
            sGrid[i][j] = '\u0000';
         }
      }
   }  
   
   /*
    * Method Type: Char
    * Returns: c (random character)
    * Definition:
    * Generates a random character between a and z.
    */
   public char randomChar() {
      //97 is the integer equivelent of a, so we start
      //our min value at that.
      int min = 97;
      
      //our max value is essentialy the min value plus 25,
      //since we already have a we just add 25 to get z
      //as our maximum number equivelent.
      int max = min + 25;
      
      //we create a random object type
      Random random = new Random();
      
      //we set our random value as a random value between
      //min and max and assign it to rand.  
      int rand = random.nextInt((max - min) +1) + min;
      
      //we cast rand as a char so we get the character
      //equivelent of the random number.  
      char c = (char) rand;
      
      //we simply return the c value.
      return c;
   }
        
}