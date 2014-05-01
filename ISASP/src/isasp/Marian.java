/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isasp;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 *
 * @author Gerco
 */
public class Marian {
    private ArrayList<Chromosome> population;
    private Block floor = new Block(0, 1, 1, 0, 0);
    private Integer[][] matrix;
    
    
    public void ReadProblem(String file){      
        try {
            //Open bestand op de opgegeven locatie
            FileInputStream fstream = new FileInputStream(file);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Lees bestand per regel
            while ((strLine = br.readLine()) != null) {
                //convertToMatrix(strLine, 1);
                System.out.println(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }   
    }
/*
    void convertToMatrix(String string, int Row) {
        //De te splitten string
        String str = string;
        String[] temp;

        //Symbool waarmee hij hem afkapt
        temp = str.split(" ");

        //voeg substrings toe aan array        
    }
    */
    
}
