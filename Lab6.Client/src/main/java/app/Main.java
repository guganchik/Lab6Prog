package app;

import data.Request;
import data.Response;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Usage for launching Client:  java -jar Lab6.Client.jar address port");
        System.out.println("Argument values:");
        System.out.println("address - address of the server, for example 127.0.0.1");
        System.out.println("port - address of the server, for example 30000");
        System.out.println("================== Starting Client ==================");
        
        if (null == args || args.length < 2) {
            System.out.println("Error! Address or port is not specified!");
            System.exit(1);
        }
        
        try {
            Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Error! Port is incorrect!");
            System.exit(1);
        }
        
        
        try {
            NetworkProvider networkProvider = new NetworkProvider(args[0], Integer.parseInt(args[1]));
            Scanner scanner = new Scanner(System.in);

            String input = null;
            String command = null;
            String[] arguments = null;
            Request request = null;
            Response response;
            
            boolean skipInput = false;
            
            while (true) {
                if (!skipInput) {
                    System.out.print("Enter command: ");
                    input = scanner.nextLine();
                    String[] tokens = input.trim().split("\\s+");
                    command = tokens[0];
                    if (command.equals("exit")) {
                        System.exit(0);
                    }
                    arguments = Arrays.copyOfRange(tokens, 1, tokens.length);
                    request = new Request(command, arguments);
                }
                skipInput = false;
                networkProvider.send(request);
                response = networkProvider.receive();
                if (response != null) {
                    if (response.getObject() == null) {
                        if (response.getOutput() != null) {
                            System.out.print(response.getOutput());
                        }
                    } else {
                        if (response.getOutput() != null) {
                            System.out.print(response.getOutput());
                        }
                        //Serializable object = (Serializable) response.object.getMethod("input").invoke(scanner);
                        //request = new Request(command, arguments, object);
                        //skipInput = true;
                        Method readMethod = response.object.getMethod("input", Scanner.class, Boolean.class);
                        Object object = readMethod.invoke(null, scanner, false);
                        request = new Request(command, arguments, (Serializable)object);
                        skipInput = true;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Exit program");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
