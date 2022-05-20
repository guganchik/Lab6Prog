package app;

import commands.*;
import data.Request;
import data.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс, который хранит все возможные команды и вызывает их по ключу.
 */
public class DataProvider {
    public static final HashMap<String, ICommand> commands = new HashMap<>();
    private CollectionManager collectionManager;
    private String filename;
    
    public DataProvider(String filename) {
        this.filename = filename;
        collectionManager = new CollectionManager(filename);
        commands.put("help", new Help());                                       // done
        commands.put("clear", new Clear(collectionManager));                    // done
        commands.put("add", new Add(collectionManager));                        // done
        commands.put("info", new Info(collectionManager));                      // done
        commands.put("add_if_max", new AddIfMax(collectionManager));
        commands.put("remove_greater", new RemoveGreater(collectionManager));   // done
        commands.put("remove_lower", new RemoveLower(collectionManager));       // done
        commands.put("show", new Show(collectionManager));                      // done
        commands.put("update", new Update(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));        // done
        commands.put("max_by_id", new MaxById(collectionManager));              // done
        commands.put("print_ascending", new PrintAscending(collectionManager)); // done
        commands.put("filter_greater_than_type", new FilterGreaterThanType(collectionManager)); // done
        commands.put("exit", new Exit(collectionManager));                      // done
        commands.put("execute_script", new ExecuteScript(this, collectionManager));
    }

    public Response execute(Request request) {

        /**
        String commandCut[] = command.trim().split("\\s+");
        String commandName = commandCut[0];
        String args[] = Arrays.copyOfRange(commandCut, 1, commandCut.length);
        */

        if (commands.containsKey(request.getCommand())) {
            return commands.get(request.getCommand()).execute(request);
        } else {
            return new Response(request.getCommand(), null, "Command not found!\n\n");
        }
    }
    
    public void doSave() {
        collectionManager.save();
    }
    
}
