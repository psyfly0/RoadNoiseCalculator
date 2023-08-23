/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saving.Writer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

/**
 *
 * @author szaboa
 */
/**
 * Helper class for writing shapefiles.
 */
public class WriterHelper {
    /**
     * Densifies a LineString geometry by adding additional vertices between existing vertices
     * based on a specified step value.
     *
     * @param geometry the LineString geometry to be densified
     * @param step     the desired distance between added vertices
     * @return the densified LineString geometry
     */
    public static Geometry densifyGeometry(Geometry geometry, double step) {
        GeometryFactory factory = geometry.getFactory();
        Coordinate[] coordinates = geometry.getCoordinates();
        List<Coordinate> densifiedCoordinates = new ArrayList<>();

        for (int i = 0; i < coordinates.length - 1; i++) {
            Coordinate start = coordinates[i];
            Coordinate end = coordinates[i + 1];
            double length = start.distance(end);

            int numSteps = (int) Math.ceil(length / step);
            double dx = (end.x - start.x) / numSteps;
            double dy = (end.y - start.y) / numSteps;

            for (int j = 0; j < numSteps; j++) {
                double x = start.x + j * dx;
                double y = start.y + j * dy;
                densifiedCoordinates.add(new Coordinate(x, y));
            }
        }

        densifiedCoordinates.add(coordinates[coordinates.length - 1]); // Add the last coordinate

        return factory.createLineString(densifiedCoordinates.toArray(new Coordinate[0]));
}
    
    /**
    * Truncates the given column name based on predefined patterns and returns the truncated name.
    * If no match is found, an empty string is returned.
    *
    * @param columnName the original column name to be truncated
    * @return the truncated column name based on predefined patterns, or an empty string if no match is found
    */
    public static String truncateNames(String columnName) {
        String truncatedName = "";

        // Define the patterns and corresponding truncated names
        Map<String, String> patterns = new HashMap<>();
        patterns.put("Azo.*", "NO");
        patterns.put("RAzo.*", "R_NO");
        patterns.put("1akNap.*", "1_ak_N");
        patterns.put("2akNap.*", "2_ak_N");
        patterns.put("3akNap.*", "3_ak_N");
        patterns.put("R1akNap.*", "R_1_ak_N");
        patterns.put("R2akNap.*", "R_2_ak_N");
        patterns.put("R3akNap.*", "R_3_ak_N");
        patterns.put("1akEj.*", "1_ak_E");
        patterns.put("2akEj.*", "2_ak_E");
        patterns.put("3akEj.*", "3_ak_E");
        patterns.put("R1akEj.*", "R_1_ak_E");
        patterns.put("R2akEj.*", "R_2_ak_E");
        patterns.put("R3akEj.*", "R_3_ak_E");
        patterns.put("1akSeb.*", "1_ak_kmh");
        patterns.put("2akSeb.*", "2_ak_kmh");
        patterns.put("3akSeb.*", "3_ak_kmh");
        patterns.put("R1akSeb.*", "R_1_ak_kmh");
        patterns.put("R2akSeb.*", "R_2_ak_kmh");
        patterns.put("R3akSeb.*", "R_3_ak_kmh");
        patterns.put("LAeq N.*", "LAeq_N");
        patterns.put("LAeq E.*", "LAeq_E");
        patterns.put("Vedotavolsag N.*", "Vedotav_N");
        patterns.put("Vedotavolsag E.*", "Vedotav_E");
        patterns.put("Hatasterulet N.*", "Hataster_N");
        patterns.put("Hatasterulet E.*", "Hataster_E");
        patterns.put("Lw N.*", "Lw_N");
        patterns.put("Lw E.*", "Lw_E");

        // Iterate through the patterns and check for matches
        for (Map.Entry<String, String> entry : patterns.entrySet()) {
            String pattern = entry.getKey();
            String truncatedValue = entry.getValue();
            if (columnName.matches(pattern)) {
                truncatedName = truncatedValue;
                break;
            }
        }

        // Handle additional specific cases
        if (columnName.contains("Nappali LAM")) {
            String numberPart = extractNumberPart(columnName);;
            return truncatedName = numberPart + "_m_N";
        } else if (columnName.contains("Éjjeli LAM")) {
            String numberPart = extractNumberPart(columnName);
            return truncatedName = numberPart + "_m_E";
        } else if (columnName.contains("LAeq N") 
                && columnName.contains("között")) {
            return truncatedName = "LAeq_KUL_N";
        } else if (columnName.contains("LAeq E") 
                && columnName.contains("között")) {
            return truncatedName = "LAeq_KUL_E";
        } else if (columnName.contains("Vedotavolsag N") 
                && columnName.contains("között")) {
            return truncatedName = "VedT_KUL_N";
        } else if (columnName.contains("Vedotavolsag E") 
                && columnName.contains("között")) {
            return truncatedName = "VedT_KUL_E";
        } else if (columnName.contains("Hatasterulet N") 
                && columnName.contains("között")) {
            return truncatedName = "HatT_KUL_N";
        } else if (columnName.contains("Hatasterulet E")
                && columnName.contains("között")) {
            return truncatedName = "HatT_KUL_E";
        }

        return truncatedName;
    }    
    
    /**
    * Extracts the number part from the given column name, which is enclosed between "szint<br>" and " méteren" tags.
    * The extracted number part is trimmed and returned as a string.
    *
    * @param columnName the column name containing the number part to be extracted
    * @return the extracted number part as a string
    */
    public static String extractNumberPart(String columnName) {
        int startIndex = columnName.indexOf("szint<br>") + "szint<br>".length();
        int endIndex = columnName.indexOf(" méteren");
        String numberPart = columnName.substring(startIndex, endIndex).trim();
        return numberPart;
    }
    
    public static Class<?> determineColumnType(String columnName) {             
    List<String> objectColumnPatterns = Arrays.asList(
        "NO", "R_NO", "1_ak_N", "2_ak_N", "3_ak_N", "R_1_ak_N", 
        "R_2_ak_N", "R_3_ak_N", "1_ak_E", "2_ak_E", "3_ak_E", 
        "R_1_ak_E", "R_2_ak_E", "R_3_ak_E", "1_ak_kmh", "2_ak_kmh", 
        "3_ak_kmh", "R_1_ak_kmh", "R_2_ak_kmh", "R_3_ak_kmh"
    );
    
        for (String name : objectColumnPatterns) {
            if (columnName.matches(name)) {
                return String.class;
            }
        }
        return Double.class;
    }
    
    public static Object castToColumnType(Object attributeValue, Class<?> columnType) {
        if (columnType == Double.class) {
            return Double.parseDouble(attributeValue.toString());
        }
        return null;
    }
}
