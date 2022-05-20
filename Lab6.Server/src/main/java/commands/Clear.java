package commands;

import app.CollectionManager;
import app.DataProvider;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда Clear
 */
public class Clear implements ICommand {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        String output;
        if (collectionManager.clear()) {
            output = "============= Operation success (Clear) =============\n\n";
        } else {
            output = "\n============== Operation error (Clear) ==============\n\n";
        }
        return new Response(request.getCommand(), null, output);
    }
    

    @Override
    public String toString() {
        return "clear - Clear the collection";
    }

}
