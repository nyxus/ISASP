package isasp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Marian {
    
    private Block floor = new Block(0, 1, 1, 0, 0);
    
    //Population is een ArrayList van Chromosomen. 
    private ArrayList<Chromosome> population = new ArrayList<Chromosome>();
        
    //BlockCollection bevat alle blocks van een probleem.
    private ArrayList<Block> blockCollection = new ArrayList<Block>();
    
    //DependencyMatrix is een array van booleans welke beschrijft welke blokken benodigd zijn om een desbetreffende block te plaatsen.
    private Boolean[][] dependencyMatrix;
    
    //FysicalMatrix is een array met daarin de blokken hoe deze daadwerkelijk opgestapeld zijn.
    private Block[][] fysicalMatrix;

    public Marian(String filename, int problemSize) {
        floor = new Block(0, 0, problemSize, 0, 0);

        this.fysicalMatrix = new Block[problemSize + 1][problemSize];

        // set floor into fyclicalMatrx at index
        for (int i = 0; i < problemSize; i++) {
            fysicalMatrix[0][i] = floor;
        }
        
        blockCollection.add(floor);

        ReadProblem(ISASP.class.getResource(filename).getPath());
        convertToDependencyMatrix();
        System.out.println("Fysical Matrix -\n"+ToStringFysicalMatrix());
        System.out.println("Block Collection -\n"+ToStringBlockCollection());
        System.out.println("Dependency Matrix -\n"+ToStringDependencyMatrix());

    }

    //Readproblem 
    //  Beschrijving: ReadProblem leest het bestand in het opgegeven pad en maakt er een fysieke matrix van blokken van.
    //  Input: De locatie van het uit te lezen bestand.
    //  Output: -
    //  Gemaakt door: Gerco Versloot.
    public void ReadProblem(String file) {
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
                tempBlock = StringToBlock(strLine);
                blockCollection.add(tempBlock.getID(), tempBlock);
                BlockIntoFysicalMatrix(tempBlock);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    //StringToBlock
    //  Beschrijving: StringToBlock maakt van een regel uit het door ReadProblem uitgelezen bestand een Block.
    //  Input: Een regel van een door het programma GeneticProblem gegenereerd bestand.
    //  Output: Een Block.
    //  Gemaakt door: Gerco Versloot.
    public Block StringToBlock(String inputBlock) {
        String[] tempStringBlock;
        Integer[] tempIntBlock;

        //Verwijder alle spaties uit de regel
        tempStringBlock = inputBlock.split(" ");

        //Maak van de string een int array
        tempIntBlock = StringToIntArray(tempStringBlock);

        //Maak een nieuwe block aan met de verkregen waarden uit de array
        Block returnBlock = new Block(tempIntBlock[0], tempIntBlock[1], tempIntBlock[2], tempIntBlock[3], tempIntBlock[4]);

        return returnBlock;
    }

    //StringToIntArray
    //  Beschrijving: StringToIntArray loopt de door StringToBlock verkregen Stringarray door om deze vervolgens naar Integers te parsen en deze in een array terug te geven.
    //  Input: Een stringarray met daarin alleen cijfers
    //  Output: Een integerarray met daarin alleen Integers
    //  Gemaakt door: Gerco Versloot
    static Integer[] StringToIntArray(String[] stringArray) {
        Integer[] intArray = new Integer[stringArray.length];

        //Loop door de array en parse elk character naar een integer
        for (int i = 0; i < stringArray.length; i++) {
            try {
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
        fysicalMatrix[1][0].AddParent(prevDepencency); // add the floor tu the rist current block
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
    public void guidedSearch(int populationSize) {
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
            population.add(newChrom);
            popuationCount++;
        }

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

    //calculateFitness
    //  Beschrijving: calculateFitness
    //  Input: 
    //  Output:
    //  Gemaakt door: Gerco Versloot
    public double calculateFitness(Chromosome chromosome, int base) {
        double fitness = 0;
        int totalCount = 0;
        int currentPos = 0;
        Block prevBlock = floor;
       // Block currentBlock;

        for (Block currentBlock : chromosome.getSequence()) {
            if (currentBlock != floor) {
                totalCount += Math.abs(currentBlock.getID() - prevBlock.getID());
            }

            prevBlock = currentBlock;

        }
        fitness = (float) totalCount / base;
        return 1 - fitness;
    }

    //getPopulation
    //  Beschrijving: getPopulation returned de population.
    //  Input: -
    //  Output: Een populatie.
    //  Gemaakt door: Gerco Versloot    
    public ArrayList<Chromosome> getPopulation() {
        return population;
    }

    //setPopulation
    //  Beschrijving: setPopulation slaat de opgegeven ArrayList met Chromosomen op als population.
    //  Input: Een populatie.
    //  Output: - 
    //  Gemaakt door: Gerco Versloot 
    public void setPopulation(ArrayList<Chromosome> population) {
        this.population = population;
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
