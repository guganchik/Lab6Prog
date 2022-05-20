package data;

import java.io.Serializable;

public class Response implements Serializable {
    
    public String command;
    public String output;
    public String[] args;
    public Class<? extends Serializable> object;

    public Response(String command, String[] args, String output) {
        this.command = command;
        this.output = output;
    }
    
    public Response(String command, String[] args, String output, Class<? extends Serializable> object) {
        this.command = command;
        this.output = output;
        this.object = object;
    }

    public String getCommand() {
        return command;
    }

    public String getOutput() {
        return output;
    }

    public Class<? extends Serializable> getObject() {
        return object;
    }
    
    
}
