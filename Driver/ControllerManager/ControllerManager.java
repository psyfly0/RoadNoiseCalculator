/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.ControllerManager;

import FileReadingAndDataStore.Controllers.DbfController;
import FileReadingAndDataStore.Controllers.ShapefileController;
import Modifiers.Controller.ModifierController;
import Saving.Controller.ShapeNoiseOutputController;

/**
 *
 * @author szaboa
 */
/**
 * The ControllerManager class manages the various controllers used in the Közúti Zajszámító Program.
 * It provides convenient access to the shapefile, DBF, modifier, and shape noise output controllers.
 */
public class ControllerManager {
    private ShapefileController shapefileController;
    private DbfController dbfController;
    private ModifierController modifierController;
    private ShapeNoiseOutputController shapeNoiseOutputController;

    /**
     * Creates a new instance of the ControllerManager class with the specified controllers.
     *
     * @param shapefileController          the controller for managing shapefile operations
     * @param dbfController                the controller for managing DBF operations
     * @param modifierController           the controller for managing modifier operations
     * @param shapeNoiseOutputController   the controller for managing shape noise output operations
     */
    public ControllerManager(
            ShapefileController shapefileController,
            DbfController dbfController,
            ModifierController modifierController,
            ShapeNoiseOutputController shapeNoiseOutputController) {
        
        this.shapefileController = shapefileController;
        this.dbfController = dbfController;
        this.modifierController = modifierController;
        this.shapeNoiseOutputController = shapeNoiseOutputController;
    }

    /**
     * Retrieves the shapefile controller.
     *
     * @return the shapefile controller
     */
    public ShapefileController getShapefileController() {
        return shapefileController;
    }

    /**
     * Retrieves the DBF controller.
     *
     * @return the DBF controller
     */
    public DbfController getDbfController() {
        return dbfController;
    }

    /**
     * Retrieves the modifier controller.
     *
     * @return the modifier controller
     */
    public ModifierController getModifierController() {
        return modifierController;
    }

    /**
     * Retrieves the shape noise output controller.
     *
     * @return the shape noise output controller
     */
    public ShapeNoiseOutputController getShapeNoiseOutputController() {
        return shapeNoiseOutputController;
    }    
}
