package Project;

import java.io.*;
import java.util.*;

// here, I create a class that read a file contains uncompressed image of size 256*256 and generate a file contains the quadtree representation
// QTFile --> this is a recursive method that go over the imageAsArray and write the result to the output file
// getImageArray --> this method use scanner to read the data file and save it as 2D array
// main method --> to read the file and generate the output file

public class ImgQuadTreeFileCreator {

    // a recursive method to go through the elements in the 2D array and write it to the output file
    private static void QTFile(PrintWriter writer, int[][] imageAsArray,  int row, int col, int length) {
        // base case:
        if (length == 0) {
            return;
        }

        // tempIntensity is to track the first intensity of the part of the image (starting from imageArray[row][col]
        int tempIntensity = imageAsArray[row][col];
        // isSameColor will be used to indicate if all the element in within the specified range has the same intensity
        boolean isSameColor = true;

        // for loop to go through the image array (from row --> row + length) & (from col --> col + length)
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (imageAsArray[row + i][col + j] != tempIntensity){
                    isSameColor = false;
                    break;
                }
            }
        }

        // true will result in creating a leaf for the Quad Tree
        if (isSameColor) {
            // read the intensity to the file
            writer.println(tempIntensity);
        }

        // false meaning it is not a leaf
        else {
            writer.println(-1);
            // in the order of (NW, NE, SW, SE)
            QTFile(writer, imageAsArray, row, col, (length / 2));    // NW --> same row and column
            QTFile(writer, imageAsArray, row , col+ (length / 2), (length / 2));  //NE --> shift right (shift row)
            QTFile(writer, imageAsArray, row+ (length / 2), col , (length / 2));  //SW --> shift down (shift column)
            QTFile(writer, imageAsArray, row + (length / 2), col + (length / 2), (length / 2));  // SE --> shift right and down (shift row and column)

        }
    }


    // this method is used once we read the file name
    private static int[][] getImageArray(Scanner scanner) {
        int[][] imageArray = new int[256][256];
        for (int i = 0; i < imageArray.length; i++){
            for (int j = 0; j < imageArray[0].length; j++){
                imageArray[i][j] = scanner.nextInt();
            }
        }
        return imageArray;
    }




    // main method
    public static void main(String[] args) {

        String inputFile;

        // to read file name from user:
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the name of the text file to load:");
        inputFile = input.nextLine();


        // access input file and create output file:
        try(Scanner reader = new Scanner(new File(inputFile));
        PrintWriter writer = new PrintWriter(inputFile.substring(0, inputFile.indexOf(".txt")) + "_QuadTreeFile.txt")){

            int[][] imageAsArray = getImageArray(reader);
            QTFile(writer, imageAsArray, 0, 0, 256);  // to create QuadTree and save the result in the output file

        }
        catch(FileNotFoundException ex){
            System.err.println("Error: Input file not found: " + inputFile);
        }


    }


}
