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
public class Chromosome <B> {
    private ArrayList<B> Sequence = new ArrayList<B>();
    private int ID;
    
    public void AddBlockToSequence( B newBlock){
       this.Sequence.add(newBlock);
    }  
    
    public ArrayList<B> GetSelection(int Min,int Max){
        ArrayList<B> selection = new ArrayList<B>();
        for (int i = Min; i < Max ; i++) {
            selection.get(i); 
        }
        return selection;
    }
    
    
    
}
