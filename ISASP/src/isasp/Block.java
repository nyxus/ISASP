/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isasp;

import java.util.ArrayList;

/**
 *
 * @author Gerco en Peter
 */
public class Block {
    public int ID;
    public int MinX;
    public int MaxX;
    public int MinY;
    public int MaxY;
    public ArrayList<Block> Parents;
    public ArrayList<Block> Siblings;
    
    public Block(int ID, int MinX, int MaxX, int MinY, int MaxY) {
        this.ID = ID;
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.MinY = MinY;
        this.MaxY = MaxY;

        Parents = new ArrayList<>();
        Siblings = new ArrayList<>();
    }
    
    public Block(int ID, int MinX, int MaxX, int MinY, int MaxY, ArrayList<Block> Parents, ArrayList<Block> Siblings) {
        this.ID = ID;
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.MinY = MinY;
        this.MaxY = MaxY;
        this.Parents = Parents;
        this.Siblings = Siblings;
    }
      
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
