package org.kaismh.solr;

import java.io.File;

/**
 * Created by kaismh on 7/29/16.
 */
public class CMDLineCreator {

    public static void main(String ... args)
    {
        if(args.length != 2 ) {

            System.out.println("Invalid arguments, you must specify both properties file and output file");
            System.out.println("Usage: java -jar solr-elevate-creator-1.0.0 propertiesFile outputFile");
            return;
        }

        if(!validateInput(args)) {
            return;
        }

        String propertiesFile = args[0];
        String outputFile = args[1];

        //Todo: add logic for command line elevate creator

    }

    private static boolean validateInput(String ... args) {

        if(args[0].equals(args[1])) {
            System.out.println("Invalid arguments, properties file should not be the same as output file");
            return false;
        }

        File propertiesFile= new File(args[0]);

        if(!propertiesFile.exists()) {
            System.out.println("propertiesFile file does not exists");
            return false;
        }


        return true;
    }
}
