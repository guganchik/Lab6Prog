package commands;

import app.CollectionManager;
import collections.Vehicle;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда RemoveById
 */
public class RemoveById implements ICommand {
    private final CollectionManager collectionManager;


    public RemoveById(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    
    public Response execute(Request request) {
        String output = null;
        try {
            int id = Integer.parseInt(request.getArgs()[0]);
            if (this.collectionManager.removeById(id)) {
                output = "========== Operation success (RemoveById) ===========\n\n";
            } else {
                output = "Object with id=" + id + " not found!\n\n";
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            output = "Invalid input! ID was not specified!\n\n";
        }
        catch (NumberFormatException e) {
            output = "Invalid input! ID must be a number!\n\n";
        }
        return new Response(request.getCommand(), null, output);
    }
    
    @Override
    public String toString() {
        return "remove_by_id id - Remove element from collection by its id";
    }
}
