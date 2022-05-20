package commands;

import data.Request;
import data.Response;

public interface ICommand {
    
    public Response execute(Request request);
    
}

