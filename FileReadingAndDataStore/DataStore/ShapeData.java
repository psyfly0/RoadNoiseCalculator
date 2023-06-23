/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.DataStore;

import java.util.List;
import org.geotools.referencing.CRS;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
/**
 *
 * @author szaboa
 */

/**
 * Represents a set of shape data.
 */
public class ShapeData {
    /**
     * The geometry of this shape data.
     */
    private List<Geometry> geometries;

    /**
     * The schema of the features associated with this shape data.
     */
    private SimpleFeatureType schema;

   /**
     * Constructs a new shape data object with the specified geometry and attributes.
     *
     * @param geometries the list of geometries of the shape data
     * @param schema     the schema of the features associated with this shape data
     */
    public ShapeData(
            List<Geometry> geometries,
            SimpleFeatureType schema) {
        
        this.geometries = geometries;
        this.schema = schema;
    }

    /**
     * Returns the list of geometries of this shape data.
     *
     * @return the list of geometries of this shape data
     */
    public List<Geometry> getGeometries() {
        return geometries;
    }

    /**
     * Sets the coordinate reference system (CRS) of this shape data.
     *
     * @return the coordinate reference system (CRS)
     */
    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        CoordinateReferenceSystem crs = schema.getCoordinateReferenceSystem();
            String epsgCode = "EPSG:23700";
            try {
                // Create the CoordinateReferenceSystem
                crs = CRS.decode(epsgCode);
                schema = SimpleFeatureTypeBuilder.retype(schema, crs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return crs;
    }
}
