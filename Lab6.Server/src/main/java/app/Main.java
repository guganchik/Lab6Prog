package app;


import data.Request;
import data.Response;
import java.io.File;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Usage for launching server:  java -jar Lab6.Server.jar filename port");
        System.out.println("Argument values:");
        System.out.println("filename - path to file where vechicle items will be saved, for example ./storage.csv");
        System.out.println("port - port number for listeting connections, for example 30000");
        System.out.println("================== Starting Server ==================");

        if (null == args || args.length == 0) {
            System.out.println("Error! Path to storage file was not specified!");
            System.exit(1);
        } else {
            Path path = Paths.get(args[0]);
            File file = new File(String.valueOf(path));
            if (Files.exists(path)&&(!file.canRead() || !file.canWrite())) {
                System.out.println("Permission denied");
                System.exit(1);
            }
            if (Files.isDirectory(path)) {
                System.out.println("Error! Path to storage can't be a directory!");
                return;
            } else {
                if (Files.exists(path)) {
                    System.out.println("Using existing storage file: " + args[0]);
                } else {
                    System.out.println("Storage file doesn't exist, using empty");
                }

            }
        }
        
        if (args.length == 1) {
            System.out.println("Port is not specified");
            System.exit(1);
        }
        
        DataProvider dataProvider = new DataProvider(args[0]);

        try {
            NetworkProvider networkProvider = new NetworkProvider(Integer.parseInt(args[1]));
            
            System.out.print("Type 'exit' to shutdown server: ");
            
            while (true) {
                Request request = networkProvider.receive();
                if (request != null) {
                    Response response = dataProvider.execute(request);
                    networkProvider.send(request.getClient(), response);
                }
                
                if (System.in.available() > 0) {
                    String command = new Scanner(System.in).nextLine();
                    if (command.equals("exit")) {
                        dataProvider.doSave();
                        System.exit(0);
                    } else if (command.equals("save")) {
                        dataProvider.doSave();
                    } else {
                        System.out.println("Command not found!\n");
                    }
                    System.out.print("Type 'exit' to shutdown server: ");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
