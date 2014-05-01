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
    private ArrayList<Block> Sequence = new ArrayList<Block>();
    private int ID;
    
    public void AddBlockToSequence( Block newBlock){
       this.Sequence.add(newBlock);
    }  
    
    public ArrayList<Block> GetSelection(int Min,int Max){
        ArrayList<Block> selection = new ArrayList<Block>();
        for (int i = Min; i < Max ; i++) {
            selection.get(i); 
        }
        return selection;
    }
    
    public void PrintChromosome(){
        this.PrintChromosome(",");
    }
    
    public String PrintChromosome(String devider){
        String output = new String(); 

        int size = this.Sequence.size();
        for (int i = 0; i < Sequence.size(); i++) { 
            output += this.Sequence.get(i).getID();
            if (--size != 0) {
               output += devider + " ";
            }
        }
        return output; 
    } 
}
