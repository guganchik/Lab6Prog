package app;

import collections.Vehicle;
import collections.VehicleType;
import utils.Const;

import java.util.*;

/**
 * Класс управления коллекцией. Здесь происходят все изменения коллекции.
 */
public class CollectionManager {

    private final TreeSet<Vehicle> collection;
    private final Storage storage;
    static Date collectionDate = new Date();
    private Integer vehicleId = 0;
    

    public CollectionManager(String filename) {
        collection = new TreeSet();
        storage = new Storage(collection, filename);
        vehicleId = read();
    }

    public int read() {
        return storage.read();
    }

    /**
     * Запись в файл
     * @return
     */
    public boolean save() {
        storage.write();
        return true;
    }

    /**
     *
     * @return Коллекция
     */
    public TreeSet<Vehicle> getCollection() {
        return collection;
    }

    /**
     * Информация о коллекции
     * @return
     */
    public String info() {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Const.timeFormat.parse(String.valueOf(collectionDate)));
        } catch (Exception e) {
        }
        return  "Type - " + collection.getClass() + "\n" +
                "Creation date - " +  calendar.getTime() + "\n"+
                "Amount of elements - " + collection.size();
    }
    
    /**
     * Новое Id для следующего эл-та
     * @return новый Id
     */
    public int getNewId() {
        return ++vehicleId;
    }

    /**
     * Добавить элемент в коллекцию
     */
    public boolean add(Vehicle v) {
        return collection.add(v);
    }

    /**
     * Обновить элемент
     */
    public boolean updateElement(Vehicle v1, Vehicle v2) {
        v1.setName(v2.getName());
        v1.setCoordinates(v2.getCoordinates());
        v1.setEnginePower(v2.getEnginePower());
        v1.setCapacity(v2.getCapacity());
        v1.setDistanceTravelled(v2.getDistanceTravelled());
        v1.setType(v2.getType());
        return true;
    }
    
    /**
     * Добавить элемент в коллекцию если  его EnginePower и Capacity больше максимального значения данных полей в этой коллекции
     */
    public boolean add_if_max(Vehicle element) {
        Iterator<Vehicle> itr = collection.iterator();
        while (itr.hasNext()) {
            Vehicle v = itr.next();
            if (v.getEnginePower() > element.getEnginePower() || v.getCapacity() > element.getCapacity()) {
                break;
            }
            else if(v.getEnginePower() < element.getEnginePower() && v.getCapacity() < element.getCapacity() && !itr.hasNext()) {
               return collection.add(element);
            }

        }
        return false;
    }
    
    /**
     * Очистить коллекцию
     */
    public boolean clear() {
        collection.clear();
        return true;
    }
    

    /**
     * Вывести элементы коллекции в порядке возрастания
     * @return
     */
    public String printAscending() {
        if (collection.isEmpty()) {
            return "Collection is empty!\n";
        } else {
            return collection
                .stream()
                .sorted(Comparator.comparingInt(vehicle -> vehicle.getId()))
                .map(Vehicle::toString)
                .reduce("", (result, vehicle) -> result + vehicle + "\n");
        }
    }
    
    /**
     * Вывести элементы коллекции
     * @return 
     */
    public String show() {
        if (collection.isEmpty()) {
            return "Collection is empty!\n";
        } else {
            return collection
                .stream()
                .map(Vehicle::toString)
                .reduce("", (result, vehicle) -> result + vehicle + "\n");
        }
    }

    /**
     * Вывести элементы индекс поля Type которого больше чем индекс введеного Type
     */
    public String FilterGreaterThanType(String vehicleType) {
        if (collection.isEmpty()) {
            return "Collection is empty!\n";
        } else {
            int ord = VehicleType.valueOf(vehicleType).ordinal();
            return collection
                .stream()
                .filter(vehicle -> vehicle.getType().ordinal() > ord)
                .map(Vehicle::toString)
                .reduce("", (result, vehicle) -> result + vehicle + "\n");
        }
    }
    
    /**
     * Найти элемент по его id
     */
    public Vehicle get_by_id(int id) {
        for(Vehicle v: collection) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }
    

    /**
     * Удалить элемент по его id
     */
    public boolean removeById(int id) {
        return collection.removeIf(vehicle -> vehicle.getId() == id);
    }
    

    /**
     * Удалить элементы больше заданного
     */
    public boolean removeGreater(Vehicle element) {
        return collection.removeIf(v -> v.getDistanceTravelled() > element.getDistanceTravelled() && v.getCapacity() > element.getCapacity() && v.getEnginePower() > element.getEnginePower());
    }

    /**
     * Удалить элементы меньше заданного
     */
    public boolean removeLower(Vehicle element) {
       return collection.removeIf(v -> v.getDistanceTravelled() < element.getDistanceTravelled() && v.getCapacity() < element.getCapacity() && v.getEnginePower() < element.getEnginePower());
    }

    /**
     * Вывести элемент с максимальным Id
     */
    public Vehicle getMaxById() {
        return collection
                .stream()
                .max(Comparator.comparing(Vehicle::getId))
                .orElse(null);
    }
}
