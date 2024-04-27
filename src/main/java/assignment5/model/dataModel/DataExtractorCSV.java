package assignment5.model.dataModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class DataExtractorCSV extracts data from a cvs file into List filled with Lists of Strings.
 * Each String is a new comma separated entry and each sublist is a new line.
 * Class is an independent CSV reader thus does not know what the data is about.
 * Class exits program completely if given incorrect file.
 */

public class DataExtractorCSV {
    public static List<List<String>> CSVToList(String fileName) {
        List<List<String>> allData = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            reader.nextLine();
            while (reader.hasNext()) {
                String dataCommaSeparated = reader.nextLine();
                List<String> dataListSeparated = new ArrayList<>();
                int nextCommaIndex = dataCommaSeparated.indexOf(",");

                while (nextCommaIndex != -1) {
                    String dataString = dataCommaSeparated.substring(0, nextCommaIndex);
                    dataListSeparated.add(dataString.trim());
                    dataCommaSeparated = dataCommaSeparated.substring(nextCommaIndex + 1);
                    nextCommaIndex = dataCommaSeparated.indexOf(",");
                }

                dataListSeparated.add(dataCommaSeparated.trim());
                allData.add(dataListSeparated);
            }

            reader.close();

        } catch (FileNotFoundException error) {
            System.out.println("File Not Found, Exiting Program ...");
            System.exit(0);
        }
        return allData;
    }
}
