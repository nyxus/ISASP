/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isasp;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Gerco
 */
public class Marian {
    private ArrayList<Chromosome> population;
    private Block floor = new Block(0, 1, 1, 0, 0);
    private Integer[][] dependencyMatrix;
    
    private Block[][] fysicalMatrix;
    
    public Marian(String file, int problemSize){
        floor = new Block(0, 0, problemSize, 0, 0);
        
        this.fysicalMatrix = new Block[problemSize][problemSize];
        
        // set floor into fyclicalMatrx at index
        /*
        for (int i = 0; i < problemSize; i++) {
            fysicalMatrix[0][i] = floor;
        }
        */
        ReadProblem(file);
        System.out.println(ToStringFysicalMatrix());
        
    }
    
    
    public void ReadProblem(String file){ 
        try {
            //Open bestand op de opgegeven locatie
            FileInputStream fstream = new FileInputStream(file);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Block tempBlock;
            //Lees bestand per regel
            while ((strLine = br.readLine()) != null) {
                tempBlock = StringtoBlock(strLine);
                BlockIntoFysicalMatrix(tempBlock);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }   
    }
    
    public Block StringtoBlock(String inputBlock){
        String[] tempStringBlock;
        Integer[] tempIntBlock;
        
        tempStringBlock = inputBlock.split(" ");
        tempIntBlock = StringToIntArray(tempStringBlock);
        
        //Block returnBlock = new Block(ID, MinX, MaxX, MinY, MaxY)
        Block returnBlock = new Block(tempIntBlock[0], tempIntBlock[1], tempIntBlock[2], tempIntBlock[3], tempIntBlock[4]);
        
        return returnBlock;  
    } 
    
    static Integer[] StringToIntArray(String[] stringArray){
        Integer[] intArray = new Integer[stringArray.length];;
        
        for (int i = 0; i < stringArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException nfe) {};
        }
        
        return intArray;
    }
    
    private void BlockIntoFysicalMatrix(Block block){
        for (int y = block.getMinY(); y <= block.getMaxY(); y++) {
            for (int x = block.getMinX(); x <= block.getMaxX(); x++) {
                this.fysicalMatrix[y][x] = block;
            }
        }
    }
    
    public String ToStringFysicalMatrix(){
        String returnString = new String();
        int counter = 0;
        for(Block[] blockArray: this.fysicalMatrix){
            returnString += "Row " + counter + ": ";
            for (Block block : blockArray) {
                returnString += block.getID()+ " ";
            }
            returnString += "\n";
            counter++;
        }
        return returnString;
    }
    
    
    
}
