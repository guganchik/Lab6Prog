package commands;

import app.CollectionManager;
import collections.Coordinates;
import collections.Vehicle;
import collections.VehicleType;
import data.Request;
import data.Response;
import java.util.Scanner;

/**
 * Команда Add
 */
public class Add implements ICommand{
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    
    public Response execute(Request request) {
        if (request.object == null) {
            return new Response(request.getCommand(), null, "============= Executing command (Add) ===============\n", Vehicle.class);
        } else {
            String output = "";
            Vehicle vehicle = (Vehicle) request.getObject();
            if (vehicle.getId() == 0) {
                vehicle.setId(collectionManager.getNewId());
            }
            if (collectionManager.add(vehicle)) {
                output = "============== Operation success (Add) ==============\n\n";
            } else {
                output = "=============== Operation error (Add) ===============\n\n";
            }
            return new Response(request.getCommand(), null, output);
        }
        
    }
    
    @Override
    public String toString() {
        return "add {element} - Add a new element to the collection";
    }
}

