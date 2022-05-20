package commands;

import app.DataProvider;
import data.Request;
import data.Response;
import java.util.Comparator;

/**
 * Команда Help
 */

public class Help implements ICommand{
    
    @Override
    public Response execute(Request request) {
        String output = DataProvider.commands
                .values()
                .stream()
                .sorted(Comparator.comparing(command -> command.toString()))
                .map(ICommand::toString)
                .reduce("", (result, element) -> result + element + "\n");
        
        output =  "============= Executing command (Help) ==============\n"
                + output
                + "============== Operation success (Help) =============\n\n";
        return new Response(request.getCommand(), null, output);
        
        
    }

    @Override
    public String toString() {
        return "help - Show this help";
    }
}

