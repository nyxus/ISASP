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
import java.util.Random;
/**
 *
 * @author Gerco
 */
public class Marian {

    
    private ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    private Block floor = new Block(0, 1, 1, 0, 0);
    private ArrayList<Block> blockCollection = new ArrayList<Block>();
    private Boolean[][] dependencyMatrix;
    
    private Block[][] fysicalMatrix;
    
    public Marian(String filename, int problemSize){
        floor = new Block(0, 0, problemSize, 0, 0);
        
        this.fysicalMatrix = new Block[problemSize+1][problemSize];
        
        
        // set floor into fyclicalMatrx at index
        
        for (int i = 0; i < problemSize; i++) {
            fysicalMatrix[0][i] = floor;
        }
        blockCollection.add(floor);
        
        ReadProblem(ISASP.class.getResource(filename).getPath());
        convertToDependencyMatrix();
        //System.out.println(ToStringFysicalMatrix());
        //System.out.println(ToStringBlockCollection());
        //System.out.println(ToStringDependencyMatrix());
        
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
                blockCollection.add(tempBlock.getID(), tempBlock);
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
    
    private void initializeDependencyMatrix(int size, boolean default_val){
        dependencyMatrix = new Boolean[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dependencyMatrix[i][j] = default_val;
            }
  
        }
    }
    
    private void convertToDependencyMatrix(){
        Block currentBlock, prevBlock, currentDepencency, prevDepencency;
        prevDepencency = null;
        prevBlock = fysicalMatrix[0][0]; // set fist prevous bock as the current fist block 
        
        initializeDependencyMatrix(blockCollection.size(), false);
                
        prevDepencency = blockCollection.get(0); // get floor
        fysicalMatrix[1][0].AddParent(prevDepencency); // add the floor tu the rist current block
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
        returnString = "   /";
        for(int i = 0; i < dependencyMatrix[0].length; i++) {
            returnString += i + " ";
        }
        returnString += "\n";
        for (int y = 0; y < dependencyMatrix.length; y++) {
            if(y < 10){
               returnString += " " + y + "| "; 
            }else{
                returnString += y + "| ";
            }
            for (int x = 0; x < dependencyMatrix[y].length; x++) {
                if(dependencyMatrix[y][x]){
                    returnString += "1 ";
                    
                }else{
                    returnString += "0 ";
                }
            }
             returnString +="\n";
        }
        
        return returnString;
        
    }
    
    public void guidedSearch(int populationSize){
        Block currentBlock;
        int randomNumber;  
        int popuationCount = 0;
        
        while(populationSize > popuationCount){
            Chromosome newChrom = new Chromosome(popuationCount);
            Block[] doneStack = new Block[blockCollection.size()];
            ArrayList<Block> possibleStack = new ArrayList<>();
            possibleStack.add(floor); // add floor to start of stack
            
            while(possibleStack.size() > 0){                
                Random random = new Random();
                randomNumber = random.nextInt(possibleStack.size());
                currentBlock = possibleStack.get(randomNumber);
                doneStack[currentBlock.getID()] = currentBlock;
                possibleStack.remove(currentBlock);
                newChrom.AddBlockToSequence(currentBlock);
                
                possibleStack.addAll(getPossibleNextBlocks(currentBlock, doneStack));
                // get random from current stack
                // add to done
                // add to sequence 
                // search for new possible 
                
            }
            population.add(newChrom);
            popuationCount++; 
        }
        
    }
    
    private ArrayList<Block> getPossibleNextBlocks(Block currentBlock, Block[] doneStack){
        ArrayList<Block> possibleBlocks = new ArrayList<>();
        Block tempBlock;
        for (int i = 0; i < blockCollection.size(); i++) {
            if (dependencyMatrix[i][currentBlock.getID()]) {
                tempBlock = blockCollection.get(i);
                Boolean addBlock = true;
                for (Block block : tempBlock.getParents()) {
                    if (doneStack[block.getID()] == null) {
                       addBlock = false;
                       break;
                    }
                }
                if(addBlock){
                    possibleBlocks.add(tempBlock);
                }
            }
                     
        }
        return possibleBlocks;
    }
    
    public double calculateFitness(Chromosome chromosome, int base){
        double fitness = 0;
        int totalCount = 0 ;
        int currentPos = 0;
        Block prevBlock = floor;
       // Block currentBlock;
        
        for(Block currentBlock : chromosome.getSequence()) {
           if(currentBlock != floor){
              totalCount += Math.abs(currentBlock.getID()- prevBlock.getID()); 
           }
           
           prevBlock = currentBlock;
           
        }
        fitness = (float) totalCount / base ;
        return 1 - fitness;
    }
    
    public ArrayList<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Chromosome> population) {
        this.population = population;
    }

    public Block getFloor() {
        return floor;
    }

    public void setFloor(Block floor) {
        this.floor = floor;
    }

    public ArrayList<Block> getBlockCollection() {
        return blockCollection;
    }

    public void setBlockCollection(ArrayList<Block> blockCollection) {
        this.blockCollection = blockCollection;
    }

    public Boolean[][] getDependencyMatrix() {
        return dependencyMatrix;
    }

    public void setDependencyMatrix(Boolean[][] dependencyMatrix) {
        this.dependencyMatrix = dependencyMatrix;
    }

    public Block[][] getFysicalMatrix() {
        return fysicalMatrix;
    }

    public void setFysicalMatrix(Block[][] fysicalMatrix) {
        this.fysicalMatrix = fysicalMatrix;
    }
}

