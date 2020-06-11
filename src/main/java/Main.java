import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    public static final String FILENAME = "coordinates";

    public static void main(String[] args){
        String fileContents = getFileContents(FILENAME);
        //System.out.println("file contents: " + fileContents);

        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("\\d{2,}\\.\\d{2,}");
        Matcher matcher = pattern.matcher(fileContents);
        while (matcher.find()) {
            sb.append(matcher.group());
            sb.append("\n");
        }

        //contains all extracted coordinates separated by a new line
        String[] coordinates = sb.toString().split("\n");

        FeatureCollection featureCollection = new FeatureCollection();
        ObjectMapper mapper = new ObjectMapper();

        for(int i = 0; i < coordinates.length; i+=2){
            ArrayNode coordinateArrayNode = mapper.createArrayNode();

            double longitude = (Double.valueOf(coordinates[i+1])).floatValue();
            double latitude = (Double.valueOf(coordinates[i])).floatValue();

            coordinateArrayNode.add(longitude);
            coordinateArrayNode.add(latitude);
            ObjectNode geometry = featureCollection.createGeometry(coordinateArrayNode);
            ObjectNode feature = featureCollection.createFeature(geometry);
            featureCollection.addFeature(feature);

            System.out.println("latitude: " + latitude + " longitude: " + longitude);
        }

        //System.out.println(featureCollection.getCollection());

        try {
            writeCollectionToFile(featureCollection.getCollection(), FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileContents(String filename) {
        File f = new File("src/main/resources/" + filename + ".txt");
        StringBuilder sb = new StringBuilder();

        try (Scanner myReader = new Scanner(f)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sb.append(data);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void writeCollectionToFile(ObjectNode collection, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/" + filename + ".json");

        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(collection));
        writer.flush();
        writer.close();
    }













    /*
    TODO For google maps

    Should save coordinates in following format:
        No. | Latitude | Longitude
        1     56.959233  24.094365
     */
    private static void createExcelDataFile(){


    }
}
