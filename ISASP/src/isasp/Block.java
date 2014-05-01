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
public class Block {
    public int ID;
    public int MinX;
    public int MaxX;
    public int MinY;
    public int MaxY;
    public ArrayList<Block> Parents;
    public ArrayList<Block> Siblings;
    
    public void AddParent(Block block){
        this.Parents.add(block);
    }    
    
    public void AddSibling(Block block){
        this.Parents.add(block);
    }    
    
    public void PrintBlock(String separator){
        System.out.println(ID + separator + MinX + separator + MaxX + separator + MinY + separator + MaxY + separator + Parents.toString() + separator + Siblings.toString());
    }
}
