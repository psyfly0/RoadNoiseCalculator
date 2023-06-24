/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modifiers.ShapeModifier;

import FileReadingAndDataStore.DataStore.ShapeData;
import Modifiers.Repository.ModifierRepository;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.operation.buffer.BufferParameters;

/**
 *
 * @author szaboa
 */
/**
 * Utility class for creating buffer zone based on calculated values.
 */
public class ShapefileModifier {
    private ModifierRepository modifierRepository;

    /**
     * Constructs a new ShapefileModifier instance.
     *
     * @param modifierRepository the repository for accessing modification operations
     */
    public ShapefileModifier(
            ModifierRepository modifierRepository) {
        
        this.modifierRepository = modifierRepository;
    }
    
    /**
     * Creates new geometries by buffering the shapefile geometries using the provided values.
     *
     * @param valuesDay   the list of day values for buffering
     * @param valuesNight the list of night values for buffering
     * @param index       the index of the shapefile
     * @return the list of newly created geometries after buffering
     */
    public List<Geometry> createNewGeometry(
            List<Double> valuesDay, 
            List<Double> valuesNight, int index) {
        ShapeData shapeData = modifierRepository.getShapeData(index);
        List<Geometry> geometries = shapeData.getGeometries();

        List<Geometry> newGeometries = new ArrayList<>();

        BufferParameters bufferParameters = new BufferParameters();
        bufferParameters.setEndCapStyle(BufferParameters.CAP_ROUND);
        bufferParameters.setJoinStyle(BufferParameters.JOIN_ROUND);
        int quadrantSegments = 8;

        // Create buffer for valuesDay
        Geometry bufferDay = null;
        for (int i = 0; i < geometries.size(); i++) {
            Geometry geometry = geometries.get(i);
            double distanceDay = valuesDay.get(i);

            Geometry buffer = geometry.buffer(distanceDay, quadrantSegments, 
                    bufferParameters.getEndCapStyle());
            if (bufferDay == null) {
                bufferDay = buffer;
            } else {
                bufferDay = bufferDay.union(buffer);
            }
        }
        bufferDay = bufferDay.getBoundary(); // Convert to a LineString
        newGeometries.add(bufferDay);

        // Create buffer for valuesNight
        Geometry bufferNight = null;
        for (int i = 0; i < geometries.size(); i++) {
            Geometry geometry = geometries.get(i);
            double distanceNight = valuesNight.get(i);

            Geometry buffer = geometry.buffer(distanceNight, quadrantSegments, 
                    bufferParameters.getEndCapStyle());
            if (bufferNight == null) {
                bufferNight = buffer;
            } else {
                bufferNight = bufferNight.union(buffer);
            }
        }
        bufferNight = bufferNight.getBoundary(); // Convert to a LineString
        newGeometries.add(bufferNight);

        return newGeometries;
        }
    

}
