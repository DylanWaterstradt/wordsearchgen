//Name: Dylan Waterstradt
//Class: CS &145
//Date: May 25, 2023
//Class Information:
//This is our main class.  We simply provide
//an introduction and establish a loop
//that repeats atleast one time handling any input
//the user enters.  The input is handled through
//our WordSearchManager class.

public class DWWordSearchTest {

    /*
     * Method type: void
     * Returns: N/A
     * Definition:
     * Runs the main method. First it prints the welcome message.
     * Then we create a new WordSearchManager object and we want
     * it to run atleast one time so, do atleast once, set char c
     * to value of the return call of chooseSelection(), then 
     * we handle the selection.  Repeat until user press q for
     * quit.
     */
    public static void main(String[] args) {
        //A char to hold the value of what the user press
        char c;
        
        //Print the intro.
        printIntro();
        
        //create a new WordSearchManager object
        WordSearchManager wsm = new WordSearchManager();
        
        do {
            c = wsm.chooseSelection(); //return whatever the user entered.
            wsm.handleSelection(c); //handle the input.
        } while(c != 'q'); //repeat until user press q
    }
    
    
    /*
     * Method Type: Void
     * Returns: N/A
     * Definition:
     * Simply prints a welcome message.
     */
    public static void printIntro() {
        System.out.println("Welcome to word search generator!");
        System.out.print("This program will allow you to ");
        System.out.println("generate your own word search puzzle.\n");
    }
}