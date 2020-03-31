import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;

public class N50Calculator {

    public static int sum;
    public static ArrayList<Integer> contigLengths;

    public static void main (String[] args) {
        File fasta = getFastaFileByArgs(args);

        setContigLengths(fasta);

        printN50();
    }

    public static File getFastaFileByArgs(String[] args) {

        File fasta = null;
        if (args.length == 2 && (args[0].equals("-f") || args[0].equals("--filepath"))) {
            fasta = new File(args[1]);

            if (!fasta.exists()) {
                System.out.println("\n********** File not found! *************\n");
                exitAndPrintHelp();
            }
        } else {
            exitAndPrintHelp();
        }

        return fasta;
    }

    public static void exitAndPrintHelp() {
        System.out.println("Usage: java N50Calculator [-h] -f FILEPATH\n");
        System.out.println("Required arguments:");
        System.out.println("-f, --filepath : path to the Fasta File");
        System.out.println("Optional arguments:");
        System.out.println("-h, --help : show this help message and exit");

        System.exit(0);
    }

    // read the Fasta file provided, line by line
    // generate an arraylist with contig lengths and the sum length of all the contigs
    public static void setContigLengths(File fasta) {
        contigLengths = new ArrayList<Integer>();
        sum = 0; // sum of all the contigs

        try {
            Scanner fastaScanner = new Scanner(fasta);
            String line = null;
            
            while (fastaScanner.hasNextLine()) {
                line = fastaScanner.nextLine(); // read a line

                // any line should not be empty
                if (!isEmptyLine(line)) {
                    // ID line
                    if (isValidIDLine(line)) {
                        contigLengths.add(0); // add a new element at the end of the arraylist to indicate a new sequence
                        line = fastaScanner.nextLine(); // skip id line and read first line of a new sequence
                    }

                    // at this point this line must be a contig line

                    if (!isEmptyLine(line) && isValidContigLine(line)) {
                        // add the current line length to the last element of arraylist
                        int lastLengthIndex = contigLengths.size()-1; 
                        int newLength = contigLengths.get(lastLengthIndex) + line.length();
                        sum += line.length();

                        contigLengths.set(lastLengthIndex, newLength);
                    } else {
                        System.out.println("Invalid Contig Line Found");
                        System.exit(0);
                    }
                    
                } else {
                    System.out.println("Invalid Line Found");
                    System.exit(0);
                }
            }
            fastaScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }

        Collections.sort(contigLengths, Collections.reverseOrder()); //sort the list in descending Order
    }

    private static boolean isEmptyLine(String line) {
        return line == null || line.length() == 0;
    }

    private static boolean isValidIDLine(String line) {
        return line.charAt(0) == '>';
    }

    private static boolean isValidContigLine(String line) {
        // regex only ACGT show in the line
        return line.matches("^[ACGT]+$");
    }

    public static void printN50() {
        int halfSum = sum / 2;

        if (contigLengths.size() == 0) {
            System.out.println("No contig found!");
        } else if (contigLengths.size() == 1) {
            System.out.println("N50 Statistic: ");
            System.out.println(contigLengths.get(0));
        } else {
            // at least 2 contigs
            int currentIndex = 0;
            int currentSum = contigLengths.get(currentIndex);

            // loop until find running sum greater or equal to half sum
            while (currentSum < halfSum) {
                currentIndex++;
                currentSum += contigLengths.get(currentIndex);
            }

            System.out.println(contigLengths.get(currentIndex));
        }
    }
}