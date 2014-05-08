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
    
    public Chromosome(int ID){
        this.id = ID;
    }
    public Chromosome(int ID, ArrayList<Block> blocks){
        this.id = ID;
        this.sequence = blocks;
    }
    
    public void AddBlockToSequence( Block newBlock){
       this.sequence.add(newBlock);
    }  
    
    public ArrayList<Block> GetSelection(int Min,int Max){
        ArrayList<Block> selection = new ArrayList<Block>();
        for (int i = Min; i < Max ; i++) {
            selection.get(i); 
        }
        return selection;
    }
    
    public String ToString(){
        return this.ToStringChromosome(",");
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
        return output; 
    } 
    
    public Block GetBlocBykIndex(int index){
        return this.sequence.get(index);
    }
    
    public void AddBlockArrayToSequence(ArrayList<Block> Blocks){
        this.sequence.addAll(Blocks);
    }
}
