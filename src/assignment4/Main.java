package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) throws InvalidCritterException {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        System.out.print("critters> ");

        while(kb.hasNextLine()){

            String[] tempArr = kb.nextLine().trim().split(" ");
            String temp = tempArr[0];

            if(temp.equals("quit")){
                return;
            }

            else if(temp.equals("show")){
                Critter.displayWorld();
            }

            else if(temp.equals("step")){
                int limit = 1;

                if(tempArr.length > 1){
                    limit = Integer.parseInt(tempArr[1]);
                }

                for (int i = 0; i < limit; i++) {
                    Critter.worldTimeStep();
                }
            }

            else if(temp.equals("seed")){
                int seed = 0;

                if(tempArr.length > 1){
                    seed = Integer.parseInt(tempArr[1]);
                }
                Critter.setSeed(seed);
            }

            else if(temp.equals("make")){
                int amt = 0;
                String cr;

                if(tempArr.length > 1 ){
                    try{
                        amt = Integer.parseInt(tempArr[2]);
                    } catch (NumberFormatException e){
                        System.out.print("error processing: ");
                        for (int i = 0; i < tempArr.length; i++) {
                            System.out.print(tempArr[i] + " ");
                        }
                        System.out.println();
                    }


                    cr = "assignment4." + tempArr[1];

                    for (int i = 0; i < amt; i++) {
                        try{
                            Critter.makeCritter(cr);
                        } catch(InvalidCritterException e){
                            System.out.println("error processing: " + temp);
                        }
                    }
//                    System.out.println();
                }

            }

            else if(temp.equals("stats")){
                List<Critter> list = new ArrayList<Critter>();
                String cr;

                cr = "assignment4." + tempArr[1];

                try {
                    list = Critter.getInstances(cr);
                    Class c = Class.forName(cr);
                    Method method = c.getMethod("runStats", List.class);
                    method.invoke(c, list);

                }catch (InvalidCritterException | ClassNotFoundException |
                        NoSuchMethodException | IllegalAccessException |
                        InvocationTargetException e){
                    System.out.print("error processing: ");
                    for (int i = 0; i < tempArr.length; i++) {
                        System.out.print(tempArr[i] + " ");
                    }
//                    System.out.println();
                }

            }
            else {
                System.out.print("invalid command: ");
                for (int i = 0; i < tempArr.length; i++) {
                    System.out.print(tempArr[i] + " ");
                }
//                System.out.println();
            }

            System.out.print("\ncritters> ");
        }
        // System.out.println("GLHF");
        
        /* Write your code above */

    }
}
