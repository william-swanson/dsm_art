package com.willard5991.dsmarthub;

/**
 * Created by willard5991 on 10/9/2017.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    private ArrayList<String[]> values; //the parsed CSV file
    String filename; //The file path and name of the CSV file
    int categories; //the number of categories found in the CSV file

    public CSVReader(String filename, int cats){
        this.filename = filename;
        this.categories = cats;
    }

    public void read() throws FileNotFoundException{
        values = new ArrayList<String[]>();
        Scanner scan = new Scanner(new File(filename));
        scan.useDelimiter(",");
        String[] line = new String[this.categories];
        int i = 0, j = 0;
        String[] holder = new String[this.categories];
        while(scan.hasNext()) {
            holder[j] = scan.next();
            j++;
            if (j == this.categories) {
                j = 0;
                this.values.add(holder);
            }
        }
    }

    public ArrayList<String[]> getValues(){
        return this.values;
    }
}
