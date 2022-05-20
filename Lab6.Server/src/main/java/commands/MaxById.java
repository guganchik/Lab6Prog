package commands;

import app.CollectionManager;
import collections.Vehicle;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда MaxById
 */
public class MaxById implements ICommand{

    private final CollectionManager collectionManager;

    public MaxById(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
    public void execute(Scanner input, String args[], boolean silent) {
        if(collectionManager.max_by_id() ){
            if(!silent){System.out.println("Operation success (MaxById)");}
        } else {
            if(!silent){System.out.println("Operation error (MaxById)");}
        }
    }
    */
    
    @Override
    public Response execute(Request request) {
        Vehicle vehicle = collectionManager.getMaxById();
        String output = "=========== Executing command (MaxById) =============\n"
                + ((vehicle!=null)?vehicle.toString():"Collection is empty!")
                + "\n============ Operation success (MaxById) ============\n\n";
        return new Response(request.getCommand(), null, output);
    }

    @Override
    public String toString() {
        return "max_by_id - Display any object from the collection whose id field value is the maximum";
    }

}

