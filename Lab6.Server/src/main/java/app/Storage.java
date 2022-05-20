package app;

import collections.Coordinates;
import collections.Vehicle;
import collections.VehicleType;
import utils.Const;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.TreeSet;

public class Storage {

    private static final String SPLITTER = ";";
    private static final String EOL = "\r\n";

    private TreeSet<Vehicle> collection;
    private String filename;

    public Storage(TreeSet<Vehicle> collection, String filename) {
        this.collection = collection;
        this.filename = filename;
    }

    /**
     * Метод чтения из csv, записывает в коллекцию элементы из файла при запуске программы
     * @return максимальный Id
     */
    public int read() {
        int lastId = 0;
        collection.clear();
        try {
            Path path = Paths.get(filename);
            if (!Files.exists(path)) {
                return lastId;
            }


            FileReader fileReader= new FileReader(filename);




            int data = fileReader.read();
            String line = "";
            boolean newLine = false;
            
            

            while(data != -1) {
                while (data == 10 || data == 13) {
                    newLine = true;
                    data = fileReader.read();
                }

                if (newLine) {
                    String[] arr = line.split(SPLITTER);
                    if (arr.length < 8){
                        System.out.println("incorrect Data in file!");
                        System.exit(1);
                    }
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(Const.timeFormat.parse(arr[4]));
                    } catch (Exception e) {
                    }
                    
                    int id = Integer.parseInt(arr[0]);
                    if (id > lastId) {
                        lastId = id;
                    }

                    for (int i = 0; i < arr.length; i++) {
                          if (arr[i].equals("")){
                              System.out.println("The numeric fields of the element are not entered correctly!");
                              System.exit(1);
                          }
                    }

                    Double distanceTravelled = null;
                    if (!arr[7].equals("")) {
                        distanceTravelled = Double.parseDouble(arr[7]);

                    }

                    Coordinates coordinates = new Coordinates(Float.parseFloat(arr[2]), Float.parseFloat(arr[3]));
                    try {
                        Vehicle v = new Vehicle(Integer.parseInt(arr[0]), arr[1], coordinates, calendar.getTime(), Float.parseFloat(arr[5]), Long.parseLong(arr[6]),distanceTravelled, VehicleType.valueOf(arr[8]));
                        collection.add(v);
                    } catch (NumberFormatException e) {
                        System.out.println("The numeric fields of the element are not entered correctly!");
                        System.exit(1);
                    } catch (IllegalArgumentException e) {
                        System.out.println("VehicleType field entered incorrectly");
                        System.exit(1);
                    }


                    newLine = false;
                    line = "";
                } else {
                    line = line + (char)data;
                    data = fileReader.read();
                }
            }
            fileReader.close();


        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return lastId;
    }


    /**
     * Метод записи в файл csv (для команды Save)
     */
    public void write() {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // create FileOutputStream from filename
            fileOutputStream = new FileOutputStream(filename);

            // create BufferedOutputStream for FileOutputStream
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            String line;
            for (Vehicle v : collection) {

                    line = v.getId() + SPLITTER
                            + v.getName() + SPLITTER
                            + v.getCoordinates().getX() + SPLITTER
                            + v.getCoordinates().getY() + SPLITTER
                            + Const.timeFormat.format(v.getCreationDate()) + SPLITTER
                            + v.getEnginePower() + SPLITTER
                            + v.getCapacity() + SPLITTER
                            + (v.getDistanceTravelled()==null?"":v.getDistanceTravelled()) + SPLITTER
                            + v.getTypeAsString()
                            + EOL;

                    bufferedOutputStream.write(line.getBytes());
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
