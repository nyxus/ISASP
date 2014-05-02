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
    private ArrayList<Block> blockCollection = new ArrayList<Block>();
    private Boolean[][] dependencyMatrix;
    
    
    private Block[][] fysicalMatrix;
    
    public Marian(String file, int problemSize){
        floor = new Block(0, 0, problemSize, 0, 0);
        
        this.fysicalMatrix = new Block[problemSize+1][problemSize];
        
        
        // set floor into fyclicalMatrx at index
        
        for (int i = 0; i < problemSize; i++) {
            fysicalMatrix[0][i] = floor;
        }
        blockCollection.add(floor);
        
        ReadProblem(file);
        convertToDependencyMatrix();
        System.out.println(ToStringFysicalMatrix());
        System.out.println(ToStringBlockCollection());
        
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
                blockCollection.add(tempBlock);
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
                this.fysicalMatrix[y+1][x] = block;
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
    
    private void convertToDependencyMatrix(){
        Block currentBlock, prevBlock, currentDepencency, prevDepencency;
        prevDepencency = null;
        prevBlock = fysicalMatrix[1][0]; // set fist prevous bock as the current fist block 
        dependencyMatrix = new Boolean[blockCollection.size()][blockCollection.size()];
        prevDepencency = blockCollection.get(0);
        for (int y = 1; y < fysicalMatrix.length; y++) {
            for (int x = 0; x < fysicalMatrix[y].length; x++) {
                currentBlock = fysicalMatrix[y][x];
                currentDepencency = fysicalMatrix[y-1][x];
                
                if(prevBlock != currentBlock){
                    prevDepencency = null;
                    if(prevBlock != null){
                        prevBlock.setSibling(Block.RIGHT, currentBlock);
                        currentBlock.setSibling(Block.LEFT, prevBlock);
                    }
                }
                                
                if(prevDepencency != currentDepencency && currentDepencency !=  currentBlock){
                                        
                    dependencyMatrix[currentBlock.getID()][currentDepencency.getID()] = true; // set depencency of the current block
                    currentBlock.AddParent(currentDepencency);
                }
                
                prevBlock = currentBlock;
                prevDepencency = currentDepencency;
            }
            prevBlock = null;
        }        
    }
    
    public String ToStringBlockCollection(){
        String returnString = new String();
        for(Block block : blockCollection) {
            returnString += block.ToString(", ") + "\n";
        }
        return returnString;
    }
    
    public String ToStringDependencyMatrix(){
        String returnString = new String();
        
        for (int y = 0; y < dependencyMatrix.length; y++) {
            for (int x = 0; x < dependencyMatrix[y].length; x++) {
                
            }
        }
        
        return returnString;
        
    }
}
    
    
    

