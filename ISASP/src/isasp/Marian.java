package isasp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class Marian {
    private Block floor = new Block(0, 1, 1, 0, 0);
    
    //Population is een ArrayList van Chromosomen. 
    
        
    //BlockCollection bevat alle blocks van een probleem.
    private ArrayList<Block> blockCollection = new ArrayList<>();
    
    //DependencyMatrix is een array van booleans welke beschrijft welke blokken benodigd zijn om een desbetreffende block te plaatsen.
    private Boolean[][] dependencyMatrix;
    
    //FysicalMatrix is een array met daarin de blokken hoe deze daadwerkelijk opgestapeld zijn.
    private Block[][] fysicalMatrix;
    
    private Chromosome bestMin;

    /**
     * Create a problem to solve with the algorithm of Marian
     * @param filename the file location that contains the blocks information, id minX maxX minY maxY
     */
    public Marian(String filename) {
        int problemSize = getProblemSize(filename);
        
        System.out.println("ProblemSize = "+ problemSize);
        
        floor = new Block(0, 0, problemSize, 0, 0);

        this.fysicalMatrix = new Block[problemSize + 1][problemSize];

        // set floor into fyclicalMatrx at index
        for (int i = 0; i < problemSize; i++) {
            fysicalMatrix[0][i] = floor;
        }
        
        blockCollection.add(floor);
        
        ReadProblem(filename);
        
        convertToDependencyMatrix();
       // System.out.println("- Fysical Matrix -\n"+ToStringFysicalMatrix());
        //System.out.println("- Block Collection -\n"+ToStringBlockCollection());
       // System.out.println("- Dependency Matrix -\n"+ToStringDependencyMatrix());

    }
    
    //getProblemSize
    //  Beschrijving: getProblemSize leest het bestand in het opgegeven pad en berekend hieruit de groote van het probleem.
    //  Input: De locatie van het uit te lezen bestand.
    //  Output: problemSize als een Integer.
    //  Gemaakt door: Peter Tielbeek.
    public int getProblemSize(String filename){
        int problemSize = 0;
        int compare = 0;
        String splitarray[];
        
        try {
            //Open bestand op de opgegeven locatie
            FileInputStream fstream = new FileInputStream(ISASP.class.getResource(filename).getPath());

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //Lees bestand per regel
            while ((strLine = br.readLine()) != null) {
                splitarray = strLine.split(" ");
                
                compare = Integer.parseInt(splitarray[2]);
                
                if(compare > problemSize){
                    problemSize = compare;
                }
            }
            
            //Close the input stream
            in.close();
            return problemSize+1;
        } catch (Exception e) {
            //Catch exception if any
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }


    
    /**
     * Reads a problem file and converts a block object, adds the block to the blockColletion and converts to the fysical matrix, . The fysical matrix is a visual representation of the file  
     * @param file the file location that contains the problem
     * @autor Gerco Versloot
     * @return the fysical matrix
     */
    public void ReadProblem(String file) {  
        try {
            //Opens the file from the file location
            FileInputStream fstream = new FileInputStream(ISASP.class.getResource(file).getPath());

            // Read the data from the file into a BufferedReader
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Block tempBlock;

            // Loop through each line untill there a no more lines
            while ((strLine = br.readLine()) != null) {
                
                // converts line to a block
                tempBlock = StringToBlock(strLine);
                
                // add block to the blockCollection
                blockCollection.add(tempBlock.getID(), tempBlock);
                
                // add Block to Fysical matrix
                BlockIntoFysicalMatrix(tempBlock);
            }
            
            //Close the input stream
            in.close();
            
        } catch (Exception e) {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Converts a string (a line of the problem file) to a block object, only numbers in the string are allowed
     * @param inputBlock string format: id minX maxX minY maxY, only numbers
     * @return a block object
     * @autor Gerco Versloot
     */
    public Block StringToBlock(String inputBlock) {
        String[] tempStringBlock;
        Integer[] tempIntBlock;

        // Split the string to a string array, split is done at a white space
        tempStringBlock = inputBlock.split(" ");

       // Convert the string array to a interger array, only possible if the strings are numbers
        tempIntBlock = StringToIntArray(tempStringBlock);

        // Create a new block object wiht the numbers from the interger array
        Block returnBlock = new Block(tempIntBlock[0], tempIntBlock[1], tempIntBlock[2], tempIntBlock[3], tempIntBlock[4]);

        return returnBlock;
    }

    //StringToIntArray
    //  Beschrijving: StringToIntArray loopt de door StringToBlock verkregen Stringarray door om deze vervolgens naar Integers te parsen en deze in een array terug te geven.
    //  Input: Een stringarray met daarin alleen cijfers
    //  Output: Een integerarray met daarin alleen Integers
    //  Gemaakt door: Gerco Versloot
    
    /**
     * Converts a string array with numbers to a intergers array with the same numbers 
     * @param stringArray the array with numbers to convert to intergers
     * @return int array with only intergers 
     * @autor Gerco Versloot
     */
    static Integer[] StringToIntArray(String[] stringArray) {
        Integer[] intArray = new Integer[stringArray.length];

        //Loop throug all string array the elements 
        for (int i = 0; i < stringArray.length; i++) {
            try {
                // Try to convert single string number to a interger
                intArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException nfe) {
            };
        }

        return intArray;
    }

    //BlockIntoFysicalMatrix
    //  Beschrijving: Zet een opgegeven block in de FysicalMatrix.
    //  Input: Een Block
    //  Output: -
    //  Gemaakt door: Gerco Versloot
    private void BlockIntoFysicalMatrix(Block block) {
        for (int y = block.getMinY(); y <= block.getMaxY(); y++) {
            for (int x = block.getMinX(); x <= block.getMaxX(); x++) {
                this.fysicalMatrix[y + 1][x] = block;
            }
        }
    }

    //ToStringFysicalMatrix
    //  Beschrijving: ToStringFysicalMatrix zet de volledige FysicalMatrix om in een string.
    //  Input: -
    //  Output: De volledige FysicalMatrix in een String.
    //  Gemaakt door: Gerco Versloot
    public String ToStringFysicalMatrix() {
        String returnString = new String();
        int counter = 0;
        for (Block[] blockArray : this.fysicalMatrix) {
            returnString += "Row " + counter + ": ";
            for (Block block : blockArray) {
                returnString += block.getID() + " ";
            }
            returnString += "\n";
            counter++;
        }
        return returnString;
    }

    //initializeDependencyMatrix
    //  Beschrijving: initializeDependencyMatrix vult en genereerd de DependencyMatrix met een opgegeven waarde.
    //  Input: De grootte van de DependencyMatrix, De waarde waarmee deze volledig gevuld wordt.
    //  Output: -
    //  Gemaakt door: Gerco Versloot
    private void initializeDependencyMatrix(int size, boolean default_val) {
        dependencyMatrix = new Boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dependencyMatrix[i][j] = default_val;
            }

        }
    }

    //convertToDependencyMatrix
    //  Beschrijving: 
    //  Input: 
    //  Output:
    //  Gemaakt door: Gerco Versloot
    private void convertToDependencyMatrix() {
        Block currentBlock, prevBlock, currentDepencency, prevDepencency;
        prevDepencency = null;
        prevBlock = fysicalMatrix[0][0]; // set first previous block as the current first block 

        initializeDependencyMatrix(blockCollection.size(), false);

        prevDepencency = blockCollection.get(0); // get floor
        fysicalMatrix[1][0].AddParent(prevDepencency); // add the floor to the first current block
        for (int y = 1; y < fysicalMatrix.length; y++) {
            for (int x = 0; x < fysicalMatrix[y].length; x++) {
                currentBlock = fysicalMatrix[y][x];
                currentDepencency = fysicalMatrix[y - 1][x];

                if (prevBlock != currentBlock) {
                    prevDepencency = null;
                    if (prevBlock != null) {
                        prevBlock.setSibling(Block.RIGHT, currentBlock);
                        currentBlock.setSibling(Block.LEFT, prevBlock);
                    }
                }

                if (prevDepencency != currentDepencency && currentDepencency != currentBlock) {

                    dependencyMatrix[currentBlock.getID()][currentDepencency.getID()] = true; // set depencency of the current block
                    currentBlock.AddParent(currentDepencency);
                }

                prevBlock = currentBlock;
                prevDepencency = currentDepencency;
            }
            prevBlock = null;
        }
    }

    //ToStringBlockCollection
    //  Beschrijving: ToStringBlockCollection zet alle blokken netjes in een lijst om zo duidelijk te maken welke blokken er zijn.
    //  Input: -
    //  Output: Een string met daarin alle blokken.
    //  Gemaakt door: Gerco Versloot
    public String ToStringBlockCollection() {
        String returnString = new String();
        for (Block block : blockCollection) {
            returnString += block.ToString(", ") + "\n";
        }
        return returnString;
    }

    //ToStringDependencyMatrix
    //  Beschrijving: ToStringDependencyMatrix zet de gehele DependencyMatrix in een nette string zodat deze grafisch weer te geven is.
    //  Input: -
    //  Output: Een String met daarin de DependencyMatrix.
    //  Gemaakt door: Gerco Versloot
    public String ToStringDependencyMatrix() {
        String returnString = new String();
        returnString = "   /";
        for (int i = 0; i < dependencyMatrix[0].length; i++) {
            returnString += i + " ";
        }
        returnString += "\n";
        for (int y = 0; y < dependencyMatrix.length; y++) {
            if (y < 10) {
                returnString += " " + y + "| ";
            } else {
                returnString += y + "| ";
            }
            for (int x = 0; x < dependencyMatrix[y].length; x++) {
                if (dependencyMatrix[y][x]) {
                    returnString += "1 ";

                } else {
                    returnString += "0 ";
                }
            }
            returnString += "\n";
        }

        return returnString;

    }

    //guidedSearch
    //  Beschrijving: guidedSearch
    //  Input: 
    //  Output:
    //  Gemaakt door: Gerco Versloot
    public Population guidedSearch(int populationSize) {
        Population pop = new Population();
        Block currentBlock;
        int randomNumber;
        int popuationCount = 0;

        while (populationSize > popuationCount) {
            Chromosome newChrom = new Chromosome(popuationCount);
            Block[] doneStack = new Block[blockCollection.size()];
            ArrayList<Block> possibleStack = new ArrayList<>();
            possibleStack.add(floor); // add floor to start of stack

            while (possibleStack.size() > 0) {
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
            pop.addChromosome(newChrom);
            popuationCount++;
        }
        return pop;
    }

    //getPossibleNextBlocks
    //  Beschrijving: getPossibleNextBlocks
    //  Input: 
    //  Output:
    //  Gemaakt door: Gerco Versloot
    private ArrayList<Block> getPossibleNextBlocks(Block currentBlock, Block[] doneStack) {
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
                if (addBlock) {
                    possibleBlocks.add(tempBlock);
                }
            }

        }
        return possibleBlocks;
    }
    
    
    //crossover --> is niet de werkelijke crossover, ik heb een verdubelaar gemaakt van een populatie, had ik even nodig :p
    public Population crossover(Population parrents, int amountChilds){
        for (int i = 0; i < amountChilds; i++) {
            parrents.addChromosome(parrents.getList().get(i));
        }
        return parrents;
    }
    
    /**
     * generates a new population based on fitness of a other population. 
     * In general this selection will select the best and some worst Chromosomes   
     * @param oldPop The input population to generate a new selected population from 
     * @param newPopSize The size of the new population
     * @return The new population
     */
    public Population getSelecetion(Population oldPop, int newPopSize){
        Population newPop = new Population();
        double prevTotalFtnss = 0;
        PriorityQueue<Double> randFitnss = new PriorityQueue<>();
        Random r = new Random();
        oldPop.sortByFitness();
        
        // Add random doubles to a sored queue
        // This random doubels will select the Chromosomes based on its fitness
        for (int i = 0; i < newPopSize; i++) {
          randFitnss.add((oldPop.getTotalFitness()  * r.nextDouble()));
        }

        /* Loop through all old pouplation and if the fitnss matcheses with 
        * the fitness, add it to the new pouplation
        */
        for (Iterator<Chromosome> it = oldPop.getList().iterator(); it.hasNext();) {
            Chromosome curChr = it.next();
            
            while( (prevTotalFtnss + curChr.getFitness()) >= randFitnss.element() ){
                // if: prevous chomesome fitnes < random selection fitness <= current chromesome fitness
                if( prevTotalFtnss < randFitnss.element() && randFitnss.element() <= (prevTotalFtnss + curChr.getFitness()) ){
                    newPop.addChromosome(curChr);
                    randFitnss.remove();
                    if(randFitnss.isEmpty()){
                        return newPop;
                    }
                }
            }
            prevTotalFtnss += curChr.getFitness();
        }
        
        return newPop;
        
    }
    

    
    //getFloor
    //  Beschrijving: getFloor returned de floor.
    //  Input: -
    //  Output: De floor.
    //  Gemaakt door: Gerco Versloot 
    public Block getFloor() {
        return floor;
    }

    //setFloor
    //  Beschrijving: setFloor slaat het opgegeven Block op als floor.
    //  Input: Een Block.
    //  Output: - 
    //  Gemaakt door: Gerco Versloot 
    public void setFloor(Block floor) {
        this.floor = floor;
    }

    //getBlockCollection
    //  Beschrijving: getBlockCollection returned de BlockCollection.
    //  Input: -
    //  Output: De Blockcollection.
    //  Gemaakt door: Gerco Versloot 
    public ArrayList<Block> getBlockCollection() {
        return blockCollection;
    }

    //setBlockCollection
    //  Beschrijving: setBlockCollection slaat de opgegeven ArrayList van Blocken op als BlockCollection.
    //  Input:  Een BlockCollection.
    //  Output: - 
    //  Gemaakt door: Gerco Versloot 
    public void setBlockCollection(ArrayList<Block> blockCollection) {
        this.blockCollection = blockCollection;
    }

    //getDependencyMatrix
    //  Beschrijving: getDependencyMatrix returned de DependencyMatrix.
    //  Input: - 
    //  Output: Een DependencyMatrix.
    //  Gemaakt door: Gerco Versloot 
    public Boolean[][] getDependencyMatrix() {
        return dependencyMatrix;
    }

    //setDependencyMatrix
    //  Beschrijving: setDependencyMatrix slaat de opgegeven Booleanarray op als DependencyMatrix.
    //  Input: Een DependencyMatrix als booleanarray.
    //  Output: -
    //  Gemaakt door: Gerco Versloot 
    public void setDependencyMatrix(Boolean[][] dependencyMatrix) {
        this.dependencyMatrix = dependencyMatrix;
    }

    //getFysicalMatrix
    //  Beschrijving: getFysicalMatrix returned de FysicalMatrix.
    //  Input: -
    //  Output: De FysicalMatrix.
    //  Gemaakt door: Gerco Versloot 
    public Block[][] getFysicalMatrix() {
        return fysicalMatrix;
    }

    //setFysicalMatrix
    //  Beschrijving: setFysicalMatrix slaat de opgegeven Blockarray op als FysicalMatrix.
    //  Input: Een FysicalMatrix als Blockarray.
    //  Output: -
    //  Gemaakt door: Gerco Versloot 
    public void setFysicalMatrix(Block[][] fysicalMatrix) {
        this.fysicalMatrix = fysicalMatrix;
    }
}
