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
    private int ID;
    private int MinX;
    private int MaxX;
    private int MinY;
    private int MaxY;
    private ArrayList<Block> Parents;
    private ArrayList<Block> Siblings;
    
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
      
    public void AddParents(ArrayList<Block> block){
        for(int i = 0; i < block.size(); i++){
            this.Parents.add(block.get(i));
        }
    }    
      
    public void AddSiblings(ArrayList<Block> block){
        for(int i = 0; i < block.size(); i++){
            this.Siblings.add(block.get(i));
        }
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
            returnString  += block.getID() + separator;
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

    public ArrayList<Block> getSiblings() {
        return Siblings;
    }

    public void setSiblings(ArrayList<Block> Siblings) {
        this.Siblings = Siblings;
    }
}
