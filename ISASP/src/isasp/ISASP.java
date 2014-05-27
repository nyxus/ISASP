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
       
       
      Block[] blocks = {asd4, asd1, asd2, asd3,  };   
        Chromosome Chms = new Chromosome(1, new ArrayList(Arrays.asList(blocks)));
       
       // System.out.println(Chms.ToStringChromosome(", "));
        
        Marian problem = new Marian("probleemMet5");
        Population pop = problem.guidedSearch(100);
        System.out.println(pop.toString());
        //int base;
        
       // base = (problem.getBlockCollection().size()/2) * (1 + problem.getBlockCollection().size());
//        for (Chromosome Chromo : problem.getPopulation()) {
//            System.out.println("Fitness: " + problem.calculateFitness(Chromo, base));
//        }
                
    }    
}
