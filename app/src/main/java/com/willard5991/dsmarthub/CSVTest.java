package com.willard5991.dsmarthub;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by willard5991 on 10/10/2017.
 */

public class CSVTest {

    public static void main(String[] args) throws FileNotFoundException{
        String filename = "C:\\Users\\willard5991\\AndroidStudioProjects\\DSMArtHub\\app\\src\\main\\java\\com\\willard5991\\dsmarthub\\test.csv";
        int cats = 3;
        CSVReader reader = new CSVReader(filename,cats);
        reader.read();

        ArrayList<String[]> values = reader.getValues();

        for(String[] s : values){
            for(int j = 0; j<cats; j++){
                System.out.print(s[j]+'|');
            }
            System.out.println();
        }
    }
}
