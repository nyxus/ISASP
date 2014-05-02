/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isasp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Gerco en Peter
 */
public class Block {
    private int ID;
    private int MinX;
    private int MaxX;
    private int MinY;
    private int MaxY;
    private ArrayList<Block> Parents;
    private Block[] Siblings;
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    
    public Block(int ID, int MinX, int MaxX, int MinY, int MaxY) {
        this.ID = ID;
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.MinY = MinY;
        this.MaxY = MaxY;

        Parents = new ArrayList<>();
        Siblings = new Block[2] ;
    }
    
    public Block(int ID, int MinX, int MaxX, int MinY, int MaxY, ArrayList<Block> Parents, Block[] Siblings) {
        this.ID = ID;
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.MinY = MinY;
        this.MaxY = MaxY;
        this.Parents = Parents;
        this.Siblings = Siblings;
    }
      
    public void AddParents(ArrayList<Block> block){
        for(int i = 0; i < block.size(); i++){
            this.Parents.add(block.get(i));
        }
    }    
      
    
      
    public void AddParent(Block block){
        this.Parents.add(block);
    }    
     
    
    public void PrintBlock(String separator){
        System.out.println(ID + separator + MinX + separator + MaxX + separator + MinY + separator + MaxY + separator + Parents.toString() + separator + Siblings.toString());
    }
    public String ToString(String separator){
       String returnString = new String();
       
       returnString  = ID + separator;
       returnString  += MaxX + separator;
       returnString  += MinX + separator;
       returnString  += MaxY + separator;
       returnString  += MinY + separator;
       returnString  += "[";
        for (Block block : Parents) {
            returnString  += block.getID() + separator;
        }
       returnString  += "]"+ separator;;
       returnString  += "[";
        for (Block block : Siblings) {
            if(block != null){
                returnString  += block.getID() + separator;
            }
        }
       returnString  += "]"+ separator;;
       
       return returnString;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMinX() {
        return MinX;
    }

    public void setMinX(int MinX) {
        this.MinX = MinX;
    }

    public int getMaxX() {
        return MaxX;
    }

    public void setMaxX(int MaxX) {
        this.MaxX = MaxX;
    }

    public int getMinY() {
        return MinY;
    }

    public void setMinY(int MinY) {
        this.MinY = MinY;
    }

    public int getMaxY() {
        return MaxY;
    }

    public void setMaxY(int MaxY) {
        this.MaxY = MaxY;
    }

    public ArrayList<Block> getParents() {
        return Parents;
    }

    public void setParents(ArrayList<Block> Parents) {
        this.Parents = Parents;
    }

    public Block[] getSiblings() {
        return Siblings;
    }
    
    public void setSiblings(Block[] block){
        this.Siblings = block;
    } 
    
    public void setSibling(int side, Block sibling){
        switch(side){
            case LEFT:
                this.Siblings[Block.LEFT] =  sibling;
                break;
            case RIGHT:
                this.Siblings[Block.RIGHT] =  sibling;
                break; 
        }
    }
    
    public Block getSibling(int side){
        switch(side){
            case LEFT:
                return this.Siblings[Block.LEFT];
            case RIGHT:
                return this.Siblings[Block.RIGHT];
        }
        return null;
    }
}
