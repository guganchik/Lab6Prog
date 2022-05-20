package commands;

import app.CollectionManager;
import collections.Coordinates;
import collections.Vehicle;
import collections.VehicleType;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда AddIfMax
 */
public class AddIfMax implements ICommand {

    private final CollectionManager collectionManager;
    
    public AddIfMax(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        if (request.object == null) {
            return new Response(request.getCommand(), null, null, Vehicle.class);
        } else {
            String output = "";
            Vehicle vehicle = (Vehicle) request.getObject();
            if (vehicle.getId() == 0) {
                vehicle.setId(collectionManager.getNewId());
            }
            if (collectionManager.add_if_max(vehicle)) {
                output = "=========== Operation success (AddIfMax) ============\n\n";
            } else {
                output = "\n============ Operation error (AddIfMax) =============\n\n";
            }
            return new Response(request.getCommand(), null, output);
        }
    }
    
    

    @Override
    public String toString() {
        return "add_if_max {element} - Add a new element to the collection if its value is greater than the value of the largest element in this collection";
    }

}
