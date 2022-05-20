package commands;

import app.CollectionManager;
import collections.Vehicle;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда RemoveGreater
 */
public class RemoveGreater implements ICommand {
    private final CollectionManager collectionManager;
    public RemoveGreater(CollectionManager collectionManager) {
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
            if (collectionManager.removeGreater(vehicle)) {
                output = "======== Operation success (RemoveGreater) ==========\n\n";
            } else {
                output = "\n========= Operation error (RemoveGreater) ===========\n\n";
            }
            return new Response(request.getCommand(), null, output);
        }
    }

    @Override
    public String toString() {
        return "remove_greater {element} - Remove from the collection all elements greater than the given";
    }

}
