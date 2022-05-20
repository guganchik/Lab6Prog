package commands;

import app.CollectionManager;
import app.DataProvider;
import collections.Vehicle;
import data.Request;
import data.Response;
import java.util.Comparator;
import java.util.Scanner;
/**
 * Команда Show
 */
public class Show implements ICommand {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    
    /**
    public void execute(Scanner input, String args[], boolean silent) {

        if(collectionManager.show()) {
            if(!silent){System.out.println("Operation success (Show)");}
        } else {
           if(!silent){System.out.println("Operation error (Show)");}
        }
    }*/
    
    @Override
    public Response execute(Request request) {
        String output =  "============= Executing command (Show) ==============\n"
                + collectionManager.show()
                + "============= Operation success (Show) ==============\n\n";
        return new Response(request.getCommand(), null, output);
    }
        
    @Override
    public String toString() {
        return "show - Print to standard output all elements of the collection in string representation";
    }
}
