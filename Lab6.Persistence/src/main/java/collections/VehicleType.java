package collections;

import java.io.Serializable;

/**
 * Enum для хранения Типа исходного класса
 */
public enum VehicleType implements Serializable {
    HELICOPTER,
    SHIP,
    CHOPPER;

    static VehicleType[] namesVehicleType = VehicleType.values();
}