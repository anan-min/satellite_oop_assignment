package unsw.models;
import unsw.utils.*;

public class HandheldDevice extends Devices {
 
    /**
     * contructor for HandheldDevice
     * @param deviceId
     * @param position
     * @param type
     */
    public HandheldDevice(String deviceId, Angle position, String type){
        super(deviceId, position, type);
        this.maxRange = 50_000.00;
    }

}
