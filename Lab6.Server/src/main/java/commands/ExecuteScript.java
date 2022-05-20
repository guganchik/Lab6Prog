package commands;

import app.CollectionManager;
import app.DataProvider;
import checker.ExecuteScriptFileNames;
import collections.VehicleType;
import data.Request;
import data.Response;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.System.exit;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Команда ExecuteScript
 */
public class ExecuteScript implements ICommand{
    private final CollectionManager collectionManager;
    private final DataProvider dataProvider;
    private final ExecuteScriptFileNames checkPath = new ExecuteScriptFileNames();

    public ExecuteScript(DataProvider dataProvider, CollectionManager collectionManager) {
        this.dataProvider = dataProvider;
        this.collectionManager = collectionManager;
    }

    
    @Override
    public Response execute(Request request) {
        String output;
        try {
            String filename = request.getArgs()[0];
            Path path = Paths.get(filename);
            Path pathAbs = path.toAbsolutePath();

            checkPath.clearList();

            if (checkPath.checkPath(pathAbs)) {
                output = "Calling scripts is looped!\n\n";
            } else {
                String command = null;
                String[] arguments = null;
                Response response;
                Scanner scanner = new Scanner(path);
                boolean skipInput = false;
                String input = null;

                StringBuilder sb = new StringBuilder();
                
                while (scanner.hasNext()) {
                    if (!skipInput) {
                        input = scanner.nextLine();
                        String[] tokens = input.trim().split("\\s+");
                        command = tokens[0];
                        arguments = Arrays.copyOfRange(tokens, 1, tokens.length);
                        request = new Request(command, arguments);
                        sb.append("Command: ").append(input).append("\n");
                    }
                    skipInput = false;
                    response = dataProvider.execute(request);
                    if (response != null) {
                        if (response.getObject() == null) {
                            if (response.getOutput() != null) {
                                sb.append(response.getOutput());
                            }
                        } else {
                            if (response.getOutput() != null) {
                                sb.append(response.getOutput());
                            }
                            Method readMethod = response.object.getMethod("input", Scanner.class, Boolean.class);
                            Object object = readMethod.invoke(null, scanner, true);
                            if (object == null) {
                                return new Response(request.getCommand(), null, "Oops, uncorrect script\n\n");
                            }
                            request = new Request(command, arguments, (Serializable)object);
                            skipInput = true;
                        }
                    }
                }
                output = "========= Executing command (ExecuteScript) =========\n";
                output = output + sb.toString();
                output = output + "\n========== Operation success (ExecuteScript) ========\n\n";
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            output = "Invalid input! Type was not specified!\n\n";
        }
        catch (IOException e) {
            output = "Couldn't find script file!\n\n";
        }
        catch (Exception e) {
            e.printStackTrace();
            output = "Oops, uncorrect script\n\n";
        }
        return new Response(request.getCommand(), null, output);
    }
    
    @Override
    public String toString() {
        return "execute_script file_name - Read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode";
    }

}
