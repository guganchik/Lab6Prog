package commands;

import app.CollectionManager;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда Exit
 */
public class Exit implements ICommand{
    
    private final CollectionManager collectionManager;

    public Exit(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return null;
    }

    @Override
    public String toString() {
        return "exit - Exit from the application";
    }


}
