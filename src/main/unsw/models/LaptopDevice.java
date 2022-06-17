package unsw.models;
import unsw.utils.*;

/**
 * constructor for laptop Devices
 */
public class LaptopDevice extends Devices{
    public LaptopDevice(String deviceId, Angle position, String type){
        super(deviceId, position, type);
        this.maxRange = 100_000.00; 
    }
}
