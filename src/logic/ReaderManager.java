package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReaderManager {

    public static BufferedReader createReader(){
        /*conectare la fisier de input si output:
        - verificam daca exista fisierele in acea cale: avem nevoie de un File
        - flux de caractere: FileReader
        - operatii avansate (readline()): BufferedReader
	 */
        //putem verifica initial daca resources exista si eventual puteti arunca exceptie daca nu exista

        File inputFile = new File("resources/input.txt");
        System.out.println(inputFile.getAbsolutePath());

        BufferedReader reader = null;
        try {
            reader =  new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("eroare");
        }
        return  reader;
    }
}
