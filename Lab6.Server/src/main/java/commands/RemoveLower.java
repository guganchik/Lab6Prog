package commands;

import app.CollectionManager;
import collections.Vehicle;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда RemoveLower
 */
public class RemoveLower implements ICommand{
    private final CollectionManager collectionManager;
    public RemoveLower(CollectionManager collectionManager) {
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
            if (collectionManager.removeLower(vehicle)) {
                output = "========= Operation success (RemoveLower) ===========\n\n";
            } else {
                output = "\n========== Operation error (RemoveLower) ============\n\n";
            }
            return new Response(request.getCommand(), null, output);
        }
    }

    @Override
    public String toString() {
        return "remove_lower {element} - Remove from the collection all elements smaller than the given one";
    }

}
