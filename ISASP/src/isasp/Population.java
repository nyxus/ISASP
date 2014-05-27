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
 *
 */
public class Population {

    private ArrayList<Chromosome> list = new ArrayList<>();
    private int Max;
    private int Min;
    private int TotalFitness;

    /**
     * Adds a Chromosome to the population list and checks if new Max or Min
     *
     * @autor Gerco Versloot
     *
     * @param chr The Chromosome to add at the list
     */
    public void addChromosome(Chromosome chr) {
        if (chr.getSize() > getMax()) {
            setMax(chr.getSize());
        }
        if (chr.getSize() < getMin() || getMin() == 0) {
            setMin(chr.getSize());
        }
        getList().add(chr);
    }

    /**
     * Finds the Minimal and Maximal size of all Chromosomes in the current population list
     * @author Gerco Versloot
     */
    private void calculateMaxMin() {
        for (Chromosome chr : list) {
            if (chr.getSize() > getMax()) {
                setMax(chr.getSize());
            }
            if (chr.getSize() < getMin() || getMin() == 0 ) {
                setMin(chr.getSize());
            }
        }
    }
    
    public void calculateFitness(){
        double fitness;
        for (Chromosome chr : list) {
            fitness =  ( (double)getMin() / (double)chr.getSize());
            TotalFitness += fitness;
            chr.setFitness(fitness);
        }
       // System.out.println("total fitness: " + TotalFitness);
    }


  /*
     public double calculateFitness(Chromosome chromosome, int base) {
     double fitness = 0;
     int totalCount = 0;
     Block prevBlock = floor;

     for (Block currentBlock : chromosome.getSequence()) {
     if (currentBlock != floor) {
     totalCount += Math.abs(currentBlock.getID() - prevBlock.getID());
     }

     prevBlock = currentBlock;

     }
     fitness = (float) totalCount / base;
     return 1 - fitness;
     }
     */
    /**
     * @return a string of all Chromosomes in the population list
     * with an  new line as Saperator
     * @author Gerco Versloot
     */
    public String toString() {
        return toStringSaperator("\n");
    }

    /**
     * Chromosome id| bloks ids, Chromosome size
     * @param sap devider of between Chromosomes id 
     * @return a string of all Chromosomes in the population list
     * @author Gerco Versloot
     */
    public String toStringSaperator(String sap) {
        String returnString = new String();
        calculateFitness();
        int counter = 0;
        for (Chromosome Chromo : getList()) {
            returnString += counter++ + " [ " + Chromo.ToString()  + " ]" + sap;
        }
        return returnString;
    }

    /**
     * @return the population list
     */
    public ArrayList<Chromosome> getList() {
        calculateFitness();
        return list;
    }

    /**
     * @param list set the population list
     * Also automaticly finds the new Max and Min 
     * @author Gerco Versloot
     */
    public void setList(ArrayList<Chromosome> list) {
        this.list = list;
        calculateMaxMin();
    }

    /**
     * @return the Max
     * @author Gerco Versloot
     */
    public int getMax() {
        return Max;
    }

    /**
     * @param Max the Max to set
     * @author Gerco Versloot
     */
    private void setMax(int Max) {
        this.Max = Max;
    }

    /**
     * @return the Min
     * @author Gerco Versloot
     */
    public int getMin() {
        return Min;
    }

    /**
     * @param Min the Min to set
     * @author Gerco Versloot
     */
    private void setMin(int Min) {
        this.Min = Min;
    }

}
