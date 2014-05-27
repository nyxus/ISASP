/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isasp;
import java.util.ArrayList;

/**
 *
 * @author Gerco
 */
public class Chromosome {

    private ArrayList<Block> sequence = new ArrayList<Block>();
    private int id;
    private double fitness; 
    private int size = 0;
    
    private Block prevBlock;
    
    public Chromosome(int ID){
        this.id = ID;
    }
    public Chromosome(int ID, ArrayList<Block> blocks){
        this.id = ID;
        this.sequence = blocks;
        CalculateSize();
    }
    
    public void AddBlockToSequence( Block newBlock){
       if(sequence.isEmpty()){
           prevBlock = newBlock;
       }
       // calcualte new block size
       size += Math.abs(newBlock.getID() - prevBlock.getID());
       this.sequence.add(newBlock);
       prevBlock = newBlock;
    } 
    
    private void CalculateSize(){
        prevBlock = sequence.get(0);
        
        for(Block currentBlock : sequence) {
            
            size += Math.abs(currentBlock.getID() - prevBlock.getID());
            prevBlock = currentBlock;
            
        }
        
    }
    
    public ArrayList<Block> GetSelection(int Min,int Max){
        ArrayList<Block> selection = new ArrayList();
        for (int i = Min; i < Max ; i++) {
            selection.get(i); 
        }
        return selection;
    }
    
    public String ToString(){
        return this.ToStringChromosome(", ");
    }
    
    public String ToStringChromosome(String devider){
        String output = new String();
        output = "id:" + this.id + "| ";
        int size = this.sequence.size();
        for (int i = 0; i < sequence.size(); i++) { 
            output += this.sequence.get(i).getID();
            if (--size != 0) {
               output += devider;
            }
        }
        output += devider + "size: " + this.size; 
        output += devider + "fitness: " + this.fitness; 
        return output; 
    }
    
    public ArrayList<Block> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<Block> sequence) {
        this.sequence = sequence;
    }
    
    public Block GetBlocBykIndex(int index){
        return this.sequence.get(index);
    }
    
    public void AddBlockArrayToSequence(ArrayList<Block> Blocks){
        this.sequence.addAll(Blocks);
    }
    
    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the Size
     */
    public int getSize() {
        return size;
    }

}
