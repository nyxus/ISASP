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
       Block asd2 = new Block(2, 1, 2, 3, 4);
       Block asd3 = new Block(3, 1, 2, 3, 4);
       Block asd4 = new Block(4, 1, 2, 3, 4);
       
       
      Block[] blocks = {asd1, asd2, asd3, asd4 };   
       Chromosome Chms = new Chromosome(1, new ArrayList(Arrays.asList(blocks)));
       
        //System.out.println(Chms.ToStringChromosome(":"));
        
        Marian problem = new Marian("probleemMet5", 5);
        problem.guidedSearch(100);
        int base;
        
        System.out.println(problem.ToStringFysicalMatrix());
        
        base = (problem.getBlockCollection().size()/2) * (1 + problem.getBlockCollection().size());
//        for (Chromosome Chromo : problem.getPopulation()) {
//            System.out.println("Fitness: " + problem.calculateFitness(Chromo, base));
//        }
                
    }    
}
