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
 * @author LAPTOPPT
 */
public class ISASP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Block asd1 = new Block(1, 1, 2, 3, 4);
       Block asd2 = new Block(22, 1, 2, 3, 4);
       Block asd3 = new Block(3, 1, 2, 3, 4);
       Block asd4 = new Block(10, 1, 2, 3, 4);
       int popSize = 300;
       
      Block[] blocks = {asd4, asd1, asd2, asd3,  };   
      Chromosome Chms = new Chromosome(1, new ArrayList(Arrays.asList(blocks)));
       
       // System.out.println(Chms.ToStringChromosome(", "));
        
        Marian problem = new Marian("probleemMet5");
        Population pop = problem.generatePopulation(popSize);
        problem.run(pop, popSize);
//        problem.pseudoMutation(pop);
        //problem.crossOver(pop);
        
        
        /*Chromosome chr = new Chromosome(0, pop.getList().get(0).GetSelection(0, 6));
        System.out.println(chr.ToString());
        problem.guidedSearch(chr);
        System.out.println(chr.ToString());
        */
        //System.out.println("Gener " + "st" + ": Max: " + pop.getMax() + "  Min: " + pop.getMin() + "  AVG: " + (pop.getTotalFitness()/(double)pop.getList().size()));
        /*
        for (int gerneration = 0; gerneration < 100; gerneration++) {
            pop = problem.crossover(pop, popSize);
            pop = problem.getSelecetion(pop, popSize);
            System.out.println("Gener " + gerneration + " : Max: " + pop.getMax() + "  Min: " + pop.getMin() + "  AVG: " + (pop.getTotalFitness()/(double)pop.getList().size()));
        }
        */
        /*
        int gernerations = 0;
        while(pop.getMax() != pop.getMin()){
            pop = problem.crossover(pop, popSize);
            pop = problem.getSelecetion(pop, popSize);
            System.out.println("Gener " + gernerations + ": Max: " + pop.getMax() + "  Min: " + pop.getMin() + "  AVG: " + (pop.getTotalFitness()/(double)pop.getList().size()));
            gernerations++;
        }
        */        
    }    
}
