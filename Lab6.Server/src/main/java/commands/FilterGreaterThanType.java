package commands;

import app.CollectionManager;
import collections.VehicleType;
import data.Request;
import data.Response;
import java.util.Scanner;
/**
 * Команда FilterGreaterThanType
 */
public class FilterGreaterThanType implements ICommand{
    private final CollectionManager collectionManager;

    public FilterGreaterThanType(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    
    @Override
    public Response execute(Request request) {
        String output = null;
        try {
            String vehicleType = request.getArgs()[0];
            VehicleType.valueOf(vehicleType);
            output =  "===== Executing command (FilterGreaterThanType) =====\n"
                    + collectionManager.FilterGreaterThanType(vehicleType)
                    + "===== Operation success (FilterGreaterThanType) =====\n\n";
        }
        catch (ArrayIndexOutOfBoundsException e) {
            output = "Invalid input! Type was not specified!\n\n";
        }
        catch (Exception e) {
            output = "Invalid input! Such type does not exist!\n\n";
        }
        return new Response(request.getCommand(), null, output);
    }
    

    @Override
    public String toString() {
        return "filter_greater_than_type type - Display elements whose type field value is greater than the given one";
    }

}
